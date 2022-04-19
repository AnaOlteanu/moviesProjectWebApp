package com.example.projectmovie.controllers;


import com.example.projectmovie.domain.Actor;
import com.example.projectmovie.domain.Genre;
import com.example.projectmovie.domain.Movie;
import com.example.projectmovie.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GenreController {

    @Autowired
    GenreService genreService;

    @RequestMapping("/genre/list")
    public ModelAndView genresList(){
        ModelAndView modelAndView = new ModelAndView("genres");
        List<Genre> genres = genreService.findAll();
        modelAndView.addObject("genres", genres);
        return modelAndView;
    }

    @RequestMapping("/genre/new")
    public String newGenre(Model model){
        model.addAttribute("genre", new Genre());
        return "genre-add";
    }

    @RequestMapping("/genre/edit/{id}")
    public String editGenre(@PathVariable Long id, Model model){
        model.addAttribute("genre", genreService.findById(id));
        return "genre-add";
    }

    @PostMapping("/genre")
    public String addNewGenre(@Valid @ModelAttribute Genre genre, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "genre-add";
        }
        genreService.save(genre);
        return "redirect:/genre/list";
    }

}
