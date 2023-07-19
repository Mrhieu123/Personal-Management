package com.group6.moneymanagementbooking.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ui.Model;

import com.group6.moneymanagementbooking.enity.Category;

public interface CategoryService {
    public Category addCategory(Category category, Model model);

    public List<Category> findAll();

    public Optional<Category> getCategory(int id);

    public void deleteCategoryById(int id);

    public List<Category> findIncomeInCategory();

    public List<Category> findExpenseInCategory();

    public Optional<Category> findByName(String name);

    public void updateCategory(double budget, String name);

    public Category updateCategory(Category category);

    public double calculateTotalExpenses(Category category);

    public Map<String, Double> getCategoryTotalExpenses(Collection<Category> categories);

    public Map<String, Integer> getExpenseCount(Collection<Category> categories);

    public Map<String, Double> getCategoriesInfo(List<Category> categories);

}
