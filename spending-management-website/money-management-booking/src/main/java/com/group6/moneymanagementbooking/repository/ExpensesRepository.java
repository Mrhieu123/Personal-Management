package com.group6.moneymanagementbooking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.moneymanagementbooking.enity.Expenses;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

    @Query("SELECT e FROM Expenses e WHERE MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year")
    public List<Expenses> findByMonth(@Param("month") int month, @Param("year") int year);

    public int countByTitle(String name);

    @Query("SELECT e FROM Expenses e WHERE e.userId = :userId")
    public List<Expenses> findAllByUserId(@Param("userId") int userId);

    public List<Expenses> findByExpenseDateBetweenAndUserId(Date fDate, Date lDate, int user_id);
}
