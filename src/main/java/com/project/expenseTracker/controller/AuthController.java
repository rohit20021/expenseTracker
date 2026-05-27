package com.project.expenseTracker.controller;

import com.project.expenseTracker.dto.AuthRequestDto;
import com.project.expenseTracker.dto.AuthResponseDto;
import com.project.expenseTracker.dto.VerifyTokenRequestDto;
import com.project.expenseTracker.dto.VerifyTokenResponseDto;
import com.project.expenseTracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsersController usersController;

    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto userDetails){
        var accessToken=authService.signUp(userDetails);
        // todo store pass in hash

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AuthResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }

    @PostMapping("/logIn")
    public ResponseEntity<AuthResponseDto> logIn(@RequestBody AuthRequestDto userDetails){
        var accessToken=authService.logIn(userDetails);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<VerifyTokenResponseDto> tokenVerification(@RequestBody VerifyTokenRequestDto token){
        var userId=authService.verifyToken(token);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(VerifyTokenResponseDto.builder()
                        .userId(userId)
                        .build());
    }

}
