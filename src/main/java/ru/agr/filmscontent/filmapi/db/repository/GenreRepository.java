package ru.agr.filmscontent.filmapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agr.filmscontent.filmapi.db.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
}
