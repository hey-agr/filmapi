package ru.agr.filmscontent.filmapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

/**
 * Repository of entity Movie
 *
 * @author Arslan Rabadanov
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
