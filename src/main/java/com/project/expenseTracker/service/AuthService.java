package com.project.expenseTracker.service;

import com.project.expenseTracker.Repository.UserRepository;
import com.project.expenseTracker.Repository.entity.User;
import com.project.expenseTracker.dto.*;
import com.project.expenseTracker.exceptions.InvalidCredentialsException;
import com.project.expenseTracker.exceptions.InvalidTokenException;
import com.project.expenseTracker.exceptions.UserAlreadyExistsException;
import com.project.expenseTracker.exceptions.UserNotFoundException;
import com.project.expenseTracker.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public String  signUp(AuthRequestDto userDetails){
        SignupDto signUpDetails = SignupDto.builder()
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .userId(userDetails.getUserId())
                .build();

        var accessToken="";
        try {
            userService.saveUser(signUpDetails);
            accessToken= jwtUtils.generateAccessToken(userDetails.getEmail());
        } catch (UserAlreadyExistsException e) {
            log.error("User already exists: {}", e.getMessage());
            throw e;
        }
        return accessToken;
    }

    @Transactional
    public String logIn(AuthRequestDto userDetails){
        LogInDto logInDetails = LogInDto.builder()
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .build();

        User user = userRepository.findByEmail(logInDetails.getEmail())
                .orElseThrow(() -> {
                    return new UserNotFoundException("User not found ", "e409");
                });
        if(!user.getPasswordHash().equals(logInDetails.getPassword())){
            throw new InvalidCredentialsException("Invalid Credentials","e401");
        }

        var accessToken= jwtUtils.generateAccessToken(logInDetails.getEmail());
        return accessToken;
    }

    public String verifyToken(VerifyTokenRequestDto tokenRequestDto) {
       return jwtUtils.getUserNameFromJwtToken(tokenRequestDto.getAccessToken()).orElseThrow(()-> new InvalidTokenException("Invalid Credentials","T401"));
    }
}
