package com.project.expenseTracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class AuthRequestDto {
    String userId;
    String name;
    String email;
    String password;
    String accessType;
}
