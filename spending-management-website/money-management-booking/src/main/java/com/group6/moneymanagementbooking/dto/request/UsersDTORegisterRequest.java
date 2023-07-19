package com.group6.moneymanagementbooking.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTORegisterRequest {

    private String email;
    private String password;
    private String repeatPassword;
    private String phone;
    private String address;
    

    
}
