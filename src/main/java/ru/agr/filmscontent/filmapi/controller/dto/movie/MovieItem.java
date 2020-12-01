package ru.agr.filmscontent.filmapi.controller.dto.movie;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

import java.io.Serializable;
import java.util.List;

/**
 * Movie item
 *
 * @author Arslan Rabadanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel
public class MovieItem implements Serializable {
    @ApiModelProperty(value = "ID", position = 1)
    private Long id;

    @ApiModelProperty(value = "Title", position = 2)
    private String title;

    @ApiModelProperty(value = "Title (en)", position = 3)
    private String titleEn;

    @ApiModelProperty(value = "Year", position = 4)
    private String year;

    @ApiModelProperty(value = "IMDB ID", position = 5)
    private String imdbID;

    @ApiModelProperty(value = "Type", position = 6, allowableValues = "movie,series")
    private Movie.MovieType type;

    @ApiModelProperty(value = "Poster URL", position = 7)
    private String poster;

    @ApiModelProperty(value = "Description", position = 8)
    private String description;

    @ApiModelProperty(value = "Country", position = 9)
    private String country;

    @ApiModelProperty(value = "Video URL", position = 10)
    private String video;

    @ApiModelProperty(value = "Genres", position = 11)
    private List<GenreItem> genres;
}
