package org.inventory.management.server.service.auth;

import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.model.employee.UserPrinciple;
import org.inventory.management.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceIml implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Employee employee = (userRepository.findUserByUsername(username)).
               orElseThrow(() -> new UsernameNotFoundException(username));
       return new UserPrinciple(employee);
    }
}
