package com.sqy.guap.controller;

import com.sqy.guap.domain.Pupil;
import com.sqy.guap.service.PupilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pupil")
@CrossOrigin("*")
public class PupilController {

    private static final Logger logger = LoggerFactory.getLogger(PupilController.class);

    private final PupilService pupilService;

    public PupilController(PupilService pupilService) {
        this.pupilService = pupilService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPupilById(@PathVariable long id) {
        logger.info("Invoke getPupilById({}).", id);
        Pupil pupil = pupilService.getPupilById(id);
        if(pupil == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pupil);
    }
}
