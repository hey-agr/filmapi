package ru.agr.filmscontent.filmapi.controller.dto.movie;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;

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

    private String year;

    private String imdbID;

    private String type;

    private String poster;

    private String description;

    private String country;

    private String video;

    private List<GenreItem> genres;
}
