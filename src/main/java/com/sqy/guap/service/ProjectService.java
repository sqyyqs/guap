package com.sqy.guap.service;

import com.sqy.guap.domain.Project;
import com.sqy.guap.dto.ProjectDto;
import com.sqy.guap.repository.ProjectRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProjectService {

    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Nullable
    public Project getProjectById(long id) {
        return projectRepository.getProjectById(id);
    }

    @Nullable
    public Collection<Project> getProjectsByStudentId(long id) {
        return projectRepository.getProjectsByStudentId(id);
    }

    @Nullable
    public Collection<Project> getProjectsByTeacherId(long id) {
        return projectRepository.getProjectsByTeacherId(id);
    }

    public void updateProject(ProjectDto projectDto) {
        projectRepository.updateProject(projectDto);
    }

    public void createProject(ProjectDto projectDto) {
        projectRepository.createProject(projectDto);
    }

    public void removeProject(long id) {
        projectRepository.removeProject(id);
    }
}
