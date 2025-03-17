package com.example.management.auth;

import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.example.management.entity.Users;
import com.example.management.service.UserService;

public class AuthUtils {
    // public static Users getAuthenticationUser(Authentication authentication, UserService userService) {
    //     String email = authentication.getName();
    //     Optional<Users> optionalUser = userService.findByEmail(email);
    //     if(optionalUser.isEmpty()){
    //         throw new RuntimeException("User not found");
    //     }
    //     return optionalUser.get();
    // }
}
