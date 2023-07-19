package com.group6.moneymanagementbooking.Junit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.controller.IncomeController;
import com.group6.moneymanagementbooking.enity.Income;
import com.group6.moneymanagementbooking.service.AccountsService;
import com.group6.moneymanagementbooking.service.CategoryService;
import com.group6.moneymanagementbooking.service.IncomeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;
public class IncomeControllerTest {
    @Mock
    private IncomeService incomeService;

    @Mock
    private AccountsService accountsService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @InjectMocks
    private IncomeController incomeController;

    @BeforeEach
    void setUp() {
    }

    // @Test
    // void testAddIncome() {
    //     Income income = new Income();
    //     when(incomeService.addIncome(income)).thenReturn(income);

    //     String result = incomeController.addIncome(income);

    //     assertEquals("redirect:/list-income", result);
    //     verify(incomeService, times(1)).addIncome(income);
    // }

    @Test
    void testDeleteIncome() {
        int id = 1;

        String result = incomeController.delete(id);

        assertEquals("redirect:/list-income", result);
        verify(incomeService, times(1)).deleteById(id);
    }
  @Test
    void testGetIncomeDetail() {
        int id = 1;
        Income income = new Income();
        when(incomeService.getIncome(id)).thenReturn(Optional.of(income));

        // String result = incomeController.detail(id, model);

        // assertEquals("detail-income", result);
        // verify(incomeService, times(1)).getIncome(id);
    }

        @Test
    void testUpdateIncome() {
        Income income = new Income();
        when(incomeService.updateIncome(income)).thenReturn((income));

        String result = incomeController.detail(income);

        assertEquals("redirect:/list-income", result);
        verify(incomeService, times(1)).updateIncome(income);
    }

}
