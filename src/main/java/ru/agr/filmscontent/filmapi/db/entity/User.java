package ru.agr.filmscontent.filmapi.db.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.agr.filmscontent.filmapi.db.meta.DBMetaConstants;

import javax.persistence.*;
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
@Table(schema = DBMetaConstants.SCHEMA, name = DBMetaConstants.UserTableConstants.name)
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBMetaConstants.UserTableConstants.fld.id)
    private Long id;

    @Column(name = DBMetaConstants.UserTableConstants.fld.username)
    private String username;

    @Column(name = DBMetaConstants.UserTableConstants.fld.password)
    private String password;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBMetaConstants.UserTableConstants.fld.date_created)
    private LocalDateTime dateCreated;

    @Column(name = DBMetaConstants.UserTableConstants.fld.blocked)
    private Boolean blocked;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = DBMetaConstants.UserRoleTableConstants.name, schema = DBMetaConstants.SCHEMA,
            joinColumns = {@JoinColumn(name = DBMetaConstants.UserRoleTableConstants.fld.user_id, referencedColumnName = DBMetaConstants.UserTableConstants.fld.id)},
            inverseJoinColumns = {@JoinColumn(name = DBMetaConstants.UserRoleTableConstants.fld.role_id, referencedColumnName = DBMetaConstants.RoleTableConstants.fld.id)})
    private Set<Role> roles;

    @Column(name = DBMetaConstants.UserTableConstants.fld.name)
    private String name;

    @Column(name = DBMetaConstants.UserTableConstants.fld.last_name)
    private String lastName;

    @Column(name = DBMetaConstants.UserTableConstants.fld.middle_name)
    private String middleName;

    @Column(name = DBMetaConstants.UserTableConstants.fld.avatar_data)
    private String avatarData;

    @Column(name = DBMetaConstants.UserTableConstants.fld.avatar_filename)
    private String avatarFilename;

    @Enumerated(EnumType.STRING)
    @Column(name = DBMetaConstants.UserTableConstants.fld.gender)
    private Gender gender;

    @Column(name = DBMetaConstants.UserTableConstants.fld.email)
    private String email;

    @Column(name = DBMetaConstants.UserTableConstants.fld.theme)
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
