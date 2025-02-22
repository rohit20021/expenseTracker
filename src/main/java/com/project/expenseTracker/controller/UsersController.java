package com.project.expenseTracker.controller;

import com.project.expenseTracker.Repository.entity.User;
import com.project.expenseTracker.dto.ChangePassReq;
import com.project.expenseTracker.dto.SignupDto;
import com.project.expenseTracker.dto.UserInfo;
import com.project.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SignupDto userDetails) {
        return userService.saveUser(userDetails);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String userId){
        User user=userService.getUserByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserInfo.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .build());
    }

    @PostMapping("/changePassword/{userId}")
    public ResponseEntity<Void> changePass(@RequestBody ChangePassReq changePassReq,
                                       @PathVariable String userId){
        userService.changePassword(userId,changePassReq);
        return ResponseEntity
                .ok().build();
    }

}
