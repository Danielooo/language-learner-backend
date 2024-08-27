package org.novi.languagelearner.security;

import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApiUserDetails implements UserDetails {

    private final User user;

    public ApiUserDetails(User user) {
        this.user = user;
    }

    public ApiUserDetails(String username, List<String> roles) {
        user = new User();
        user.setUserName(username);

        for (String role : roles) {
            user.getRoles().add(new Role(role));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: user.getRoles()) {
            if(role.isActive()) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

//    Remark: changed this to just username, because it's just username in the SQL backend
//    @Override
//    public String getUsername() {
//        return  user.getId() + "::" + user.getUserName();
//    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isExpired();
    }
    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isAreCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
