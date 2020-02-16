package ru.agr.filmscontent.filmapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class FilmapiApplication {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello, it,s FilmApiApplication!";
	}

	public static void main(String[] args) {
		SpringApplication.run(FilmapiApplication.class, args);
	}

}
