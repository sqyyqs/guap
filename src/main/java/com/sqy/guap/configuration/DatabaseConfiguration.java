package com.sqy.guap.configuration;

import com.sqy.guap.utils.DataSourceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfiguration {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(
                DataSourceUtils.createDataSource(driver, url, username, password)
        );
    }
}
