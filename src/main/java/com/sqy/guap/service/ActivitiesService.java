package com.sqy.guap.service;

import com.sqy.guap.repository.ActivitiesRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivitiesService {

    private final ActivitiesRepository activitiesRepository;

    public ActivitiesService(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }

    public void latestActivity() {
        activitiesRepository.getLatest();
    }
}
