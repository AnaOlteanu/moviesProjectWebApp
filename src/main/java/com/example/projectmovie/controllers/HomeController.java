package com.example.projectmovie.controllers;

import com.example.projectmovie.domain.security.User;
import com.example.projectmovie.services.MovieService;
import com.example.projectmovie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping({"", "/", "/index"})
    public String getWelcomePage(){
        return "index";
    }

}
