package com.project.expenseTracker.dto;

import jakarta.persistence.Access;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyTokenRequestDto {
    String accessToken;
}
