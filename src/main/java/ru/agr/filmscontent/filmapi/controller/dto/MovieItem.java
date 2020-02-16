package ru.agr.filmscontent.filmapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 *
 *
 * @author Arslan Rabadanov
 */
@AllArgsConstructor
@Getter
public class MovieItem implements Serializable {
    private String id;

    private String title;

    private Short year;

    private String imdbID;

    private String type;

    private String poster;
}
