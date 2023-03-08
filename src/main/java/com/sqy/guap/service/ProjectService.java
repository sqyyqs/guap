package com.sqy.guap.service;

import com.sqy.guap.domain.Project;
import com.sqy.guap.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProjectById(long id) {
        return projectRepository.getProjectById(id);
    }

}
