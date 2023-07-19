package com.group6.moneymanagementbooking.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group6.moneymanagementbooking.enity.Users;
import com.group6.moneymanagementbooking.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/users")

public class UserController {
    @Autowired
    private final UsersService usersService;


    @GetMapping("/add-adjust")
    public String setAdjust(Model model, @ModelAttribute("mess") String mess) {
        model.addAttribute("mess", mess);
        Users user = usersService.getUsers();
        model.addAttribute("user", user);
        return "add-adjust";
    }

    @PostMapping("/add-adjust")
    public String setAdjust(@ModelAttribute Users user, RedirectAttributes redirectAttributes) {
        usersService.addAdjustForUser(user, redirectAttributes);
        return "redirect:/users/add-adjust";
    }

}
