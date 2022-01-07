package ru.agr.filmscontent.filmapi.service;

import ru.agr.filmscontent.filmapi.db.entity.Genre;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
public interface GenreService {
    Genre getByName(String name);

    List<Genre> getAll();

    Genre save(Genre genre);
}
