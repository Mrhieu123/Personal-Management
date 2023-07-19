package com.group6.moneymanagementbooking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.moneymanagementbooking.enity.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    @Query("SELECT i FROM Income i WHERE i.userId = :userId")
    public List<Income> findAllByUserId(@Param("userId") int userId);

    public List<Income> findByIncomeDateBetweenAndUserId(Date fDate, Date lDate, int user_id);
}
