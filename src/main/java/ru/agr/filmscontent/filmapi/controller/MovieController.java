package ru.agr.filmscontent.filmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.filmscontent.filmapi.controller.dto.GenreItem;
import ru.agr.filmscontent.filmapi.controller.dto.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.MovieItem;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.MovieService;
import springfox.documentation.annotations.ApiIgnore;

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

    @ApiIgnore
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Hello, it,s FilmApiApplication, made by Arslan Rabadanov!";
    }

    @GetMapping("movies/find")
    public MovieDTO findByTitle(@RequestParam(value = "title") String title) {
        if (title == null) {
            return new MovieDTO(new ArrayList<>(), "0", "False");
        }

        List<Movie> movies = movieService.getByTitle(title);

        return getMovieDTO(movies);
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
                            movie.getTitleEn(),
                            movie.getYear(),
                            movie.getImdbID(),
                            (movie.getType() != null) ? movie.getType().name() : "",
                            movie.getPoster(),
                            movie.getDescription(),
                            movie.getCountry(),
                            movie.getGenres().stream()
                                    .map(genre -> new GenreItem(genre.getName()))
                                    .collect(Collectors.toList())))
                    .collect(Collectors.toList()),
                    Integer.toString(movies.size()),
                    "True");
        } else {
            return new MovieDTO(new ArrayList<>(), "0", "True");
        }
    }
}
