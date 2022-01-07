package ru.agr.filmscontent.filmapi.service;

import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;

/**
 * @author Arslan Rabadanov
 */
public interface RolePermissionService {
    RolePermission save(RolePermission RolePermission);

    void deleteByRole(Role role);
}
