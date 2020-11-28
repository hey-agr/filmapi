package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agr.filmscontent.filmapi.controller.dto.DtoConverter;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieItem;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MoviesPageDTO;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.AuthenticationService;
import ru.agr.filmscontent.filmapi.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

/**
 * Movie REST controller v1
 *
 * @author Arslan Rabadanov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/movies")
public class MovieControllerV1 {

    private final MovieService movieService;

    private final AuthenticationService authenticationService;

    private final DtoConverter dtoConverter;


    @Autowired
    public MovieControllerV1(MovieService movieService,
                             AuthenticationService authenticationService,
                             DtoConverter dtoConverter) {
        this.movieService = movieService;
        this.authenticationService = authenticationService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("")
    public MovieDTO findAll() {
        log.debug("Find all movies");
        return dtoConverter.getMovieDTO(movieService.getAll());
    }

    @GetMapping("/pageable")
    public MoviesPageDTO findAllPageable(@RequestParam(value="pageNum") Integer pageNum,
                                         @RequestParam(value="pageSize") Integer pageSize) {
        log.debug("Find all movies pageable");
        Page<Movie> moviesPage = movieService.getAll(PageRequest.of(pageNum-1, pageSize));

        return new MoviesPageDTO(pageNum,
                moviesPage.getTotalPages(),
                pageSize,
                Integer.valueOf(moviesPage.getContent().size()).longValue(),
                movieService.count(),
                true,
                dtoConverter.getMovieItems(moviesPage.getContent()));
    }

    @GetMapping("/title={title}")
    public MovieDTO findByTitle(@PathVariable(value = "title") String title) {
        log.debug("Find all movies by title: " + title);
        if (title == null) {
            return new MovieDTO(new ArrayList<>(), 0L, false);
        }

        List<Movie> movies = movieService.getByTitle(title);

        return dtoConverter.getMovieDTO(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable(value = "id") long id) {
        log.debug("Find movie by id: " + id);
        Movie currentMovie = movieService.getById(id);
        if (currentMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtoConverter.getMovieDTO(Collections.singletonList(currentMovie)), HttpStatus.OK);
    }

    @GetMapping("/pageable/pageNum={pageNum}&pageSize={pageSize}&title={title}")
    public MoviesPageDTO findByTitlePageable(@PathVariable(value="pageNum") Integer pageNum,
                                             @PathVariable(value="pageSize") Integer pageSize,
                                             @PathVariable(value = "title") String title) {
        log.debug("Find all movies pageable by title: " + title);
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
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MovieItem movieItem,
                                    HttpServletRequest request) {
        log.debug("Create new movie: " + movieItem);

        try {
            Movie movieSaved = movieService.save(dtoConverter.convertMovieItemToMovie(new Movie(), movieItem));
            return created(
                    ServletUriComponentsBuilder
                            .fromContextPath(request)
                            .path("/api/v1/movie/{id}")
                            .buildAndExpand(movieSaved.getId())
                            .toUri())
                    .build();
        } catch (Exception e) {
            String errorMessage = "Error during creation movie: " + e.getMessage();
            log.error(errorMessage, e);
            return badRequest().body(errorMessage);
        }
    }

    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable long id,
                                    @RequestBody MovieItem movieItem,
                                    HttpServletRequest request) {
        log.debug("Update movie: " + movieItem);

        Movie currentMovie = movieService.getById(id);
        if (currentMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentMovie = dtoConverter.convertMovieItemToMovie(currentMovie, movieItem);
        currentMovie.setId(id);
        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/api/v1/movie/{id}")
                        .buildAndExpand(movieService.save(currentMovie).getId())
                        .toUri())
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id,
                                       HttpServletRequest request) {
        log.debug("Delete movie with id: " + id);

        Movie movie = movieService.getById(id);
        if (movie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        movieService.delete(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
