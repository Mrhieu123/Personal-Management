package com.group6.moneymanagementbooking.enity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int user_id;
    private String name;
    private boolean income_or_expense;
    private double budget;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) 
    @EqualsAndHashCode.Exclude
    @ToString.Exclude 
    private Collection<Expenses> expenses;

      @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) 
    @EqualsAndHashCode.Exclude 
    @ToString.Exclude
    private Collection<Income> income;
}
