package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Genre REST controller v1
 *
 * @author Arslan Rabadanov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/genres")
public class GenreControllerV1 {

    private final GenreService genreService;

    @Autowired
    public GenreControllerV1(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreItem> findAllGenres() {
        log.debug("Find all genre");
        return genreService.getAll().stream()
                .map(genre -> new GenreItem(genre.getName()))
                .collect(Collectors.toList());
    }
}
