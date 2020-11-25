package ru.agr.filmscontent.filmapi.controller.dto;

import org.springframework.stereotype.Component;
import ru.agr.filmscontent.filmapi.controller.dto.genre.GenreItem;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieDTO;
import ru.agr.filmscontent.filmapi.controller.dto.movie.MovieItem;
import ru.agr.filmscontent.filmapi.controller.dto.user.RoleDTO;
import ru.agr.filmscontent.filmapi.controller.dto.user.RoleForm;
import ru.agr.filmscontent.filmapi.controller.dto.user.RolePermissionDTO;
import ru.agr.filmscontent.filmapi.controller.dto.user.UserBaseForm;
import ru.agr.filmscontent.filmapi.controller.dto.user.UserDTO;
import ru.agr.filmscontent.filmapi.db.entity.Genre;
import ru.agr.filmscontent.filmapi.db.entity.Movie;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.db.entity.User;
import ru.agr.filmscontent.filmapi.service.GenreService;
import ru.agr.filmscontent.filmapi.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class DtoConverter {

    private final RoleService roleService;

    private final GenreService genreService;

    public DtoConverter(RoleService roleService,
                        GenreService genreService) {
        this.roleService = roleService;
        this.genreService = genreService;
    }

    public UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .blocked(user.getBlocked())
                .dateCreated(user.getDateCreated())
                .roles(user.getRoles().stream()
                        .map(this::convertRoleToDTO).collect(Collectors.toSet()))
                .build();
    }

    public RoleDTO convertRoleToDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .description(role.getDescription())
                .name(role.getName())
                .rolePermissions(role.getRolePermissions().stream()
                        .map(this::convertRolePermissionToDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public RolePermissionDTO convertRolePermissionToDTO(RolePermission rolePermission) {
        return RolePermissionDTO.builder()
                .id(rolePermission.getId())
                .authority(rolePermission.getAuthority().name())
                .authorityDescription(rolePermission.getAuthority().getDescription())
                .build();
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

    public MovieDTO getMovieDTO(List<Movie> movies) {
        if (movies != null) {
            return new MovieDTO(getMovieItems(movies),
                    Integer.valueOf(movies.size()).longValue(),
                    true);
        } else {
            return new MovieDTO(new ArrayList<>(), 0L, false);
        }
    }

    public Movie convertMovieItemToMovie(MovieItem movieItem) {
        return Movie.builder()
                .country(movieItem.getCountry())
                .description(movieItem.getDescription())
                .imdbID(movieItem.getImdbID())
                .type(Movie.MovieType.valueOf(movieItem.getType()))
                .poster(movieItem.getPoster())
                .title(movieItem.getTitle())
                .titleEn(movieItem.getTitleEn())
                .year(movieItem.getYear())
                .video(movieItem.getVideo())
                .genres(movieItem.getGenres().stream()
                        .map(this::convertGenreItemToGenre)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .build();
    }

    public Genre convertGenreItemToGenre(GenreItem genreItem) {
        Genre genre = genreService.getByName(genreItem.getName());
        if (isNull(genre)) {
            return genreService.save(new Genre(null, genreItem.getName()));
        }
        return genre;
    }

    public List<MovieItem> getMovieItems(List<Movie> movies) {
        return movies.stream()
                .map(movie ->
                        new MovieItem(movie.getId().toString(),
                                movie.getTitle(),
                                movie.getTitleEn(),
                                movie.getYear(),
                                movie.getImdbID(),
                                (movie.getType() != null) ? movie.getType().name() : "",
                                movie.getPoster(),
                                movie.getDescription(),
                                movie.getCountry(),
                                movie.getVideo(),
                                movie.getGenres().stream()
                                        .map(genre -> new GenreItem(genre.getName()))
                                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
