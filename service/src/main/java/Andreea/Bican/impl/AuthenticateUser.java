package Andreea.Bican.impl;

import Andreea.Bican.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by andre on 02.08.2016.
 */
public class AuthenticateUser implements UserDetails {

    User user;

    public AuthenticateUser(User user){

        HashSet<String> authorities = (HashSet<String>) user.getAuthorities();
        System.out.println("User's authorities");
        for(String authority : authorities){
            System.out.println(authority);
        }
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;// (Collection<? extends GrantedAuthority>) authoritiesService.getAuthorities("bf3bd175-d0b5-4bf1-a751-d27906ed25ab");
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
