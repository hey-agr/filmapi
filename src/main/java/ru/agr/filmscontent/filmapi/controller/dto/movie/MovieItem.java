package ru.agr.filmscontent.filmapi.controller.dto.movie;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.db.entity.MovieType;

import java.util.List;

/**
 * Movie item
 *
 * @author Arslan Rabadanov
 */
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieItem {
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
    private MovieType type;

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
