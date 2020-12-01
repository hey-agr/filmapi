package ru.agr.filmscontent.filmapi.controller.dto.movie;

import lombok.Getter;

import java.util.List;

@Getter
public class MoviesPageDTO extends MovieDTO{
    private final Integer page;

    private final Integer totalPages;

    private final Integer size;

    private final Long currentSize;

    public MoviesPageDTO(Integer page, Integer totalPages, Integer size, Long currentSize, Long totalResults, Boolean response, List<MovieItem> search) {
        super(search, totalResults, response);
        this.page = page;
        this.totalPages = totalPages;
        this.size = size;
        this.currentSize = currentSize;
    }
}
