package ru.agr.filmscontent.filmapi.controller.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieItem;
import ru.agr.filmscontent.filmapi.controller.dto.role.RoleForm;
import ru.agr.filmscontent.filmapi.controller.dto.user.UserBaseForm;
import ru.agr.filmscontent.filmapi.db.entity.Genre;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.service.GenreService;
import ru.agr.filmscontent.filmapi.service.RoleService;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class ResponseHelper {

    private final RoleService roleService;

    private final GenreService genreService;

    public ResponseHelper(RoleService roleService,
                          GenreService genreService) {
        this.roleService = roleService;
        this.genreService = genreService;
    }

    public @NotNull ResponseEntity<MovieDTO> getMovieDtoResponse(@Nullable MovieItem movieItem, @NotNull HttpStatus status) {
        return getMoviesDtoResponse(nonNull(movieItem) ? Collections.singletonList(movieItem) : null, status);
    }

    public @NotNull ResponseEntity<MovieDTO> getMoviesDtoResponse(@Nullable List<MovieItem> movieItems, @NotNull HttpStatus status) {
        return ResponseEntity.status(status)
                .body(MovieDTO.builder()
                        .totalResults(nonNull(movieItems) ? movieItems.size() : 0L)
                        .search(nonNull(movieItems) ? movieItems : Collections.emptyList())
                        .response(nonNull(movieItems))
                        .build());
    }

    public Set<Role> convertRoleDataToRoles(List<UserBaseForm.RoleData> roleDataList) {
        return roleDataList.stream()
                .map(roleData -> roleService.findById(roleData.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<RolePermission.Authority> convertRolePermissionDataToAuthority(List<RoleForm.RolePermissionData> rolePermissionDataList) {
        return rolePermissionDataList.stream()
                .filter(rolePermissionData -> RolePermission.Authority.hasName(rolePermissionData.getAuthority()))
                .map(rolePermissionData -> RolePermission.Authority.valueOf(rolePermissionData.getAuthority()))
                .collect(Collectors.toSet());
    }

    public <FC, SC> void fillOnlyNonNullFields(FC firstObject, SC secondObject, ReflectionUtils.FieldFilter fieldFilter) {
        ReflectionUtils.doWithFields(firstObject.getClass(),
                callback -> {
                    callback.setAccessible(true);
                    if (nonNull(callback.get(firstObject))) {
                        Field secondObjectField = ReflectionUtils.findField(secondObject.getClass(), callback.getName());
                        if (nonNull(secondObjectField)) {
                            secondObjectField.setAccessible(true);
                            ReflectionUtils.setField(secondObjectField, secondObject, callback.get(firstObject));
                        } else {
                            throw new IllegalArgumentException("Field " + callback.getName() + " doesn't exist!");
                        }
                    }
                }, fieldFilter);
    }

    public Movie convertMovieItemToMovie(Movie movie, MovieItem movieItem) {
        fillOnlyNonNullFields(movieItem, movie, field -> !field.getName().equals(Movie.Fields.genres));
        if (nonNull(movieItem.getGenres())) {
            movie.setGenres(movieItem.getGenres().stream()
                    .map(this::convertGenreItemToGenre)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
            );
        }
        if (isNull(movie.getGenres())) {
            movie.setGenres(new ArrayList<>());
        }
        return movie;
    }

    public Genre convertGenreItemToGenre(GenreItem genreItem) {
        Genre genre = genreService.getByName(genreItem.getName());
        if (isNull(genre)) {
            return genreService.save(new Genre(null, genreItem.getName()));
        }
        return genre;
    }
}
