package com.sid.dto;

import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails extends AppUser implements UserDetails {

    public CustomUserDetails(AppUser user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getRoles().stream().map(AppRole::getRoleName).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isActive();
    }

    @Override
    public boolean isEnabled() {
        return super.isActive();
    }

    public final String getId() {
        return super.getUserId();
    }
}
