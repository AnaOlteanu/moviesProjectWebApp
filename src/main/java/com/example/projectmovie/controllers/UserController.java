package com.example.projectmovie.controllers;

import com.example.projectmovie.domain.security.User;
import com.example.projectmovie.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String showLogInForm(){ return "login"; }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register"; }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("Error during register {}", bindingResult.getFieldErrors());
            return "register";
        }

        userService.save(user);
        return "redirect:/login";
    }
}
