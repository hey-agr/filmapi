package ru.agr.filmscontent.filmapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Application configuration class
 *
 * @author Arslan Rabadanov
 */
@EnableJpaRepositories(basePackages = "ru.agr.filmscontent.filmapi.db.repository")
@EntityScan(basePackages = "ru.agr.filmscontent.filmapi.db.entity")
@ComponentScan(basePackages = {
        "ru.agr.filmscontent.filmapi.service",
        "ru.agr.filmscontent.filmapi.controller",
        "ru.agr.filmscontent.filmapi.security"
})
@EnableSwagger2
@Configuration
public class FilmApiConfiguration {
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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(?!/error.*).*"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securityScheme())
                .securityContexts(securityContexts());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Film API Rest")
                .description("")
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .version("1.0.0")
                //.contact(new Contact(""))
                .build();
    }

    private List<SecurityScheme> securityScheme() {
        return singletonList(new ApiKey("Authorization", "Authorization", "header"));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder
                .builder()
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }

    private List<SecurityContext> securityContexts() {
        return singletonList(
                SecurityContext
                        .builder()
                        .securityReferences(defaultAuth())
                        .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        return singletonList(
                new SecurityReference(
                        "Authorization",
                        new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")}
                )
        );
    }

}
