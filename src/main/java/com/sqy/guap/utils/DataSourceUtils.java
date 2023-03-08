package com.sqy.guap.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceUtils {
    public static DataSource createDataSource(String driverClassName, String jdbcUrl, String jdbcUser, String jdbcPassword) {
        HikariConfig config = new HikariConfig();
        if (jdbcUrl == null || jdbcUrl.equals("")) {
            throw new IllegalArgumentException("Can't create data source!");
        } else {
            config.setDriverClassName(driverClassName);
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(jdbcUser);
            config.setPassword(jdbcPassword);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(1);
            config.setAutoCommit(true);
        }
        // default tuning
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}
