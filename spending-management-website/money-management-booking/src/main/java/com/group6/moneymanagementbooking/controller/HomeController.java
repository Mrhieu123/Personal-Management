package com.group6.moneymanagementbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping(value = {"","/homepage", "/" })
  public String homepag(Model model) {
    // AccountDTOLoginRequest accountDTOLoginRequest =
    // AccountDTOLoginRequest.builder().build();
    // model.addAttribute("accountDTOLoginRequest", accountDTOLoginRequest);
    return "home";
  }

}
