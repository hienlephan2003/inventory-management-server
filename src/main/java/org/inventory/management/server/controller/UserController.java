package org.inventory.management.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inventory.management.server.entity.User;
import org.inventory.management.server.model.user.RegisterUserModel;
import org.inventory.management.server.model.user.SignInUserModel;
import org.inventory.management.server.model.user.UserModelRes;
import org.inventory.management.server.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByUserId(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserModelRes> signUp(@RequestBody RegisterUserModel createUserRequest){
        return ResponseEntity.ok(userService.createNewUser(createUserRequest));
    }
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  ResponseEntity<UserModelRes> signIn(@RequestBody SignInUserModel userModel) {
        return ResponseEntity.ok(userService.signIn(userModel));
    }
}
