package com.group6.moneymanagementbooking.security;


import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.group6.moneymanagementbooking.enity.Users;



public class MyUserDetails implements UserDetails {
    private Users account;

    public MyUserDetails(Users account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {

        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.isNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.isActive();
    }

}
