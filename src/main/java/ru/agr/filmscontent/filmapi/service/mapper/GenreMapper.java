package ru.agr.filmscontent.filmapi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.db.entity.Genre;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    Genre toEntity(GenreItem genreItem);
    GenreItem toItem(Genre genre);
}
