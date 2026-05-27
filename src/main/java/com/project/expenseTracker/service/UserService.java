package com.project.expenseTracker.service;

import com.project.expenseTracker.Repository.UserRepository;
import com.project.expenseTracker.Repository.entity.User;
import com.project.expenseTracker.dto.ChangePassReq;
import com.project.expenseTracker.dto.SignupDto;
import com.project.expenseTracker.exceptions.UserAlreadyExistsException;
import com.project.expenseTracker.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> saveUser(SignupDto userDetails) {
            // Validate userName
            if (userDetails.getUserName() == null || userDetails.getUserName().isEmpty() || userDetails.getEmail() == null || userDetails.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("User name  or email cannot be null or empty");
            }

            userRepository.findByEmail(userDetails.getEmail()).ifPresent(existingUser -> {
                throw new UserAlreadyExistsException("User already exists", "e409");
            });
        // Create User entity
            User user = User.builder()
                    .username(userDetails.getUserName())
                    .email(userDetails.getEmail())
                    .passwordHash(userDetails.getPassword())
                    .createdAt(LocalDateTime.now())
                    .build();

            // Save user to the database
            User savedUser = userRepository.save(user);

            // Return the saved user with an HTTP 200 OK status
            return ResponseEntity.ok(savedUser.toString());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("user not found","e404"));
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> {
            return new UserNotFoundException("user not found", "e404");
        });
    }

    public void changePassword(String userName, ChangePassReq changePassReq) {
        User user = getUserByUserName(userName);
        if(!user.getPasswordHash().equals(changePassReq.getOldPass())){
            throw new BadCredentialsException("Forbidden");
        }
        user.setPasswordHash(changePassReq.getNewPass());
        userRepository.save(user);
    }
}
