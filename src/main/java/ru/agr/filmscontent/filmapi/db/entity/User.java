package ru.agr.filmscontent.filmapi.db.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.agr.filmscontent.filmapi.db.meta.FilmApiMetaUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity User
 *
 * @author Arslan Rabadanov
 */
@Entity
@Table(schema = FilmApiMetaUtils.SCHEMA, name = FilmApiMetaUtils.user.name)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FilmApiMetaUtils.user.fld.id)
    private Long id;

    @NotEmpty
    @Column(name = FilmApiMetaUtils.user.fld.username)
    private String username;

    @NotEmpty
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Hibernate.isInitialized(roles)) {
            return roles.stream()
                    .flatMap(role -> role.getRolePermissions().stream())
                    .distinct()
                    .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getAuthority().name()))
                    .collect(Collectors.toList());
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
}
