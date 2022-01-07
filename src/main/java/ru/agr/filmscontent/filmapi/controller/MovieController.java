package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.agr.filmscontent.filmapi.controller.dto.DtoConverter;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieItem;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MoviesPageDTO;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.GenreService;
import ru.agr.filmscontent.filmapi.service.MovieService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Movie REST controller
 *
 * @author Arslan Rabadanov
 */
@RestController
@Slf4j
public class MovieController {

    private final MovieService movieService;

    private final GenreService genreService;

    private final DtoConverter dtoConverter;

    @Autowired
    public MovieController(MovieService movieService,
                           GenreService genreService,
                           DtoConverter dtoConverter) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.dtoConverter = dtoConverter;
    }

    @ApiIgnore
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Hello, it,s FilmApiApplication, made by Arslan Rabadanov!";
    }

    @GetMapping("movies")
    public MovieDTO findAll() {
        log.info("Find all movies");
        List<Movie> movies = movieService.getAll();
        return dtoConverter.getMovieDTO(movies);
    }

    @GetMapping("genre")
    public List<GenreItem> findAllGenre() {
        log.info("Find all genre");
        List<GenreItem> genres = genreService.getAll().stream()
                .map(genre -> new GenreItem(genre.getName()))
                .collect(Collectors.toList());
        return genres;
    }

    @RequestMapping(value = "movies/page={pageNum}/size={pageSize}", method = RequestMethod.GET)
    public MoviesPageDTO findAllPageable(@PathVariable(value="pageNum") Integer pageNum,
                                         @PathVariable(value="pageSize") Integer pageSize) {
        log.info("Find all movies pageable");
        Page<Movie> moviesPage = movieService.getAll(PageRequest.of(pageNum-1, pageSize));

        return new MoviesPageDTO(pageNum,
                moviesPage.getTotalPages(),
                pageSize,
                Integer.valueOf(moviesPage.getContent().size()).longValue(),
                movieService.count(),
                true,
                dtoConverter.getMovieItems(moviesPage.getContent()));
    }

    @GetMapping("movies/find")
    public MovieDTO findByTitle(@RequestParam(value = "title") String title) {
        log.info("Find all movies by title: " + title);
        if (title == null) {
            return new MovieDTO(new ArrayList<>(), 0L, false);
        }

        List<Movie> movies = movieService.getByTitle(title);

        return dtoConverter.getMovieDTO(movies);
    }

    @GetMapping("movies/find/{id}")
    public ResponseEntity<MovieDTO> findById(@RequestParam(value = "id") long id) {
        log.info("Find movie by id: " + id);
        Movie currentMovie = movieService.getById(id);
        if (currentMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtoConverter.getMovieDTO(Collections.singletonList(currentMovie)), HttpStatus.OK);
    }

    @RequestMapping(value = "movies/page={pageNum}/size={pageSize}/find", method = RequestMethod.GET)
    public MoviesPageDTO findByTitlePageable(@PathVariable(value="pageNum") Integer pageNum,
                                             @PathVariable(value="pageSize") Integer pageSize,
                                             @RequestParam(value = "title") String title) {
        log.info("Find all movies pageable by title: " + title);
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
                dtoConverter.getMovieItems(moviesPage.getContent()));
    }

    @Transactional
    @RequestMapping(value = "movies/create", method = RequestMethod.POST)
    public ResponseEntity<MovieDTO> create(@RequestBody MovieItem movieItem) {
        log.info("Create new movie: " + movieItem);
        try {
            Movie movieSaved = movieService.save(dtoConverter.convertMovieItemToMovie(new Movie(), movieItem));
            return new ResponseEntity<>(dtoConverter.getMovieDTO(Collections.singletonList(movieSaved)), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error create movie: " + movieItem, e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @RequestMapping(value = "movies/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MovieDTO> update(@PathVariable long id, @RequestBody MovieItem movieItem) {
        log.info("Update movie: " + movieItem);
        Movie currentMovie = movieService.getById(id);
        if (currentMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dtoConverter.convertMovieItemToMovie(currentMovie, movieItem);
        currentMovie.setId(id);
        return new ResponseEntity<>(dtoConverter.getMovieDTO(Collections.singletonList(movieService.save(currentMovie))), HttpStatus.OK);
    }

    @RequestMapping(value = "movies/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        log.info("Delete movie with id: " + id);
        Movie movie = movieService.getById(id);
        if (movie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        movieService.delete(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
