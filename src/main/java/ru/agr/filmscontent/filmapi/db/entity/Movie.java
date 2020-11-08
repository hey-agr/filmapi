package ru.agr.filmscontent.filmapi.db.entity;

import lombok.*;
import ru.agr.filmscontent.filmapi.db.meta.FilmApiMetaUtils;

import javax.persistence.*;
import java.util.List;

/**
 * Movie entity layer
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = FilmApiMetaUtils.SCHEMA, name = FilmApiMetaUtils.movie.name)
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false) @Builder
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FilmApiMetaUtils.movie.fld.id)
    private Long id;

    @Column(name = FilmApiMetaUtils.movie.fld.title)
    private String title;

    @Column(name = FilmApiMetaUtils.movie.fld.title_en)
    private String titleEn;

    @Column(name = FilmApiMetaUtils.movie.fld.year)
    private Short year;

    @Column(name = FilmApiMetaUtils.movie.fld.imdbID)
    private String imdbID;

    @Enumerated(EnumType.STRING)
    @Column(name = FilmApiMetaUtils.movie.fld.type)
    private MovieType type;

    @Column(name = FilmApiMetaUtils.movie.fld.poster)
    private String poster;

    @Column(name = FilmApiMetaUtils.movie.fld.description)
    private String description;

    @Column(name = FilmApiMetaUtils.movie.fld.country)
    private String country;

    @Column(name = FilmApiMetaUtils.movie.fld.video)
    private String video;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = FilmApiMetaUtils.movie_genre.name, schema = FilmApiMetaUtils.SCHEMA,
            joinColumns = {
                @JoinColumn(name = FilmApiMetaUtils.movie_genre.fld.movie_id, referencedColumnName = FilmApiMetaUtils.movie.fld.id)
            },
            inverseJoinColumns = {
                @JoinColumn(name = FilmApiMetaUtils.movie_genre.fld.genre_id, referencedColumnName = FilmApiMetaUtils.genre.fld.id)
    })
    private List<Genre> genres;

    public enum MovieType {
        movie,
        series;
    }
}
