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

    public boolean updateProject(ProjectDto projectDto) {
        return projectRepository.updateProject(projectDto);
    }

    public boolean createProject(ProjectDto projectDto) {
        return projectRepository.createProject(projectDto);
    }

    public boolean removeProject(long id) {
        return projectRepository.removeProject(id);
    }
}
