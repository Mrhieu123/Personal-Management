package com.group6.moneymanagementbooking.Junit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.controller.ExpensesController;
import com.group6.moneymanagementbooking.enity.Expenses;
import com.group6.moneymanagementbooking.service.AccountsService;
import com.group6.moneymanagementbooking.service.CategoryService;
import com.group6.moneymanagementbooking.service.ExpensesService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ExpensesControllerTest {
    @Mock
    private ExpensesService expensesService;

    @Mock
    private AccountsService accountsService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Model model;

    @InjectMocks
    private ExpensesController expensesController;

    @BeforeEach
    void setUp() {
    }

    // @Test
    // void testAddExpense() {
    //     Expenses expense = new Expenses();
    //     when(expensesService.addExpenses(expense)).thenReturn((expense));

    //     String result = expensesController.addExpense(expense);

    //     assertEquals("redirect:/list-expenses", result);
    //     verify(expensesService, times(1)).addExpenses(expense);
    // }

    @Test
    void testDeleteExpense() {
        int id = 1;

        String result = expensesController.delete(id);

        assertEquals("redirect:/list-expenses", result);
        verify(expensesService, times(1)).deleteById(id);
    }

        @Test
    void testUpdateExpense() {
        Expenses expense = new Expenses();
        when(expensesService.updateExpenses(expense)).thenReturn((expense));

        String result = expensesController.detail(expense);

        assertEquals("redirect:/list-expenses", result);
        verify(expensesService, times(1)).updateExpenses(expense);
    }

    @Test
    void testDetailExpense() {
        int id = 1;
        Expenses expense = new Expenses();
        when(expensesService.getExpense(id)).thenReturn(Optional.of(expense));

        // String result = expensesController.detail(id, model);

        // assertEquals("detail-expenses", result);
        // verify(expensesService, times(1)).getExpense(id);
        // verify(model, times(1)).addAttribute("listaccount", accountsService.findByActive());
        // verify(model, times(1)).addAttribute("listcategory", categoryService.findExpenseInCategory());
    }
}
