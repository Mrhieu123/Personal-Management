package com.group6.moneymanagementbooking.enity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Setter
@Getter
@Builder
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)

    private String email;

    @Column(unique = true)
    private String phone;

    private String address;

    private String password;

    private String token;
    private String avatar;

    private String role;
    @Column(name = "is_active")
    private boolean active;
    @Column(name= "annually_spending")
    private double annuallySpending;
    @Column(name= "monthly_spending")
    private double monthlySpending;
    @Column(name= "monthly_saving")
    private double monthlySaving;
    @Column(name= "monthly_earning")
    private double monthlyEarning;

    private String currency;

    private int failed_attempt;

    @Column(name = "account_non_locked")
    private boolean nonLocked;

    private Date lockTime;

}
