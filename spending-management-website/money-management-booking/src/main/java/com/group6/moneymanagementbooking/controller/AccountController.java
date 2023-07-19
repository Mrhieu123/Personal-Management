package com.group6.moneymanagementbooking.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

import com.group6.moneymanagementbooking.enity.Accounts;
import com.group6.moneymanagementbooking.enity.Expenses;
import com.group6.moneymanagementbooking.service.AccountsService;
import com.group6.moneymanagementbooking.service.ExpensesService;
import com.group6.moneymanagementbooking.util.PaginationUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class AccountController {
    @Autowired
    private final AccountsService accountsService;
    private final ExpensesService expensesService;

    @PostMapping("/add-account")
    public String addAccount(@ModelAttribute Accounts addaccounts, RedirectAttributes redirectAttributes) {
        try{
            accountsService.addAccounts(addaccounts, redirectAttributes);  
        }catch(Exception e){
          redirectAttributes.addAttribute("report", e.getMessage());
        }
        return "redirect:/users/overview";
    }

    @GetMapping("/overview")
    public String index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int pageSize,
            Model model) {
        model.addAttribute("listaccount", accountsService.findAll());
        model.addAttribute("record", accountsService.findAll().size());
        Pageable pageable = PaginationUtil.getPageable(page, pageSize);
        List<Accounts> items = accountsService.findAll();
        Page<Accounts> itemsPage = PaginationUtil.paginate(pageable, items);
        List<Accounts> listAccounts = accountsService.findAll();
        Map<String, Integer> accountsTransaction = accountsService.getTransactionCount(listAccounts);
        double totalIncome = accountsService.getTotalIncome();
        double totalExpenses = accountsService.getTotalExpenses();
        double totalBalance = accountsService.getTotalBalance();
        List<Expenses> expenses = expensesService.findAll();
        Map<String, Double> monthlyExpenses = expensesService.calculateMonthlyExpenses(expenses);
        model.addAttribute("data", monthlyExpenses);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("accountsTransaction", accountsTransaction);
        model.addAttribute("page", itemsPage);
        model.addAttribute("addaccounts", new Accounts());
        return "list-account";
    }

    @GetMapping("/list-account/{id}/{action}")
    public String updateActive(@PathVariable("id") int id, @PathVariable("action") Boolean action, Model model) {
        accountsService.updateActiveById(action, id);
        return "redirect:/users/overview";
    }

    @PostMapping("/detail-account")
    public String detail(@ModelAttribute Accounts account, RedirectAttributes redirectAttributes) {
        try {
            accountsService.updateAccount(account, redirectAttributes);
        } catch (Exception e) {
            redirectAttributes.addAttribute("mess", e.getMessage());
        }
        return "redirect:/users/overview";
    }

    @GetMapping("/delete-account/{id}")
    public String delete(@PathVariable("id") int id) {
        accountsService.deleteById(id);
        return "redirect:/users/overview";

    }

    @GetMapping("/change-status/{id}")
    public void changeUserActiveStatus(HttpServletResponse response, Model model, @PathVariable("id") String AccountId)
            throws IOException {
        int id = Integer.parseInt(AccountId);
        accountsService.changeActiveStatus(response, id);
    }

}
