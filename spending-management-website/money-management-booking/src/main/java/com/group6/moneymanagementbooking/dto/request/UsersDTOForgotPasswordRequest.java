package com.group6.moneymanagementbooking.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsersDTOForgotPasswordRequest {
    private String email;
    private String password;
    private String repeatPassword;
}
