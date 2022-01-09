package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.agr.filmscontent.filmapi.controller.dto.ResponseHelper;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieItem;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MoviesPageDTO;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.service.GenreService;
import ru.agr.filmscontent.filmapi.service.MovieService;
import ru.agr.filmscontent.filmapi.service.mapper.GenreMapper;
import ru.agr.filmscontent.filmapi.service.mapper.MovieMapper;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

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
    private final ResponseHelper responseHelper;
    private final MovieMapper movieMapper;
    private final GenreMapper genreMapper;

    @Autowired
    public MovieController(MovieService movieService,
                           GenreService genreService,
                           ResponseHelper responseHelper,
                           MovieMapper movieMapper,
                           GenreMapper genreMapper) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.responseHelper = responseHelper;
        this.movieMapper = movieMapper;
        this.genreMapper = genreMapper;
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
        List<MovieItem> movies = movieMapper.toItem(movieService.getAll());
        long totalCount = nonNull(movies) ? (long) movies.size() : 0L;
        return MovieDTO.builder()
                .totalResults(totalCount)
                .response(totalCount > 0)
                .search(movies)
                .build();
    }

    @GetMapping("genre")
    public List<GenreItem> findAllGenre() {
        log.info("Find all genre");
        return genreService.getAll().stream()
                .map(genreMapper::toItem)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "movies/page={pageNum}/size={pageSize}", method = RequestMethod.GET)
    public MoviesPageDTO findAllPageable(@PathVariable(value="pageNum") Integer pageNum,
                                         @PathVariable(value="pageSize") Integer pageSize) {
        log.info("Find all movies pageable");
        Page<Movie> moviesPage = movieService.getAll(PageRequest.of(pageNum-1, pageSize));
        return MoviesPageDTO.builder()
                .page(pageNum)
                .size(pageSize)
                .totalPages(moviesPage.getTotalPages())
                .currentSize((long) moviesPage.getContent().size())
                .totalResults(movieService.count())
                .response(true)
                .search(movieMapper.toItem(moviesPage.getContent()))
                .build();
    }

    @GetMapping("movies/find")
    public ResponseEntity<MovieDTO> findByTitle(@RequestParam(value = "title") String title) {
        log.info("Find all movies by title: " + title);
        List<MovieItem> movies = movieMapper.toItem(movieService.getByTitle(title));
        return responseHelper.getMoviesDtoResponse(movies, HttpStatus.OK);
    }

    @GetMapping("movies/find/{id}")
    public ResponseEntity<MovieDTO> findById(@RequestParam(value = "id") long id) {
        log.info("Find movie by id: " + id);
        MovieItem currentMovie = movieMapper.toItem(movieService.getById(id));
        if (currentMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseHelper.getMovieDtoResponse(currentMovie, HttpStatus.OK);
    }

    @RequestMapping(value = "movies/page={pageNum}/size={pageSize}/find", method = RequestMethod.GET)
    public MoviesPageDTO findByTitlePageable(@PathVariable(value="pageNum") Integer pageNum,
                                             @PathVariable(value="pageSize") Integer pageSize,
                                             @RequestParam(value = "title") String title) {
        log.info("Find all movies pageable by title: " + title);
        Page<Movie> moviesPage = movieService.getByTitle(title, PageRequest.of(pageNum-1, pageSize));
        return MoviesPageDTO.builder()
                .page(pageNum)
                .size(pageSize)
                .totalPages(moviesPage.getTotalPages())
                .currentSize((long) moviesPage.getContent().size())
                .totalResults(movieService.countByTitle(title))
                .response(true)
                .search(movieMapper.toItem(moviesPage.getContent()))
                .build();
    }

    @Transactional
    @RequestMapping(value = "movies/create", method = RequestMethod.POST)
    public ResponseEntity<MovieDTO> create(@RequestBody MovieItem movieItem) {
        log.info("Create new movie: " + movieItem);
        try {
            MovieItem movieSaved = movieMapper.toItem(movieService.save(responseHelper.convertMovieItemToMovie(new Movie(), movieItem)));
            return responseHelper.getMovieDtoResponse(movieSaved, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error create movie: " + movieItem, e);
            return responseHelper.getMovieDtoResponse(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @RequestMapping(value = "movies/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MovieDTO> update(@PathVariable long id, @RequestBody MovieItem movieItem) {
        log.info("Update movie: " + movieItem);
        Movie currentMovie = movieService.getById(id);
        if (currentMovie == null) {
            return responseHelper.getMovieDtoResponse(null, HttpStatus.NOT_FOUND);
        }
        currentMovie = responseHelper.convertMovieItemToMovie(currentMovie, movieItem);
        currentMovie.setId(id);
        MovieItem savedMovie = movieMapper.toItem(movieService.save(currentMovie));
        return responseHelper.getMovieDtoResponse(savedMovie, HttpStatus.OK);
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
