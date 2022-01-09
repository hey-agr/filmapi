package ru.agr.filmscontent.filmapi.service.mapper;

import org.jetbrains.annotations.Nullable;
import org.mapstruct.Mapper;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieItem;
import ru.agr.filmscontent.filmapi.db.entity.Movie;

import java.util.List;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = GenreMapper.class)
public interface MovieMapper {
    @Nullable Movie toEntity(@Nullable MovieItem movieItem);
    @Nullable MovieItem toItem(@Nullable Movie movie);
    @Nullable List<MovieItem> toItem(@Nullable List<Movie> movies);
}
