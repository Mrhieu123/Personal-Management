package com.group6.moneymanagementbooking.controller;

import com.group6.moneymanagementbooking.dto.response.UsersForAdminDTOResponse;
import com.group6.moneymanagementbooking.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adminHomePage() {
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();
        when(adminService.getPageGroupOfUsers(model, 1)).thenReturn(groupOfUsers);

        adminController.adminHomePage(model);
        verify(adminService, times(1)).getPageGroupOfUsers(model, 1);
    }

    @Test
    void adminHomePageWithPageNumber() {
        String pagging = "page2";
        int page = 2;
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();

        when(adminService.getPageGroupOfUsers(model, page)).thenReturn(groupOfUsers);

        adminController.adminHomePage(pagging, model);

        verify(adminService, times(1)).getPageGroupOfUsers(model, page);

    }

    @Test
    void testSearchUsers() {
        String searchBy = "name";
        String value = "1";
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();

        when(adminService.searchUsers(model, searchBy, value, 1)).thenReturn(groupOfUsers);

        adminController.search(model, searchBy, value, null, null);

        verify(adminService, times(1)).searchUsers(model, searchBy, value, 1);

    }

    @Test
    void testSearchWithPageNumber() {
        String pagging = "page2";
        String searchBy = "select";
        String value = "example value";
        int page = 2;
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();

        when(adminService.searchUsers(model, searchBy, value, page)).thenReturn(groupOfUsers);

        adminController.search(model, pagging, searchBy, value, null, null);

        verify(adminService, times(1)).searchUsers(model, searchBy, value, page);

    }

    @Test
    void testDeleteUser() throws Exception {
        int id = 123;
        HttpServletResponse response = mock(HttpServletResponse.class);

        adminController.changeUserActiveStatus(response, model, String.valueOf(id));

        verify(adminService, times(1)).changeActiveStatus(response, id);
    }

    @Test
    void testGetGroupUsersLocked() {
        boolean isLocked = true;
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();

        when(adminService.getGroupOfLockedUser(model, isLocked, 1)).thenReturn(groupOfUsers);

        adminController.getGroupsOfLockedUsers(isLocked, model);

        verify(adminService, times(1)).getGroupOfLockedUser(model, isLocked, 1);
        ;
    }

    @Test
    void testGetListOfActiveUsers() {
        boolean isActive = true;
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();

        when(adminService.getGroupOfActiveUsers(model, isActive, 1)).thenReturn(groupOfUsers);

        adminController.getGroupOfActiveUsers(isActive, model);

        verify(adminService, times(1)).getGroupOfActiveUsers(model, isActive, 1);

    }

    @Test
    void testGetUsersLockedByPage() {
        boolean isLocked = true;
        String pagging = "page2";
        int page = 2;
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();

        when(adminService.getGroupOfLockedUser(model, isLocked, page)).thenReturn(groupOfUsers);

        adminController.getGroupsOfLockedUsers(isLocked, model, pagging);

        verify(adminService, times(1)).getGroupOfLockedUser(model, isLocked, page);

    }

    @Test
    void testGetUsersActiveByPage() {
        boolean isActive = true;
        String pagging = "page2";
        int page = 2;
        List<UsersForAdminDTOResponse> groupOfUsers = new ArrayList<>();

        when(adminService.getGroupOfActiveUsers(model, isActive, page)).thenReturn(groupOfUsers);

        adminController.getGroupOfActiveUsers(isActive, model, pagging);

        verify(adminService, times(1)).getGroupOfActiveUsers(model, isActive, page);

    }

}
