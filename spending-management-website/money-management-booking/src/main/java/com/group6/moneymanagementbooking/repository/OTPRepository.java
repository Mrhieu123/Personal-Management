package com.group6.moneymanagementbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group6.moneymanagementbooking.enity.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer>  {

    Optional<OTP> findByEmail(String email);
}
