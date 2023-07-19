package com.group6.moneymanagementbooking.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.dto.request.UserDTOEditProfileRequest;
import com.group6.moneymanagementbooking.dto.request.UsersDTOForgotPasswordRequest;
import com.group6.moneymanagementbooking.dto.request.UsersDTORegisterRequest;
import com.group6.moneymanagementbooking.enity.Users;

public interface UsersService {

  public void checkUserRegister(List<String> report, UsersDTORegisterRequest accountDTORegister,
      HttpServletRequest request)
      throws Exception;

  public void checkEmailCondition(HttpServletRequest request, HttpServletResponse response)
      throws IOException;

  public void addUser(UsersDTORegisterRequest userDTORegister);

  public void checkForgotPassword(List<String> report, UsersDTOForgotPasswordRequest usersDTOForgotPasswordRequest);

  public Users getUserByEmail(String email);

  public void changePassword(String newPassword);

  public Users getUsers();

  public void addAdjustForUser(Users users, Model model);

  public void uploadAvatar(String avatar);

  public void updateInfo(UserDTOEditProfileRequest userDTOEditProfile, HttpServletRequest request) throws Exception;

  public void checkChangePassword(String[] newPass) throws Exception;

  public double getMonthlySpending();

}
