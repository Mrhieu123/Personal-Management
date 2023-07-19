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

import com.group6.moneymanagementbooking.enity.Income;
import com.group6.moneymanagementbooking.service.AccountsService;
import com.group6.moneymanagementbooking.service.CategoryService;
import com.group6.moneymanagementbooking.service.IncomeService;
import com.group6.moneymanagementbooking.service.UsersService;
import com.group6.moneymanagementbooking.util.PaginationUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class IncomeController {
    @Autowired
    private final IncomeService incomeService;
    private final AccountsService accountsService;
    private final CategoryService categoryService;
    private final UsersService usersService;

    @PostMapping("/add-income")
    public String addIncome(@ModelAttribute Income income, RedirectAttributes redirectAttributes) {
        incomeService.addIncome(income, redirectAttributes);
        return "redirect:/users/list-income";
    }

    @GetMapping("/list-income")
    public String index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int pageSize,
            Model model) {
        model.addAttribute("listincome", incomeService.findAll());
        model.addAttribute("record", incomeService.findAll().size());
        model.addAttribute("listaccount", accountsService.findByActive());
        model.addAttribute("listcategory", categoryService.findIncomeInCategory());
        model.addAttribute("addincome", new Income());
        Pageable pageable = PaginationUtil.getPageable(page, pageSize);
        List<Income> items = incomeService.findAll();
        Page<Income> itemsPage = PaginationUtil.paginate(pageable, items);
        model.addAttribute("page", itemsPage);
        model.addAttribute("monthlyEarning", usersService.getUsers().getMonthlyEarning());
        model.addAttribute("totalIncomeByMonth", incomeService.getTotalAmountCurrentMonth());
        return "list-income";
    }

    @PostMapping("/detail-income")
    public String detail(@ModelAttribute Income addincome) {
        return Optional.ofNullable(incomeService.updateIncome(addincome)).map(t -> "redirect:/users/list-income")
                .orElse("failed");
    }

    @GetMapping("/delete-income/{id}")
    public String delete(@PathVariable("id") int id) {
        incomeService.deleteById(id);
        return "redirect:/users/list-income";
    }
}
