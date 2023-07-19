package com.group6.moneymanagementbooking.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group6.moneymanagementbooking.enity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select name from Category where name = ?1 and user_id = ?2")
    public Optional<Category> findByNameAndUser_id(String name, int id);

    @Query("select c from Category c where c.name = ?1 and c.user_id = ?2")
    public Optional<Category> findByName(String name, int id);

    @Query("select c from Category c where c.income_or_expense = 1 and c.user_id= ?1")
    public List<Category> findIncomeInCategory(int id);

    @Query("select c from Category c where c.income_or_expense = 0 and c.user_id= ?1")
    public List<Category> findExpenseInCategory(int id);

    @Modifying
    @Transactional
    @Query("UPDATE Category SET budget = ?1 WHERE name = ?2")
    public void updateCategory(double budget, String name);

    @Query("SELECT c FROM Category c WHERE c.user_id = :user_id")
    public List<Category> findAllByUserId(@Param("user_id") int userId);

}
