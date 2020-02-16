package ru.agr.filmscontent.filmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.agr.filmscontent.filmapi.controller.dto.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.MovieItem;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.MovieService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("movies/find")
    public MovieDTO findByTitle(@RequestParam(value = "title") String title) {
        if (title == null) {
            return new MovieDTO(new ArrayList<>(), "0", "False");
        }

        List<Movie> movies = movieService.getByTitle(title);

        return getMovieDTO(movies);
    }

    @PostMapping("movies/add")
    public void addFilm(@RequestBody MovieItem movieItem) {

    }

    @GetMapping("movies")
    public MovieDTO findAll() {

        List<Movie> movies = movieService.getAll();

        return getMovieDTO(movies);
    }

    private MovieDTO getMovieDTO(List<Movie> movies) {
        if (movies != null) {
            return new MovieDTO(movies.stream()
                    .map(movie -> new MovieItem(movie.getId().toString(),
                            movie.getTitle(),
                            movie.getYear(),
                            movie.getImdbID(),
                            movie.getType(),
                            movie.getPoster())).collect(Collectors.toList()),
                    Integer.toString(movies.size()),
                    "True");
        } else {
            return new MovieDTO(new ArrayList<>(), "0", "True");
        }
    }
}
