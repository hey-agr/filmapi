package ru.agr.filmscontent.filmapi.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

import java.util.List;

/**
 * Movie repository
 *
 * @author Arslan Rabadanov
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT DISTINCT m FROM Movie m fetch all properties " +
            "ORDER BY m.id ")
    List<Movie> findAllWithGenre();

    @Query("SELECT DISTINCT m FROM Movie m fetch all properties " +
            "ORDER BY m.id")
    Page<Movie> findAllWithGenre(Pageable pageable);

    @Query("SELECT DISTINCT m FROM Movie m fetch all properties " +
            "WHERE lower(m.title) LIKE lower(concat('%',:title,'%')) " +
            "ORDER BY m.id")
    List<Movie> getAllByTitleContainingIgnoreCaseWithGenre(@Param("title") String title);

    @Query("SELECT DISTINCT m FROM Movie m fetch all properties " +
            "WHERE lower(m.title) LIKE lower(concat('%',:title,'%')) " +
            "ORDER BY m.id")
    Page<Movie> getAllByTitleContainingIgnoreCaseWithGenre(@Param("title") String title, Pageable pageable);

    @Query("SELECT COUNT(m) FROM Movie m " +
            "WHERE lower(m.title) LIKE lower(concat('%',:title,'%'))")
    Long countByTitleIgnoreCase(@Param("title") String title);
}
