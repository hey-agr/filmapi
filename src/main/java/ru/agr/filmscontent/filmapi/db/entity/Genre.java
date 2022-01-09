package ru.agr.filmscontent.filmapi.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.db.meta.DBMetaConstants;

import javax.persistence.*;

/**
 * Genre entity layer
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = DBMetaConstants.SCHEMA, name = DBMetaConstants.GenreTableConstants.name)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Genre extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBMetaConstants.GenreTableConstants.fld.id)
    private Long id;

    @Column(name = DBMetaConstants.GenreTableConstants.fld.name)
    private String name;
}
