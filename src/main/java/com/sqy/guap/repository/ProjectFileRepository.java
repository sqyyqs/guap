package com.sqy.guap.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Repository
public class ProjectFileRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProjectFileRepository.class);

    private final Path fileLocation;

    public ProjectFileRepository(Path fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void saveProjectFile(MultipartFile file) {
        try {
            String filename = StringUtils.cleanPath(
                    Objects.requireNonNull(file.getOriginalFilename())
            );
            Path targetLocation = fileLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            logger.error("Invoke saveProjectFile({}) with exception.", file, ex);
        }
    }

    public Resource getFile(String filename) {
        try {
            Path path = fileLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            logger.error("Invoke getFile({}) with exception.", filename, ex);
        }
        return null;
    }
}
