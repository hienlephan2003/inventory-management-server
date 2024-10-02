package org.inventory.management.server.service.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import org.inventory.management.server.entity.User;
import org.inventory.management.server.exception.DataNotFoundException;
import org.inventory.management.server.exception.UserExistException;
import org.inventory.management.server.model.user.RegisterUserModel;
import org.inventory.management.server.model.user.Role;
import org.inventory.management.server.model.user.SignInUserModel;
import org.inventory.management.server.model.user.UserModelRes;
import org.inventory.management.server.repository.UserJpaRepository;
import org.inventory.management.server.service.auth.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserModelRes createNewUser(RegisterUserModel createUserRequest) throws UserExistException {
        if (!userJpaRepository.existsUserByUsername(createUserRequest.getUsername())) {
            User newUser = User.builder()
                    .fullName(createUserRequest.getFullname())
                    .password(passwordEncoder.encode(createUserRequest.getPassword()))
                    .username(createUserRequest.getUsername())
                    .role(Role.USER_ROLE)
                    .build();
            User user = userJpaRepository.save(newUser);
            return modelMapper.map(user, UserModelRes.class);
        } else{
            throw new UserExistException("User exists!");
        }
    }
    
    @Override
    public UserModelRes signIn(SignInUserModel userModel) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
        if(!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Authentication failed");
        }
        User user = userJpaRepository.findUserByUsername(userModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User is not exists"));
        String token = authService.createToken(userModel.getUsername(), user.getId());
        UserModelRes userModelRes = modelMapper.map(user, UserModelRes.class);
        userModelRes.setAccessToken(token);
        return userModelRes;
    }

    @Override
    public User getUserByUsername(String username) {
        return (userJpaRepository.findUserByUsername(username)).orElseThrow(()-> new DataNotFoundException("User is not exists"));
    }

    @Override
    public User findById(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found user"));
    }
}

