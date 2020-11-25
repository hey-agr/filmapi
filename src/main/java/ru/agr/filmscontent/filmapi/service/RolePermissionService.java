package ru.agr.filmscontent.filmapi.service;

import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.db.repository.RolePermissionRepository;

@Service
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public RolePermission save(RolePermission RolePermission) {
        return rolePermissionRepository.saveAndFlush(RolePermission);
    }

    public void deleteByRole(Role role) {
        rolePermissionRepository.deleteAllByRole(role);
    }
}
