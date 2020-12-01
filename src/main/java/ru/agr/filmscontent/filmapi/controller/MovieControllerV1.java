package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agr.filmscontent.filmapi.controller.dto.DtoConverter;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieForm;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MoviesPageDTO;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

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

    private final static int DEFAULT_PAGE = 1;

    private final static int DEFAULT_SIZE = 100;

    private final MovieService movieService;

    private final DtoConverter dtoConverter;

    @Autowired
    public MovieControllerV1(MovieService movieService,
                             DtoConverter dtoConverter) {
        this.movieService = movieService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value="page", required = false) Optional<Integer> page,
                                            @RequestParam(value="size", required = false) Optional<Integer> size,
                                            @RequestParam(value = "title", required = false) Optional<String> title) {
        log.debug("Find all movies");
        MovieDTO movieDTO;
        if (page.isPresent() || size.isPresent()) {
            movieDTO = findPageable(page.orElse(DEFAULT_PAGE), size.orElse(DEFAULT_SIZE), title);
        } else if (title.isPresent()) {
            movieDTO = dtoConverter.getMovieDTO(movieService.getByTitle(title.get()));
        } else {
            movieDTO = dtoConverter.getMovieDTO(movieService.getAll());
        }

        return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }

    private MoviesPageDTO findPageable(Integer page, Integer size, Optional<String> title) {
        Page<Movie> moviesPage;
        Long totalCount;
        if (title.isPresent()) {
            moviesPage = movieService.getByTitle(title.get(), PageRequest.of(page-1, size));
            totalCount = movieService.countByTitle(title.get());
        } else {
            moviesPage = movieService.getAll(PageRequest.of(page-1, size));
            totalCount = movieService.count();
        }

        return new MoviesPageDTO(page,
                moviesPage.getTotalPages(),
                size,
                Integer.valueOf(moviesPage.getContent().size()).longValue(),
                totalCount,
                true,
                dtoConverter.getMovieItems(moviesPage.getContent()));
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

    @Transactional
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MovieForm movieForm, HttpServletRequest request) {
        log.debug("Create new movie: " + movieForm);

        try {
            Movie movieSaved = movieService.save(dtoConverter.convertMovieItemToMovie(new Movie(), movieForm));
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
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody MovieForm movieForm, HttpServletRequest request) {
        log.debug("Update movie: " + movieForm);

        Movie currentMovie = movieService.getById(id);
        if (currentMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentMovie = dtoConverter.convertMovieItemToMovie(currentMovie, movieForm);
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
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        log.debug("Delete movie with id: " + id);

        Movie movie = movieService.getById(id);
        if (movie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        movieService.delete(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
