package ru.agr.filmscontent.filmapi.controller.dto.movie;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class MoviesPageDTO extends MovieDTO {
    private final Integer page;
    private final Integer totalPages;
    private final Integer size;
    private final Long currentSize;
}
