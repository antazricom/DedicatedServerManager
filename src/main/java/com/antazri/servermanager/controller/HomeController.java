package com.antazri.servermanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ModelAndView getHomeView(final Model model, final HttpServletRequest request) {
        return new ModelAndView("index");
    }
}
