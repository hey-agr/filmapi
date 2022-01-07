package ru.agr.filmscontent.filmapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
public interface MovieService {
    Movie save(Movie movie);
    void delete(Movie movie);
    List<Movie> getByTitle(String title);
    Page<Movie> getByTitle(String title, Pageable pageable);
    List<Movie> getAll();
    Page<Movie> getAll(Pageable pageable);
    Long count();
    Long countByTitle(String title);
    Movie getById(long id);
}
