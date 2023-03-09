package com.sqy.guap.service;

import com.sqy.guap.domain.Pupil;
import com.sqy.guap.repository.PupilRepository;
import org.springframework.stereotype.Service;

@Service
public class PupilService {
    private final PupilRepository pupilRepository;

    public PupilService(PupilRepository pupilRepository) {
        this.pupilRepository = pupilRepository;
    }

    public Pupil getPupilById(long id) {
        return pupilRepository.getPupilById(id);
    }
}
