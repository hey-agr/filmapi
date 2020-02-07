package ru.agr.filmscontent.filmapi.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data @NoArgsConstructor @AllArgsConstructor
public class Movie extends BaseEntity {

    @Id
    @Column(name = FilmApiMeta.movie.fld.id)
    private Long id;

}
