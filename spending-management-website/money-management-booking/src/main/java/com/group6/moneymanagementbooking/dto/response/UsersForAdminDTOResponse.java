package com.group6.moneymanagementbooking.dto.response;

import java.util.Date;

import lombok.Builder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsersForAdminDTOResponse {
    private int id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String role;

    private boolean active;
    
    private int failedAttempt;

    private boolean accountNonLocked;

    private Date lockTime;

}
