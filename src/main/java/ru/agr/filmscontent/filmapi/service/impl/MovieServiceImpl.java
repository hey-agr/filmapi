package ru.agr.filmscontent.filmapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.db.repository.MovieRepository;
import ru.agr.filmscontent.filmapi.service.MovieService;

import java.util.List;

/**
 * Service layer of entity Movie
 *
 * @author Arslan Rabadanov
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.saveAndFlush(movie);
    }

    @Override
    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    @Override
    public List<Movie> getByTitle(String title) {
        return movieRepository.getAllByTitleContainingIgnoreCaseWithGenre(title);
    }

    @Override
    public Page<Movie> getByTitle(String title, Pageable pageable) {
        return movieRepository.getAllByTitleContainingIgnoreCaseWithGenre(title, pageable);
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAllWithGenre();
    }

    @Override
    public Page<Movie> getAll(Pageable pageable) {
        return movieRepository.findAllWithGenre(pageable);
    }

    @Override
    public Long count() {
        return movieRepository.count();
    }

    @Override
    public Long countByTitle(String title) {
        return movieRepository.countByTitleIgnoreCase(title);
    }

    @Override
    public Movie getById(long id) {
        return movieRepository.findById(id).orElse(null);
    }
}
