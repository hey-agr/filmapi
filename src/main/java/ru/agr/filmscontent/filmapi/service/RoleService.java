package ru.agr.filmscontent.filmapi.service;

import ru.agr.filmscontent.filmapi.db.entity.Role;

import java.util.List;
import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
public interface RoleService {
    Role save(Role role);

    Optional<Role> findById(Long id);

    List<Role> getAll();

    void delete(Role role);
}
