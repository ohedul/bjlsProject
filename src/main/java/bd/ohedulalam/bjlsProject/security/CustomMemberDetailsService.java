package bd.ohedulalam.bjlsProject.security;

import bd.ohedulalam.bjlsProject.model.Member;
import bd.ohedulalam.bjlsProject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* To authenticate an user or perform various role based operations
* spring security needs to load ueer's detail somehow, so we created this.
* It implements UserDetailsService and we give the implementation for
* loadUserByUsername() method which returns UserDetails object that is
* used by spring.
*/

@Service
public class CustomMemberDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsernameOrEmail(s,s).orElseThrow(()-> new
                UsernameNotFoundException("No member is found by this username or email!"));

        return MemberPrincipal.create(member);
    }

    @Transactional
    public UserDetails loadUserById(long id){
        // This method is used by JwtAuthenticationFilter class

        Member member = memberRepository.findById(id).orElseThrow(()->
        new UsernameNotFoundException("Member not found with id " + id));
        return MemberPrincipal.create(member);
    }
}
