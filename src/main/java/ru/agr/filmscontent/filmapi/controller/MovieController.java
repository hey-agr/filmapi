package ru.agr.filmscontent.filmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.filmscontent.filmapi.controller.dto.MovieDTO;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.MovieService;

import java.util.ArrayList;
import java.util.List;

/**
 * Movie REST controller
 *
 * @author Arslan Rabadanov
 */
@RestController
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //s=home%20alone&apikey=4a3b711b

    @GetMapping
    public MovieDTO greeting(@RequestParam(value = "s") String title, @RequestParam(value = "apikey") String apiKey) {
        if (title == null) {
            return new MovieDTO(new ArrayList<>(), "0", "False");
        }

        List<Movie> movies = movieService.getByTitle(title);

        if (movies != null) {
            return new MovieDTO(movies, Integer.toString(movies.size()), "True");
        } else {
            return new MovieDTO(new ArrayList<>(), "0", "True");
        }
    }
}
