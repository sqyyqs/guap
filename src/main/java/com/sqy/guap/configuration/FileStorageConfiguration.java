package com.sqy.guap.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class FileStorageConfiguration {
    @Value("${file.upload-dir}")
    private String path;

    @Bean
    public Path getUploadDirAsPath() {
        return Path.of(path);
    }
}
