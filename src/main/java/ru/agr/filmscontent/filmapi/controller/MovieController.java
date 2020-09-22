package ru.agr.filmscontent.filmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.filmscontent.filmapi.controller.dto.GenreItem;
import ru.agr.filmscontent.filmapi.controller.dto.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.MovieItem;
import ru.agr.filmscontent.filmapi.controller.dto.MoviesPageDTO;
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
    public MoviesPageDTO findAllPageable(@PathVariable(value="pageNum") Integer pageNum,
                                         @PathVariable(value="pageSize") Integer pageSize) {

        Page<Movie> moviesPage = movieService.getAll(PageRequest.of(pageNum-1, pageSize));

        return new MoviesPageDTO(pageNum,
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
            return new MovieDTO(new ArrayList<>(), 0L, false);
        }

        List<Movie> movies = movieService.getByTitle(title);

        return getMovieDTO(movies);
    }

    @RequestMapping(value = "movies/page={pageNum}/size={pageSize}/find", method = RequestMethod.GET)
    public MoviesPageDTO findByTitlePageable(@PathVariable(value="pageNum") Integer pageNum,
                                             @PathVariable(value="pageSize") Integer pageSize,
                                             @RequestParam(value = "title") String title) {
        if (title == null) {
            return new MoviesPageDTO(pageNum,
                    0,
                    pageSize,
                    0L,
                    0L,
                    false,
                    new ArrayList<>());
        }

        Page<Movie> moviesPage = movieService.getByTitle(title, PageRequest.of(pageNum-1, pageSize));

        return new MoviesPageDTO(pageNum,
                moviesPage.getTotalPages(),
                pageSize,
                Integer.valueOf(moviesPage.getContent().size()).longValue(),
                movieService.countByTitle(title),
                true,
                getMovieItems(moviesPage.getContent()));
    }

    private MovieDTO getMovieDTO(List<Movie> movies) {
        if (movies != null) {
            return new MovieDTO(getMovieItems(movies),
                    Integer.valueOf(movies.size()).longValue(),
                    true);
        } else {
            return new MovieDTO(new ArrayList<>(), 0L, false);
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
