package ru.agr.filmscontent.filmapi.db.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import ru.agr.filmscontent.filmapi.db.meta.DBMetaConstants;

import javax.persistence.*;
import java.util.List;

/**
 * Movie entity layer
 *
 * @author Arslan Rabadanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @Builder
@FieldNameConstants
@Entity
@Table(schema = DBMetaConstants.SCHEMA, name = DBMetaConstants.MovieTableConstants.name)
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBMetaConstants.MovieTableConstants.fld.id)
    private Long id;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.title)
    private String title;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.title_en)
    private String titleEn;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.year)
    private String year;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.imdbID)
    private String imdbID;

    @Enumerated(EnumType.STRING)
    @Column(name = DBMetaConstants.MovieTableConstants.fld.type)
    private MovieType type;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.poster)
    private String poster;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.description)
    private String description;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.country)
    private String country;

    @Column(name = DBMetaConstants.MovieTableConstants.fld.video)
    private String video;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = DBMetaConstants.MovieGenreTableConstants.name, schema = DBMetaConstants.SCHEMA,
            joinColumns = {
                @JoinColumn(name = DBMetaConstants.MovieGenreTableConstants.fld.movie_id, referencedColumnName = DBMetaConstants.MovieTableConstants.fld.id)
            },
            inverseJoinColumns = {
                @JoinColumn(name = DBMetaConstants.MovieGenreTableConstants.fld.genre_id, referencedColumnName = DBMetaConstants.GenreTableConstants.fld.id)
    })
    private List<Genre> genres;
}
