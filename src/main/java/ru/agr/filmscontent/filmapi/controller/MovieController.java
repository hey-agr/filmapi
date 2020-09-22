package ru.agr.filmscontent.filmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.agr.filmscontent.filmapi.controller.dto.GenreItem;
import ru.agr.filmscontent.filmapi.controller.dto.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.MovieItem;
import ru.agr.filmscontent.filmapi.controller.dto.MoviesPageResult;
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

    @GetMapping("movies")
    public MovieDTO findAll() {

        List<Movie> movies = movieService.getAll();

        return getMovieDTO(movies);
    }

    @RequestMapping(value = "movies/page={pageNum}/size={pageSize}", method = RequestMethod.GET)
    public MoviesPageResult findAllPageable(@PathVariable(value="pageNum") Integer pageNum,
                                            @PathVariable(value="pageSize") Integer pageSize) {

        Page<Movie> moviesPage = movieService.getAll(PageRequest.of(pageNum, pageSize));

        return new MoviesPageResult(pageNum,
                moviesPage.getTotalPages(),
                pageSize,
                Integer.valueOf(moviesPage.getContent().size()).longValue(),
                movieService.count(),
                true,
                getMovieItems(moviesPage.getContent()));
    }

    @GetMapping("movies/find")
    public MovieDTO findByTitle(@RequestParam(value = "title") String title) {
        if (title == null) {
            return new MovieDTO(new ArrayList<>(), "0", "False");
        }

        List<Movie> movies = movieService.getByTitle(title);

        return getMovieDTO(movies);
    }

    @RequestMapping(value = "movies/page={pageNum}/size={pageSize}/find", method = RequestMethod.GET)
    public MoviesPageResult findByTitlePageable(@PathVariable(value="pageNum") Integer pageNum,
                                        @PathVariable(value="pageSize") Integer pageSize,
                                        @RequestParam(value = "title") String title) {
        if (title == null) {
            return new MoviesPageResult(pageNum,
                    0,
                    pageSize,
                    0L,
                    movieService.count(),
                    false,
                    new ArrayList<>());
        }

        Page<Movie> moviesPage = movieService.getByTitle(title, PageRequest.of(pageNum, pageSize));

        return new MoviesPageResult(pageNum,
                moviesPage.getTotalPages(),
                pageSize,
                Integer.valueOf(moviesPage.getContent().size()).longValue(),
                movieService.count(),
                true,
                getMovieItems(moviesPage.getContent()));
    }

    private MovieDTO getMovieDTO(List<Movie> movies) {
        if (movies != null) {
            return new MovieDTO(getMovieItems(movies),
                    Integer.toString(movies.size()),
                    "True");
        } else {
            return new MovieDTO(new ArrayList<>(), "0", "True");
        }
    }

    private List<MovieItem> getMovieItems(List<Movie> movies) {
        return movies.stream()
                .map(movie ->
                        new MovieItem(movie.getId().toString(),
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
                    .collect(Collectors.toList());
    }
}
