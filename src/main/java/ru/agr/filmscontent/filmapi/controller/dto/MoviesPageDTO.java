package ru.agr.filmscontent.filmapi.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MoviesPageDTO extends MovieDTO{
    private final Integer page;

    private final Integer totalPages;

    private final Integer pageSize;

    private final Long currentSize;

    public MoviesPageDTO(Integer page, Integer totalPages, Integer pageSize, Long currentSize, String totalResults, Boolean response, List<MovieItem> search) {
        super(search, totalResults, response);
        this.page = page;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.currentSize = currentSize;
    }
}
