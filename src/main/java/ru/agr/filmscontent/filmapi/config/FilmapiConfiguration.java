package ru.agr.filmscontent.filmapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * Configuration class
 *
 * @author Arslan Rabadanov
 */
@EnableJpaRepositories(basePackages = "ru.agr.filmscontent.filmapi.db.repository")
@EntityScan(basePackages = "ru.agr.filmscontent.filmapi.db.entity")
@ComponentScan(basePackages = {"ru.agr.filmscontent.filmapi.service","ru.agr.filmscontent.filmapi.controller"})
@Configuration
public class FilmapiConfiguration {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPassword);
        return new HikariDataSource(config);
    }
}
