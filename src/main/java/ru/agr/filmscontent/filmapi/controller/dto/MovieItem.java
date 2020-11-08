package ru.agr.filmscontent.filmapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author Arslan Rabadanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieItem implements Serializable {
    private String id;

    private String title;

    private String titleEn;

    private Short year;

    private String imdbID;

    private String type;

    private String poster;

    private String description;

    private String country;

    private String video;

    private List<GenreItem> genres;
}
