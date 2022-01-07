package ru.agr.filmscontent.filmapi.db.entity;

import lombok.*;
import ru.agr.filmscontent.filmapi.db.meta.FilmApiMetaUtils;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity Role
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = FilmApiMetaUtils.SCHEMA, name = FilmApiMetaUtils.role.name)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FilmApiMetaUtils.role.fld.id)
    private Long id;

    @Column(name = FilmApiMetaUtils.role.fld.name)
    private String name;

    @Column(name = FilmApiMetaUtils.role.fld.description)
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "role")
    private Set<RolePermission> rolePermissions;
}
