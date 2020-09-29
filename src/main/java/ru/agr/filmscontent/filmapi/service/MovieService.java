package ru.agr.filmscontent.filmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Movie save(Movie movie) {
        return movieRepository.saveAndFlush(movie);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    public List<Movie> getByTitle(String title) {
        return movieRepository.getAllByTitleContainingIgnoreCaseWithGenre(title);
    }

    public Page<Movie> getByTitle(String title, Pageable pageable) {
        return movieRepository.getAllByTitleContainingIgnoreCaseWithGenre(title, pageable);
    }

    public List<Movie> getAll() {
        return movieRepository.findAllWithGenre();
    }

    public Page<Movie> getAll(Pageable pageable) {
        return movieRepository.findAllWithGenre(pageable);
    }

    public Long count() {
        return movieRepository.count();
    }

    public Long countByTitle(String title) {
        return movieRepository.countByTitleIgnoreCase(title);
    }

    public Movie getById(long id) {
        return movieRepository.findById(id).orElse(null);
    }
}
