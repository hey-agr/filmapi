package ru.agr.filmscontent.filmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.db.repository.MovieRepository;

/**
 * Service layer of entity Movie
 *
 * @author Arslan Rabadanov
 */
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void add(Movie movie) {
        movieRepository.save(movie);
    }
}
