package ru.agr.filmscontent.filmapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

import java.util.List;

/**
 * Repository of entity Movie
 *
 * @author Arslan Rabadanov
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> getAllByTitleContainingIgnoreCase(String title);

    List<Movie> findAll();

    @Query("SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.genres order by m.id")
    List<Movie> findAllWithGenre();

    @Query("SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.genres WHERE lower(m.title) LIKE lower(concat('%',:title,'%')) order by m.id")
    List<Movie> getAllByTitleContainingIgnoreCaseWithGenre(@Param("title") String title);
}
