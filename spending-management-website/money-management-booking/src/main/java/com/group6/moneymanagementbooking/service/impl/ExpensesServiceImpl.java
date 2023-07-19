package com.group6.moneymanagementbooking.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.Comparator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.enity.Expenses;
import com.group6.moneymanagementbooking.enity.Users;
import com.group6.moneymanagementbooking.repository.AccountsRepository;
import com.group6.moneymanagementbooking.repository.ExpensesRepository;
import com.group6.moneymanagementbooking.repository.UsersRepository;
import com.group6.moneymanagementbooking.service.AccountsService;
import com.group6.moneymanagementbooking.service.ExpensesService;
import com.group6.moneymanagementbooking.service.UsersService;
import com.group6.moneymanagementbooking.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private final ExpensesRepository expensesRepository;
    private final AccountsRepository accountsRepository;
    private final AccountsService accountsService;
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @Override
    public Expenses addExpenses(Expenses expenses, Model model) throws Exception {
        expenses.setUserId(usersRepository.findByEmail(SecurityUtils.getCurrentUsername()).get().getId());
        double amountExpense = expenses.getAmount();
        double balanceAccount = accountsRepository.findById(expenses.getAccounts().getId()).get().getBalance();
        if (amountExpense <= 0)
            throw new Exception("Amount must be greater than 0");
        if (balanceAccount - amountExpense < 0) {
            model.addAttribute("report", "Warning account with amount less than 0");
        }

        accountsRepository.addBalanceById(balanceAccount - amountExpense, expenses.getAccounts().getId());
        return expensesRepository.save(expenses);
    }

    @Override
    public List<Expenses> findAll() {
        List<Expenses> listExpenses = expensesRepository
                .findAllByUserId(usersRepository.findByEmail(SecurityUtils.getCurrentUsername()).get().getId());
        // Sắp xếp danh sách theo trường ngày
        Collections.sort(listExpenses, new Comparator<Expenses>() {
            @Override
            public int compare(Expenses expenses1, Expenses expenses2) {
                return Long.compare(expenses2.getId(), expenses1.getId());
            }
        });
        return listExpenses;
    }

    @Override
    public Optional<Expenses> getExpense(int id) {
        return expensesRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        Optional<Expenses> expenses = expensesRepository.findById(id);
        accountsService.addBalance(expenses.get().getAmount(), expenses.get().getAccounts().getId());
        expensesRepository.deleteById(id);
    }

    @Override
    public Expenses updateExpenses(Expenses expenses) {
        try {
            expenses.setUserId(usersRepository.findByEmail(SecurityUtils.getCurrentUsername()).get().getId());
            double updateMoney = expenses.getAmount();
            double currentMoney = getExpense(expenses.getId()).get().getAmount();
            double balanceAccount = accountsRepository.findById(expenses.getAccounts().getId()).get().getBalance();
            if (expenses.getAmount() < 0)
                throw new Exception("Amount must be > 0");
            if (balanceAccount - updateMoney < 0)
                System.out.println("Warning Balance <0");
            if (expenses.getAccounts().getId() == getExpense(expenses.getId()).get().getAccounts().getId()) {
                accountsService.expenseBalance(updateMoney - currentMoney, expenses.getAccounts().getId());
                return expensesRepository.save(expenses);
            }
            accountsService.expenseBalance(-currentMoney, getExpense(expenses.getId()).get().getAccounts().getId());
            accountsService.expenseBalance(updateMoney, expenses.getAccounts().getId());
            return expensesRepository.save(expenses);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    public List<Expenses> getExpensesByMonth(int month, int year) {
        return expensesRepository.findByMonth(month, year);
    }

    @Override
    public double totalExpenseInMonth() {
        double total = 0;
        try {
            List<Expenses> list = getExpensesByMonth(LocalDate.now().getMonth().getValue(), LocalDate.now().getYear());
            for (Expenses item : list) {
                total += item.getAmount();
            }
            return total;
        } catch (Exception e) {
            e.getMessage();
        }
        return total;
    }

    @Override
    public int getCountByTitle(String name) {
        return expensesRepository.countByTitle(name);
    }

    @Override
    public Map<String, Double> calculateMonthlyExpenses(List<Expenses> expenses) {
        Map<String, Double> monthlyExpenses = new HashMap<>();

        for (Expenses expense : expenses) {
            String monthKey = getMonthKey(expense.getExpenseDate());
            double currentAmount = monthlyExpenses.getOrDefault(monthKey, 0.0);
            currentAmount += expense.getAmount();
            if (expense.getExpenseDate().toLocalDate().getYear() == LocalDate.now().getYear()) {
                monthlyExpenses.put(monthKey, currentAmount);
            }
        }
        // Sort Map By String (Month)
        Map<String, Double> sortedMonthlyExpenses = new LinkedHashMap<>();
        monthlyExpenses.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> sortedMonthlyExpenses.put(entry.getKey(), entry.getValue()));

        return sortedMonthlyExpenses;
    }

    @Override
    public double getTotalAmountCurrentMonth() {
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Lấy tháng và năm hiện tại
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Lấy ngày đầu tiên và cuối cùng của tháng hiện tại
        LocalDate firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1);
        LocalDate lastDayOfMonth = YearMonth.of(currentYear, currentMonth).atEndOfMonth();

        // Chuyển đổi LocalDate thành java.sql.Date
        Date firstDateOfMonth = Date.valueOf(firstDayOfMonth);
        Date lastDateOfMonth = Date.valueOf(lastDayOfMonth);
        Users users = usersService.getUsers();
        // Lấy tổng số tiền của các expense trong tháng hiện tại
        List<Expenses> expensesList = expensesRepository.findByExpenseDateBetweenAndUserId(firstDateOfMonth,
                lastDateOfMonth, users.getId());
        double totalAmount = expensesList.stream().mapToDouble(Expenses::getAmount).sum();

        return totalAmount;
    }

    private String getMonthKey(Date date) {
        // Format month key as "yyyy-MM" (e.g., "2023-06")
        return date.toString().substring(0, 7);
    }
}