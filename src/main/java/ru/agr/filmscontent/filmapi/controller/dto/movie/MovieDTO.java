package ru.agr.filmscontent.filmapi.controller.dto.movie;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@Getter
@SuperBuilder
public class MovieDTO {
    private List<MovieItem> search;

    private Long totalResults;

    private Boolean response;
}
