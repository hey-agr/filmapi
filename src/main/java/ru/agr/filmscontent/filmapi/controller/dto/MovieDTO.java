package ru.agr.filmscontent.filmapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author Arslan Rabadanov
 */
@AllArgsConstructor
public class MovieDTO implements Serializable {
    @Getter
    private List<MovieItem> Search;

    @Getter
    private String totalResults;

    @Getter
    private String Response;
}
