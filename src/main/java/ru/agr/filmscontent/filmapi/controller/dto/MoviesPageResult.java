package ru.agr.filmscontent.filmapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MoviesPageResult {
    private Integer page;

    private Integer totalPages;

    private Integer pageSize;

    private Long currentSize;

    private Long totalResults;

    private Boolean response;

    private List<MovieItem> search;
}
