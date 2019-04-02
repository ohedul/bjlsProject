package bd.ohedulalam.bjlsProject.security;

import bd.ohedulalam.bjlsProject.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
* Spring security will use this Object's instance for performing
* authentication and authorization.
* Our CustomMemberDetailsService will use it.*/
public class MemberPrincipal implements UserDetails {
    private long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public MemberPrincipal(long id, String name, String username, String email,
                           String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static MemberPrincipal create(Member member){
        //This method will be called by CustomMemberDetailsService.
        List<GrantedAuthority> authorities = member.getRoles().stream().map(role->
        new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new MemberPrincipal(member.getId(),
                member.getName(),
                member.getUsername(),
                member.getEmail(),
                member.getPassword(),
                authorities);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj==null || getClass() != obj.getClass()){
            return false;
        }
        MemberPrincipal that = (MemberPrincipal) obj;
        return Objects.equals(id, that.id);
    }
}
