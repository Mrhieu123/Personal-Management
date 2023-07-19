package com.group6.moneymanagementbooking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.enity.Debtor;
import com.group6.moneymanagementbooking.service.DebtorService;
import com.group6.moneymanagementbooking.service.UsersService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class DebtorControllerTest {

    @Mock
    private DebtorService debtorService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private DebtorController debtorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNew() throws Exception {
        Debtor debtor = new Debtor();
        // debtor.setTotal(0.0);

        when(debtorService.Save(any(Debtor.class))).thenReturn(debtor);

        String viewName = debtorController.addNew(debtor);

        assertEquals("redirect:/Debtor/ListAll", viewName);
        verify(debtorService, times(1)).Save(any(Debtor.class));
    }

    @Test
    void testDeleteDebtor() {
        Debtor debtor = new Debtor();
        debtor.setTotal(0.0);
        when(debtorService.getDebtor(anyInt())).thenReturn(Optional.of(debtor));


        // String viewName = debtorController.deleteDebtor(model, 1);

        // assertEquals("redirect:/Debtor/ListAll", viewName);
        verify(debtorService, times(1)).getDebtor(anyInt());
        verify(debtorService, times(1)).deleteDebtorById(anyInt());
    }

    @Test
    void testUpdateDebtor() throws Exception {
        // Mock existing debtor
        Debtor existingDebtor = Debtor.builder()
                .id(1)
                .userId(1)
                .name("Debtor 1")
                .address("Ha Noi")
                .phone("0399876543")
                .email("hung@gmail.com")
                .date_create(LocalDateTime.now())
                .date_update(LocalDateTime.now())
                .total(0.0)
                .build();

        // Mock updated debtor
        Debtor updatedDebtor = Debtor.builder()
                .id(1)
                .userId(1)
                .name("Updated Debtor 1")
                .address("New Address")
                .phone("1234567890")
                .email("updated@gmail.com")
                .date_create(existingDebtor.getDate_create())
                .date_update(LocalDateTime.now())
                .total(100.0)
                .build();

        when(debtorService.getDebtor(anyInt())).thenReturn(Optional.of(existingDebtor));
        when(debtorService.Save(any(Debtor.class))).thenReturn(updatedDebtor);

        Model model = mock(Model.class);

        String viewName = debtorController.updateDebtor(model, updatedDebtor);

        assertEquals("redirect:/Debtor/ListAll", viewName);
        verify(debtorService, times(1)).getDebtor(anyInt());
        verify(debtorService, times(1)).Save(any(Debtor.class));

        // Verify that model attributes are set correctly
    }

    @Test
    void testReadDebtor() throws Exception {
        // Mock Debtor
        Debtor debtor = Debtor.builder()
                .id(1)
                .userId(1)
                .name("Debtor 1")
                .address("Ha Noi")
                .phone("0399876543")
                .email("hung@gmail.com")
                .date_create(LocalDateTime.now())
                .date_update(LocalDateTime.now())
                .total(0.0)
                .build();

        when(debtorService.getDebtor(anyInt())).thenReturn(Optional.of(debtor));

        int debtorId = 1;

        Optional<Debtor> retrievedDebtor = debtorService.getDebtor(debtorId);

        assertTrue(retrievedDebtor.isPresent());
        assertEquals(debtorId, retrievedDebtor.get().getId());
        assertEquals(debtor.getName(), retrievedDebtor.get().getName());
        // Assert other debtor properties as needed
    }

}
