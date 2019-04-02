package bd.ohedulalam.bjlsProject.controllers;

import bd.ohedulalam.bjlsProject.exceptions.AppException;
import bd.ohedulalam.bjlsProject.model.Member;
import bd.ohedulalam.bjlsProject.model.Role;
import bd.ohedulalam.bjlsProject.model.RoleName;
import bd.ohedulalam.bjlsProject.payload.ApiResponse;
import bd.ohedulalam.bjlsProject.payload.JwtAuthenticationResponse;
import bd.ohedulalam.bjlsProject.payload.LoginRequest;
import bd.ohedulalam.bjlsProject.payload.SignupRequest;
import bd.ohedulalam.bjlsProject.repository.MemberRepository;
import bd.ohedulalam.bjlsProject.repository.RoleRepository;
import bd.ohedulalam.bjlsProject.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    /*
    * This is the controller where all sign up and login
    * logic goes, we let user login using username or email
    * so, we used getUsernameOrEmail() method*/

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateMember(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));//After successfull login payload will send the token.
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> registerMember(@Valid @RequestBody SignupRequest signupRequest){
        if (memberRepository.existsByUsername(signupRequest.getUsername())){
            return new ResponseEntity<>(new ApiResponse(false, "Username already exists!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (memberRepository.existsByEmail(signupRequest.getEmail())){
            return new ResponseEntity<>(new ApiResponse(false, "Email already exists!"),
                    HttpStatus.BAD_REQUEST);
        }

        Member member = new Member(signupRequest.getName(),
                signupRequest.getUsername(), signupRequest.getEmail(),
                signupRequest.getPassword());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Role memberRole = roleRepository.findByName(RoleName.ROLE_MEMBER).orElseThrow(
                ()->new AppException("Member role is not set!")
        );

        member.setRoles(Collections.singleton(memberRole));
        Member result = memberRepository.save(member);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/members/{username}").buildAndExpand(result.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true,
                "Successfully registered a member"));
    }
}
