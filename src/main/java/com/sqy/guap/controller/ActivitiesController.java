package com.sqy.guap.controller;

import com.sqy.guap.domain.Quiz;
import com.sqy.guap.service.ActivitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/activities")
public class ActivitiesController {

    public static final Logger logger = LoggerFactory.getLogger(ActivitiesController.class);

    private final ActivitiesService activitiesService;

    public ActivitiesController(ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Quiz>> latestActivity() {
        activitiesService.latestActivity();
        return null;
    }
}
