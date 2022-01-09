package ru.agr.filmscontent.filmapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.agr.filmscontent.filmapi.config.FilmApiConfiguration;
import ru.agr.filmscontent.filmapi.config.SecurityConfiguration;
import ru.agr.filmscontent.filmapi.config.SwaggerConfiguration;

@SpringBootApplication
@Import({FilmApiConfiguration.class, SecurityConfiguration.class, SwaggerConfiguration.class})
public class FilmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmApiApplication.class, args);
	}

}
