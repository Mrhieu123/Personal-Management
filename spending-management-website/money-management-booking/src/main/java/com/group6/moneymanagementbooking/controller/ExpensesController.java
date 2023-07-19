package com.group6.moneymanagementbooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group6.moneymanagementbooking.enity.Expenses;
import com.group6.moneymanagementbooking.service.AccountsService;
import com.group6.moneymanagementbooking.service.CategoryService;
import com.group6.moneymanagementbooking.service.ExpensesService;
import com.group6.moneymanagementbooking.service.UsersService;
import com.group6.moneymanagementbooking.util.PaginationUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class ExpensesController {
    @Autowired
    private final ExpensesService expensesService;
    private final AccountsService accountsService;
    private final CategoryService categoryService;
    private final UsersService usersService;

    @PostMapping("/add-expenses")
    public String addExpense(@ModelAttribute Expenses addexpense, RedirectAttributes redirectAttributes) {
        try {
            expensesService.addExpenses(addexpense, redirectAttributes);
        } catch (Exception e) {
            redirectAttributes.addAttribute("report", e.getMessage());
        }
        return "redirect:/users/list-expenses";
    }

    @GetMapping("/list-expenses")
    public String index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int pageSize,
            Model model) {
        model.addAttribute("listexpenses", expensesService.findAll());
        model.addAttribute("record", expensesService.findAll().size());
        model.addAttribute("listaccount", accountsService.findByActive());
        model.addAttribute("listcategory", categoryService.findExpenseInCategory());
        model.addAttribute("addexpense", new Expenses());
        Pageable pageable = PaginationUtil.getPageable(page, pageSize);
        List<Expenses> items = expensesService.findAll();
        Page<Expenses> itemsPage = PaginationUtil.paginate(pageable, items);
        model.addAttribute("page", itemsPage);
        model.addAttribute("monthlySpending", usersService.getUsers().getMonthlySpending());
        model.addAttribute("totalExpenseByMonth", expensesService.getTotalAmountCurrentMonth());
        return "list-expenses";
    }

    @PostMapping("/detail-expense")
    public String detail(@ModelAttribute Expenses expense) {
        return Optional.ofNullable(expensesService.updateExpenses(expense)).map(t -> "redirect:/users/list-expenses")
                .orElse("failed");
    }

    @GetMapping("/delete-expense/{id}")
    public String delete(@PathVariable("id") int id) {
        expensesService.deleteById(id);
        return "redirect:/users/list-expenses";
    }

}
