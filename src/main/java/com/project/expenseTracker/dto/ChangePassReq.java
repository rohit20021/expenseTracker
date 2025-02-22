package com.project.expenseTracker.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangePassReq {
    private String oldPass;
    private String newPass;
}
