package ru.agr.filmscontent.filmapi.db.entity;

import lombok.*;
import ru.agr.filmscontent.filmapi.db.meta.DBMetaConstants;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity Role
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = DBMetaConstants.SCHEMA, name = DBMetaConstants.RoleTableConstants.name)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBMetaConstants.RoleTableConstants.fld.id)
    private Long id;

    @Column(name = DBMetaConstants.RoleTableConstants.fld.name)
    private String name;

    @Column(name = DBMetaConstants.RoleTableConstants.fld.description)
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "role")
    private Set<RolePermission> rolePermissions;
}
