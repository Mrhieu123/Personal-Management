package com.group6.moneymanagementbooking.service;

import com.group6.moneymanagementbooking.dto.request.UserDTOEditProfileRequest;

public interface EditProfileService {

   public UserDTOEditProfileRequest getUserByEmail(String email);

   public void updateInfo(UserDTOEditProfileRequest userDTOEditProfile);

}
