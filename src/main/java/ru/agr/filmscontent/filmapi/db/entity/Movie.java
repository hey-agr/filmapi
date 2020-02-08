package ru.agr.filmscontent.filmapi.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.db.meta.FilmApiMeta;

import javax.persistence.*;

/**
 * Movie entity layer
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = FilmApiMeta.schema, name = FilmApiMeta.movie.name)
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = false)
public class Movie extends BaseEntity {
    @Id
    @Column(name = FilmApiMeta.movie.fld.id)
    private Long id;

    @Column(name = FilmApiMeta.movie.fld.title)
    private String title;

    @Column(name = FilmApiMeta.movie.fld.year)
    private Short year;

    @Column(name = FilmApiMeta.movie.fld.imdbID)
    private Integer imdbID;

    @Column(name = FilmApiMeta.movie.fld.type)
    private String type;

    @Column(name = FilmApiMeta.movie.fld.poster)
    private String poster;
}
