package com.movie.auth_service.security;

import com.movie.auth_service.entity.Role;
import com.movie.auth_service.entity.User;
import com.movie.auth_service.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * Convert Roles into Spring Security Authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getUserRoles()
                .stream()
                .map(UserRole::getRole)
                .map(Role::getName)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    /**
     * Username for Login
     * We are using email as username.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Encoded Password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Account Expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Account Locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Credentials Expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * User Enabled
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    /**
     * Getter for actual User entity
     */
    public User getUser() {
        return user;
    }
}