package com.antazri.servermanager.controller;

import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/app")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity getAllApplications(final HttpServletRequest request) {
        List<Application> apps = applicationService.fetchAllApplications();

        return new ResponseEntity(apps, HttpStatus.OK);
    }
}
