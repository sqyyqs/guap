package com.sqy.guap.controller;

import com.sqy.guap.service.ProjectFileService;
import com.sqy.guap.utils.MappingUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping("/api/file")
public class ProjectFileController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectFileController.class);
    private final ProjectFileService projectFileService;

    public ProjectFileController(ProjectFileService projectFileService) {
        this.projectFileService = projectFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProjectFile(@RequestParam("file") MultipartFile file) {
        projectFileService.storeFile(file);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }

    @GetMapping("download/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable("filename") String filename,
                                          HttpServletRequest req) {
        logger.info("Invoke downloadFile({}).", filename);
        Resource fileResource = projectFileService.getFile(filename);
        String contentType = null;
        try {
            contentType = req.getServletContext().getMimeType(fileResource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.error("Invoke downloadFile({}) with exception.", filename, ex);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }
}
