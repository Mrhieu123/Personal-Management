package com.group6.moneymanagementbooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.moneymanagementbooking.enity.Debt_detail;

@Repository
public interface DetailDebtRepository extends JpaRepository<Debt_detail, Integer> {
  @Query("SELECT d FROM Debt_detail d WHERE d.deptorId = :deptorId")
  List<Debt_detail> findAllByDeptorId(@Param("deptorId") int deptorId);
  // public List<Debt_detail> findAllByDeptorId(int id);

  @Query("SELECT d FROM Debt_detail d WHERE d.deptorId = ?1  AND d.money_debt BETWEEN ?2 AND ?3")
  public List<Debt_detail> findAllByTotal(int idDebtor, double from, double to);

  @Query("SELECT d FROM Debt_detail d WHERE d.deptorId = ?1 AND d.date_create BETWEEN ?2 AND ?3")
  public List<Debt_detail> findAllByDate(int idDebtor, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);
}
