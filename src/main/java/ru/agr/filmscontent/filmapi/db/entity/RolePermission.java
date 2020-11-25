package ru.agr.filmscontent.filmapi.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.agr.filmscontent.filmapi.db.meta.FilmApiMetaUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity Role Permission
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = FilmApiMetaUtils.SCHEMA, name = FilmApiMetaUtils.role_permission.name)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FilmApiMetaUtils.role_permission.fld.id)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = FilmApiMetaUtils.role_permission.fld.authority)
    private Authority authority;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = FilmApiMetaUtils.role_permission.fld.role_id, nullable = false)
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
