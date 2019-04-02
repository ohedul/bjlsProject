package bd.ohedulalam.bjlsProject.controllers;

import bd.ohedulalam.bjlsProject.exceptions.ResourceNotFoundException;
import bd.ohedulalam.bjlsProject.model.Member;
import bd.ohedulalam.bjlsProject.payload.ApiResponse;
import bd.ohedulalam.bjlsProject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/member")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MemberController {
    /*
    * here we simply implemented two method
    * 1. member can see all the member details
    * 2. admin can delete a member
    * we can do more cool things using spring data jpa
    */

    @Autowired
    MemberRepository memberRepository;

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(value = "/all")
    public List<Member> getAllMember(){
        return memberRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<?> deleteMember(@PathVariable(value = "username") String usernsme){
        Member member = memberRepository.findByUsername(usernsme).orElseThrow(
                ()->new ResourceNotFoundException("Member", "username", usernsme));
        memberRepository.delete(member);

        return ResponseEntity.ok(new ApiResponse(true, "Successfully deleted the member: "+ usernsme));


    }
}
