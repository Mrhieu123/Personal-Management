package com.group6.moneymanagementbooking.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.group6.moneymanagementbooking.enity.Users;
import com.group6.moneymanagementbooking.repository.UsersRepository;
import com.group6.moneymanagementbooking.security.CustomUserDetailsService;
import com.group6.moneymanagementbooking.security.MyUserDetails;
import com.group6.moneymanagementbooking.service.impl.UsersServiceImpl;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsersServiceImpl usersServiceImpl;
    private final UsersRepository usersRepository;

    public CustomAuthenticationFailureHandler(CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder, UsersServiceImpl usersServiceImpl, UsersRepository usersRepository) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.usersServiceImpl = usersServiceImpl;
        this.usersRepository = usersRepository;

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            // Kiểm tra trạng thái isEnabled của người dùng
            MyUserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails.isEnabled()) {
                // Xử lý các trường hợp khác khi đăng nhập không thành công
                Users users = usersRepository.findByEmail(username).get();
                loginLimitAttem(password, users, request, response, exception);
            } else {
                // Người dùng bị vô hiệu hóa
                response.sendRedirect("/login?error=disabled");
            }
        } catch (UsernameNotFoundException e) {
            // Người dùng không tồn tại
            response.sendRedirect("/login?error=true");
        } catch (InternalAuthenticationServiceException iase) {
            response.sendRedirect("/login?error=true");
        } catch (Exception ex) {
            response.sendRedirect("/login?error=true");
        }
    }

    private void loginLimitAttem(String password, Users users, HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        if (passwordEncoder.matches(password, users.getPassword())) {
            if (users.isNonLocked()) {
                super.onAuthenticationFailure(request, response, exception);
            } else {
                if (usersServiceImpl.unlock(users)) {
                    super.onAuthenticationFailure(request, response, exception);
                } else {
                    response.sendRedirect("/login?error=login-fail&turn=0");
                }
            }
        } else {
            if (users.isNonLocked()) {
                usersServiceImpl.checkUnLockUser(users, response);
            } else {
                if (usersServiceImpl.unlock(users)) {
                    usersServiceImpl.checkUnLockUser(users, response);
                } else {
                    response.sendRedirect("/login?error=login-fail&turn=0");
                }
            }
        }
    }

}