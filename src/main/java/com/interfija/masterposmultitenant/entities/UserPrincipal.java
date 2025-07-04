package com.interfija.masterposmultitenant.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private final String username;
    private final String password;
    private final String tenantId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    // Constructor para cuando no tienes password (ej: desde JWT)
    public UserPrincipal(String username, String tenantId, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = null;
        this.tenantId = tenantId;
        this.authorities = authorities;
        this.enabled = true;
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
        return enabled;
    }
}