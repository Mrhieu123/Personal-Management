package com.group6.moneymanagementbooking.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.dto.mapper.UsersMapper;
import com.group6.moneymanagementbooking.dto.request.UserDTOEditProfileRequest;
import com.group6.moneymanagementbooking.dto.request.UsersDTOForgotPasswordRequest;
import com.group6.moneymanagementbooking.dto.request.UsersDTORegisterRequest;
import com.group6.moneymanagementbooking.enity.Users;
import com.group6.moneymanagementbooking.repository.UsersRepository;
import com.group6.moneymanagementbooking.service.UsersService;
import com.group6.moneymanagementbooking.util.SecurityUtils;
import com.group6.moneymanagementbooking.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final int MAX_FAILED_ATTEMPTS = 5;
    private final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;

    // register
    @Override
    public void checkUserRegister(List<String> report, UsersDTORegisterRequest userDTORegister,
            HttpServletRequest request) throws Exception {
        registerCheckCondition(userDTORegister, report);
        if (report.size() == 0) {
            HttpSession session = request.getSession();

        }
    }

    @Override
    public void addUser(UsersDTORegisterRequest userDTORegister) {
        Users user = UsersMapper.toUsers(userDTORegister);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = usersRepository.save(user);
    }

    // update avatar
    @Override
    public void uploadAvatar(String avatar) {
        Users users = usersRepository.findByEmail(SecurityUtils.getCurrentUsername()).get();
        users.setAvatar(avatar);
        usersRepository.save(users);
    }

    // update_user
    @Override
    public void updateInfo(UserDTOEditProfileRequest userDTOEditProfile, HttpServletRequest request) throws Exception {
        Optional<Users> usersOptional = usersRepository.findByEmail(userDTOEditProfile.getEmail());
        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            users.setAddress(userDTOEditProfile.getAddress());
            users.setFirstName(userDTOEditProfile.getFirstName());
            users.setLastName(userDTOEditProfile.getLastName());
            users.setPhone(userDTOEditProfile.getPhone());
            if (!request.getSession().getAttribute("userFullName")
                    .equals(users.getFirstName() + " " + users.getLastName())) {
                HttpSession session = request.getSession();
                session.setAttribute("userFullName", users.getFirstName() + " " + users.getLastName());
            }
            try {
                usersRepository.save(users);
            } catch (Exception e) {
                throw new Exception("Update Infor: " + e.getMessage());
            }

        } else {
            throw new Exception("Data cannot be null");
        }
    }

    // change_password
    @Override
    public void checkChangePassword(String[] listPass) throws Exception {
        if (listPass[0].isEmpty() || listPass[1].isEmpty() || listPass[2].isEmpty()) {
            throw new Exception("All of input cannot be empty");
        }
        if (listPass[0].equals(listPass[1])) {
            throw new Exception("This new password is the same with your old password");
        }
        if (!passwordEncoder.matches(listPass[0], getUsers().getPassword())) {
            throw new Exception("Old password is incorrect");
        }
        if (!listPass[1].equals(listPass[2])) {
            throw new Exception("Password and re-password are not the same");
        }
        if (!StringUtils.checkPasswordValidate(listPass[1])) {
            throw new Exception("Password must conform to regex");
        }
    }

    @Override
    public void changePassword(String newPassword) {
        Users users = getUsers();
        users.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(users);
    }

    // forgot_password
    @Override
    public void checkForgotPassword(List<String> report, UsersDTOForgotPasswordRequest usersDTOForgotPasswordRequest) {
        String email = usersDTOForgotPasswordRequest.getEmail();
        Optional<Users> usersOptional = usersRepository.findByEmail(email);
        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            if (!StringUtils.checkPasswordValidate(usersDTOForgotPasswordRequest.getPassword())) {
                report.add(" Warning: Your new password must conform regex");
            }
            if (!usersDTOForgotPasswordRequest.getPassword()
                    .equals(usersDTOForgotPasswordRequest.getRepeatPassword())) {
                report.add(" Warning: Password and re-password are not the same");
            }
            if (usersDTOForgotPasswordRequest.getPassword().isEmpty()
                    || usersDTOForgotPasswordRequest.getRepeatPassword().isEmpty()) {
                report.add(" Warning: Input can not be empty");
            }
            if(usersDTOForgotPasswordRequest.getPassword().equals(users.getPassword())){
                report.add(" Warning: This new password is the same with your old password");
            }
            if (report.size() == 0) {
                users.setPassword(passwordEncoder.encode(usersDTOForgotPasswordRequest.getPassword()));
                usersRepository.save(users);
            }
        } else {
            report.add("Warning: Your account not exits");
        }

    }

    // get user by email
    @Override
    public Users getUserByEmail(String email) {
        if (usersRepository.findByEmail(email).isPresent()) {
            return usersRepository.findByEmail(email).get();
        }
        return null;
    }

    // check_condition
    // 1. check email condition
    @Override
    public void checkEmailCondition(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userEmail = request.getParameter("userEmail");
        try (PrintWriter out = response.getWriter()) {
            if (!userEmail.isEmpty() && StringUtils.patternMatchesEmail(userEmail,
                    "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}(.[a-z]{2,3})+$|^$")) {
                if (checkEmailDuplicate(userEmail)) {
                    out.println("<p class='alert alert-danger text-center mess' >Your email already exist!!!</p>");
                } else {
                    out.println(
                            "<p class='alert alert-success text-center mess' >Your email address is accepted!!!</p>");
                }
            } else {
                out.println("<p  class='alert alert-danger text-center mess'>Your email is invalid!!!</p>");
            }
        }
    }

    // function for security
    public void increaseFailedAttempt(Users users) {
        int fa = users.getFailed_attempt() + 1;
        users.setFailed_attempt(fa);
        usersRepository.save(users);
    }

    public void lock(Users users) {
        users.setNonLocked(false);
        users.setLockTime(new Date());
        usersRepository.save(users);
    }

    public boolean unlock(Users users) {
        long lockTimeInMillis = users.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            users.setNonLocked(true);
            users.setLockTime(null);
            users.setFailed_attempt(0);
            return true;
        }
        return false;
    }

    public void checkUnLockUser(Users users, HttpServletResponse response) throws IOException {
        if (users.getFailed_attempt() < (MAX_FAILED_ATTEMPTS - 1)) {
            increaseFailedAttempt(users);
            response.sendRedirect("/login?error=login-fail&turn=" + (MAX_FAILED_ATTEMPTS - users.getFailed_attempt()));
        } else {
            lock(users);
            response.sendRedirect("/login?error=login-fail&turn=0");
        }
    }

    // support function
    private boolean checkEmailDuplicate(String email) {
        Optional<Users> accouOptional = usersRepository.findByEmail(email);
        if (!accouOptional.isPresent()) {

            return false;
        }
        return true;
    }

    private void registerCheckCondition(UsersDTORegisterRequest accountDTORegister, List<String> report) {
        String pass = accountDTORegister.getPassword();
        if (checkEmailDuplicate(accountDTORegister.getEmail())) {
            report.add("This email already exits!!");
        }
        if (!StringUtils.isValidEmail(accountDTORegister.getEmail())) {
            report.add("This email is not valid!!");
        }
        if (!StringUtils.checkPasswordValidate(pass)) {
            report.add("Password must conform to regex!!");
        }

        if (!accountDTORegister.getPhone().isEmpty() &&
                !StringUtils.isDigit(accountDTORegister.getPhone())) {
            report.add("Phone number must not have character!!");

        }
        if (!pass.equals(accountDTORegister.getRepeatPassword())) {
            report.add("Password and re-password are not the same!!");
        }
        if (pass.isEmpty() || accountDTORegister.getRepeatPassword().isEmpty()) {
            report.add("Password/Re-password cannot be empty!!");
        }
    }

    @Override
    public Users getUsers() {
        return usersRepository.findByEmail(SecurityUtils.getCurrentUsername()).get();
    }

    @Override
    public void addAdjustForUser(Users users, Model model) {
        try {
            Optional<Users> existingUser = usersRepository.findByEmail(SecurityUtils.getCurrentUsername());
            if (existingUser.isPresent()) {
                Users userToUpdate = existingUser.get();
                double monthlySpending = users.getMonthlySpending();
                double monthlyEarning = users.getMonthlyEarning();
                double monthlySaveing = users.getMonthlySaving();
                double getAnnuallySpending = users.getAnnuallySpending();
                if (monthlySpending < 0)
                    throw new Exception("monthlySpending must be greater than 0");
                if (monthlyEarning < 0)
                    throw new Exception("monthlyEarning must be greater than 0");
                if (monthlySaveing > monthlyEarning - monthlySpending) {
                    throw new Exception("Monthly Saving set must be small or equal to the money earned minus spending");
                }
                userToUpdate.setAnnuallySpending(getAnnuallySpending);
                userToUpdate.setMonthlySpending(monthlySpending);
                userToUpdate.setMonthlySaving(monthlySaveing);
                userToUpdate.setMonthlyEarning(monthlyEarning);
                usersRepository.save(userToUpdate);
            }
        } catch (Exception e) {
            model.addAttribute("mess", e.getMessage());
        }
    }
    @Override
    public double getMonthlySpending(){
        return getUsers().getMonthlySpending();
    }

}
