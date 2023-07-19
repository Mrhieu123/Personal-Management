package com.group6.moneymanagementbooking.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group6.moneymanagementbooking.enity.Accounts;
import com.group6.moneymanagementbooking.enity.Debt_detail;
import com.group6.moneymanagementbooking.enity.Debtor;
import com.group6.moneymanagementbooking.repository.DetailDebtRepository;
import com.group6.moneymanagementbooking.service.AccountsService;
import com.group6.moneymanagementbooking.service.DebtorService;
import com.group6.moneymanagementbooking.service.DetailDebtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailDebtServiceImpl implements DetailDebtService {
   private final DetailDebtRepository detailDebtRepository;
   private final DebtorService debtorService;
   private final AccountsService accountsService;

   @Override
   public List<Debt_detail> findAll() {
      return detailDebtRepository.findAll();
   }

   @Override
   public List<Debt_detail> findAllById(int ids) {

      return detailDebtRepository.findAllByDeptorId(ids);
   }

   @Override
   public Debt_detail Save(Debt_detail detail_edbt) {
      Accounts acc = accountsService.findById(detail_edbt.getAccounts().getId());
      Debtor depDebtor = (debtorService.getDebtor(detail_edbt.getDeptorId())).get();

      depDebtor.setTotal(detail_edbt.isClassify() ? (depDebtor.getTotal() + detail_edbt.getMoney_debt())
            : (depDebtor.getTotal() - detail_edbt.getMoney_debt()));

      acc.setBalance(detail_edbt.isClassify() ? (acc.getBalance() - detail_edbt.getMoney_debt())
            : (acc.getBalance() + detail_edbt.getMoney_debt()));

      depDebtor.setDate_update(LocalDateTime.now());
      return detailDebtRepository.save(detail_edbt);
   }

   @Override
   public void deleteDebtById(int id) {
      Debt_detail debt = (detailDebtRepository.findById(id)).get();
      Debtor debtor = debtorService.getDebtorById(debt.getDeptorId());
      Accounts accounts = accountsService.findById(debt.getAccounts().getId());
      double money_debt = debt.getMoney_debt();
      if (debt.isClassify()) {
         accounts.setBalance(accounts.getBalance() + money_debt);
         debtor.setTotal(debtor.getTotal() - money_debt);
      } else if (!debt.isClassify()) {
         accounts.setBalance(accounts.getBalance() - money_debt);
         debtor.setTotal(debtor.getTotal() + money_debt);
      }
      debtor.setDate_update(LocalDateTime.now());
      detailDebtRepository.deleteById(id);
   }

   @Override
   public Debt_detail findById(int id) {
      Optional<Debt_detail> debt = detailDebtRepository.findById(id);
      return debt.get();
   }

   @Override
   public List<Debt_detail> FilterDebtor(int idDebtor, String filterType, String filterValueStart,
         String filterValueEnd) {
      List<Debt_detail> listdebtor = new ArrayList<>();

      if ("total".equals(filterType)) {
         if (filterValueStart != "" && filterValueEnd != "") {
            try {
               Double from = Double.parseDouble(filterValueStart.trim());
               Double to = Double.parseDouble(filterValueEnd.trim());
               listdebtor = detailDebtRepository.findAllByTotal(idDebtor, from, to);
            } catch (NumberFormatException e) {
               e.printStackTrace();
            }
         } else if (filterValueStart == "" && filterValueEnd == "") {
            listdebtor = detailDebtRepository.findAllByDeptorId(idDebtor);
         }

      } else if ("date".equals(filterType)) {
         if (filterValueStart != "" && filterValueEnd != "") {
            try {
               String pattern = "yyyy-MM-dd";
               DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
               LocalDate dateStart = LocalDate.parse(filterValueStart.trim(), dateFormatter);
               LocalDate dateEnd = LocalDate.parse(filterValueEnd.trim(), dateFormatter);
               LocalDateTime dateTimeStart = dateStart.atStartOfDay();
               LocalDateTime dateTimeEnd = dateEnd.atStartOfDay();
               listdebtor = detailDebtRepository.findAllByDate(idDebtor, dateTimeStart, dateTimeEnd);
            } catch (DateTimeParseException e) {
               e.printStackTrace();
            }
         } else if (filterValueStart == "" && filterValueEnd == "") {
            listdebtor = detailDebtRepository.findAllByDeptorId(idDebtor);
         }
      }
      return listdebtor;
   }

   @Override
   public void UpdateDebt(Debt_detail deb, Debt_detail newdebt_detail) {
      Debtor depDebtor = (debtorService.getDebtor(deb.getDeptorId())).get();
      Accounts oldAcc = accountsService.findById(deb.getAccounts().getId());

      if (deb.getAccounts().getId() != newdebt_detail.getAccounts().getId()) {
         Accounts newAcc = accountsService.findById(newdebt_detail.getAccounts().getId());
         newAcc.setBalance(newdebt_detail.isClassify() ? (newAcc.getBalance() - newdebt_detail.getMoney_debt())
               : (newAcc.getBalance() + newdebt_detail.getMoney_debt()));

      } else if (deb.getAccounts().getId() == newdebt_detail.getAccounts().getId()) {
         oldAcc.setBalance(newdebt_detail.isClassify() ? (oldAcc.getBalance() - newdebt_detail.getMoney_debt())
               : (oldAcc.getBalance() + newdebt_detail.getMoney_debt()));
      }

      depDebtor.setTotal(deb.isClassify() ? (depDebtor.getTotal() - deb.getMoney_debt())
            : (depDebtor.getTotal() + deb.getMoney_debt()));

      oldAcc.setBalance(deb.isClassify() ? (oldAcc.getBalance() + deb.getMoney_debt())
            : (oldAcc.getBalance() - deb.getMoney_debt()));

      depDebtor.setTotal(newdebt_detail.isClassify() ? (depDebtor.getTotal() + newdebt_detail.getMoney_debt())
            : (depDebtor.getTotal() - deb.getMoney_debt()));
      detailDebtRepository.save(newdebt_detail);

   }

}
