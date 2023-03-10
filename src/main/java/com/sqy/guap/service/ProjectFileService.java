package com.sqy.guap.service;

import com.sqy.guap.repository.ProjectFileRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectFileService {
    private final ProjectFileRepository projectFileRepository;

    public ProjectFileService(ProjectFileRepository projectFileRepository) {
        this.projectFileRepository = projectFileRepository;
    }

    public void storeFile(MultipartFile file) {
        projectFileRepository.saveProjectFile(file);
    }

    public Resource getFile(String filename) {
        return projectFileRepository.getFile(filename);
    }
}
