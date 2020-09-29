package ru.agr.filmscontent.filmapi.service;

import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Genre;
import ru.agr.filmscontent.filmapi.db.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre getByName(String name) {
        return genreRepository.findByName(name);
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
