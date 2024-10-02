package org.inventory.management.server.service.auth;

import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.User;
import org.inventory.management.server.model.user.UserPrinciple;
import org.inventory.management.server.repository.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceIml implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = (userJpaRepository.findUserByUsername(username)).
               orElseThrow(() -> new UsernameNotFoundException(username));
       return new UserPrinciple(user);
    }
}
