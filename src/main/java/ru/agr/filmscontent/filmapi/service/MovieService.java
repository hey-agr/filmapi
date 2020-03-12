package ru.agr.filmscontent.filmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.db.repository.MovieRepository;

import java.util.List;

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

    public List<Movie> getByTitle(String title) {
        return movieRepository.getAllByTitleContainingIgnoreCaseWithGenre(title);
    }

    public List<Movie> getAll() {
        return movieRepository.findAllWithGenre();
    }
}
