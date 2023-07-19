package com.group6.moneymanagementbooking.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.moneymanagementbooking.enity.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {

    public List<Accounts> findAll();

    public Optional<Accounts> findByName(String name);

    @Query("select a.name from Accounts a where a.name = ?1 and a.userId = ?2")
    public Optional<Accounts> findByNameAndUser_id(String name, int id);

    @Modifying
    @Transactional
    @Query("UPDATE Accounts Set is_Active = ?1 where id = ?2")
    public void updateActiveById(Boolean active, int id);

    @Modifying
    @Transactional
    @Query("UPDATE Accounts SET balance = ?1 WHERE id = ?2")
    public void addBalanceById(double balance, int id);

    @Query("UPDATE Accounts SET balance = ?1 WHERE id = ?2")
    public boolean CheckBalanceById(double balance, int id);

    @Modifying
    @Transactional
    @Query("UPDATE Accounts SET balance = ?1 WHERE id = ?2")
    public void expenseBalanceById(double balance, int id);

    @Query("select balance from Accounts where id = ?1")
    public double findBalanceById(int id);

    @Query("select a from Accounts a where a.active = ?1 and a.userId = ?2")
    public List<Accounts> findByActiveAndUserId(boolean active, int id);

    @Query("SELECT a FROM Accounts a WHERE a.userId = :userId")
    public List<Accounts> findAllByUserId(@Param("userId") int userId);
}
