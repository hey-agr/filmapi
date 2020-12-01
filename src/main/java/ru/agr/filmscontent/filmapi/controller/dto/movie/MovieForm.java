package ru.agr.filmscontent.filmapi.controller.dto.movie;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Movie update/create item
 *
 * @author Arslan Rabadanov
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieForm extends MovieItem{
    @ApiModelProperty(hidden = true, position = 1)
    private Long id;
}
