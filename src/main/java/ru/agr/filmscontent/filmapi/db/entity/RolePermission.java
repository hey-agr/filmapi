package ru.agr.filmscontent.filmapi.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.agr.filmscontent.filmapi.db.meta.DBMetaConstants;

import javax.persistence.*;

/**
 * Entity Role Permission
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = DBMetaConstants.SCHEMA, name = DBMetaConstants.RolePermissionTableConstants.name)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBMetaConstants.RolePermissionTableConstants.fld.id)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = DBMetaConstants.RolePermissionTableConstants.fld.authority)
    private Authority authority;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = DBMetaConstants.RolePermissionTableConstants.fld.role_id, nullable = false)
    private Role role;

    public enum Authority {
        ADD_EDIT_MOVIE("Add/Edit movie"),
        DELETE_MOVIE("Delete movie"),
        USER_ADMIN("User administration");

        private String description;

        Authority(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public static boolean hasName(String name) {
            for (Authority authority : Authority.values()) {
                if (authority.name().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }
}
