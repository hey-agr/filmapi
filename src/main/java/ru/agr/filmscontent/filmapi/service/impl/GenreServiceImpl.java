package ru.agr.filmscontent.filmapi.service.impl;

import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Genre;
import ru.agr.filmscontent.filmapi.db.repository.GenreRepository;
import ru.agr.filmscontent.filmapi.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }
}
