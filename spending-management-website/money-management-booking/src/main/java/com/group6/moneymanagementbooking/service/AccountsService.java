package com.group6.moneymanagementbooking.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.enity.Accounts;

public interface AccountsService {
    public Accounts addAccounts(Accounts accounts, Model model) throws Exception;

    public List<Accounts> findAll();

    public void updateActiveById(boolean action, int id);

    public void addBalance(double balance, int id);

    public void expenseBalance(double balance, int id);

    public Optional<Accounts> findOptinalById(int id);

    public List<Accounts> findByActive();

    public Accounts updateAccount(Accounts accounts, Model model) throws Exception;

    public void deleteById(int id);

    public List<Accounts> findAllByUserId(int id);

    public Accounts findById(int id);

    public void changeActiveStatus(HttpServletResponse response, int id) throws IOException;

    public Map<String, Integer> getTransactionCount(Collection<Accounts> accounts);

    public Iterable<Accounts> getAllAccounts();

    public double getTotalBalance();

    public double getTotalExpenses();

    public double getTotalIncome();

}
