package ru.agr.filmscontent.filmapi.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.db.meta.FilmApiMeta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Genre entity layer
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = FilmApiMeta.SCHEMA, name = FilmApiMeta.genre.name)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Genre extends BaseEntity {
    @Id
    @Column(name = FilmApiMeta.genre.fld.id)
    private Long id;

    @Column(name = FilmApiMeta.genre.fld.name)
    private String name;
}
