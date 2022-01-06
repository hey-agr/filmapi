package ru.agr.filmscontent.filmapi.db.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity User
 *
 * @author Arslan Rabadanov
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Entity
@Table(schema = FilmApiMetaUtils.SCHEMA, name = FilmApiMetaUtils.user.name)
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FilmApiMetaUtils.user.fld.id)
    private Long id;

    @Column(name = FilmApiMetaUtils.user.fld.username)
    private String username;

    @Column(name = FilmApiMetaUtils.user.fld.password)
    private String password;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FilmApiMetaUtils.user.fld.date_created)
    private LocalDateTime dateCreated;

    @Column(name = FilmApiMetaUtils.user.fld.blocked)
    private Boolean blocked;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = FilmApiMetaUtils.user_role.name, schema = FilmApiMetaUtils.SCHEMA,
            joinColumns = {@JoinColumn(name = FilmApiMetaUtils.user_role.fld.user_id, referencedColumnName = FilmApiMetaUtils.user.fld.id)},
            inverseJoinColumns = {@JoinColumn(name = FilmApiMetaUtils.user_role.fld.role_id, referencedColumnName = FilmApiMetaUtils.role.fld.id)})
    private Set<Role> roles;

    @Column(name = FilmApiMetaUtils.user.fld.name)
    private String name;

    @Column(name = FilmApiMetaUtils.user.fld.last_name)
    private String lastName;

    @Column(name = FilmApiMetaUtils.user.fld.middle_name)
    private String middleName;

    @Column(name = FilmApiMetaUtils.user.fld.avatar_data)
    private String avatarData;

    @Column(name = FilmApiMetaUtils.user.fld.avatar_filename)
    private String avatarFilename;

    @Enumerated(EnumType.STRING)
    @Column(name = FilmApiMetaUtils.user.fld.gender)
    private Gender gender;

    @Column(name = FilmApiMetaUtils.user.fld.email)
    private String email;

    @Column(name = FilmApiMetaUtils.user.fld.theme)
    private String theme;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Hibernate.isInitialized(roles)) {
            return roles.stream()
                    .flatMap(role -> role.getRolePermissions().stream())
                    .distinct()
                    .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getAuthority().name()))
                    .collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Gender {
        MAN,
        WOMAN;
    }
}
