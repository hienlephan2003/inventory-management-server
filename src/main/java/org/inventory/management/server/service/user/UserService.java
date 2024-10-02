package org.inventory.management.server.service.user;

import jakarta.security.auth.message.AuthException;
import org.inventory.management.server.entity.User;
import org.inventory.management.server.exception.DataNotFoundException;
import org.inventory.management.server.exception.UserExistException;
import org.inventory.management.server.model.user.RegisterUserModel;
import org.inventory.management.server.model.user.SignInUserModel;
import org.inventory.management.server.model.user.UserModelRes;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserModelRes createNewUser(RegisterUserModel createUserRequest) throws UserExistException;
    UserModelRes signIn(SignInUserModel signInRequest) throws UsernameNotFoundException;
    User getUserByUsername(String username) throws DataNotFoundException;
    User findById(Long userId);
}

