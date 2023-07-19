package com.group6.moneymanagementbooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.moneymanagementbooking.enity.Debtor;

@Repository
public interface DebtorRepository extends JpaRepository<Debtor, Integer> {
    public List<Debtor> findAllByNameContaining(String name);

    @Query("SELECT d FROM Debtor d WHERE d.userId = :userId")
    public List<Debtor> findAllByUserid(@Param("userId") int id);

    @Query("SELECT d FROM Debtor d WHERE d.userId = ?1 AND d.name LIKE %?2%")

    public List<Debtor> findAllByNameContainingAnduserid(int id, String name);

    @Query("SELECT d FROM Debtor d WHERE d.userId = ?1  AND d.total BETWEEN ?2 AND ?3")
    public List<Debtor> findAllByTotal(int idUser, double from, double to);

    @Query("SELECT d FROM Debtor d WHERE d.userId = ?1 AND d.date_create BETWEEN ?2 AND ?3")
    public List<Debtor> findAllByDate(int idUser, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd);

    // @Query("SELECT d FROM Debtor d WHERE d.userId = ?1 AND d.name = ?2 AND
    // d.date_create BETWEEN ?3 AND ?4")
    // public List<Debtor> findAllByDateAndName(int idUser, String name,
    // LocalDateTime dateTimeStart,
    // LocalDateTime dateTimeEnd);

    // @Query("SELECT d FROM Debtor d WHERE d.userId = ?1 AND d.name = ?2 AND
    // d.total BETWEEN ?3 AND ?4")
    // public List<Debtor> findAllByTotalAndName(int idUser, String name, Double
    // from, Double to);

}
