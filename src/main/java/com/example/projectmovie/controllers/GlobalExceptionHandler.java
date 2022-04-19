package com.example.projectmovie.controllers;

import com.example.projectmovie.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handlerNotFoundException(Exception e){
        ModelAndView modelAndView  = new ModelAndView("notfound");
        modelAndView.getModel().put("exception", e.getMessage());
        return modelAndView;
    }
}
