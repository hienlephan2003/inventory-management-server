package org.inventory.management.server.model.employee;

import org.inventory.management.server.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrinciple implements UserDetails {
    String name;
    String password;
    long userId;
    List<GrantedAuthority> authorities;

    public UserPrinciple(Employee employee){
        this.name = employee.getName();
        this.password = employee.getPassword();
        this.userId = employee.getId();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(employee.getRole().name()));
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
        return name;
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

    public Long getUserId() {
        return userId;
    }
}
