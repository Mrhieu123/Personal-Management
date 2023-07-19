package com.group6.moneymanagementbooking.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTOEditProfileRequest {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String avatar;

}
