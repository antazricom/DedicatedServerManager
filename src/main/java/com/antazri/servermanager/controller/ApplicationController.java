package com.antazri.servermanager.controller;

import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/app")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ModelAndView getAllApplications(final Model model, final HttpServletRequest request) {
        List<Application> apps = applicationService.fetchAllApplications();

        ModelAndView mv = new ModelAndView("applications");
        mv.addObject("apps", apps);

        return mv;
    }
}
