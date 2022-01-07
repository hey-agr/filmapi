package ru.agr.filmscontent.filmapi.service.impl;

import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.db.repository.RolePermissionRepository;
import ru.agr.filmscontent.filmapi.service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public RolePermission save(RolePermission RolePermission) {
        return rolePermissionRepository.saveAndFlush(RolePermission);
    }

    @Override
    public void deleteByRole(Role role) {
        rolePermissionRepository.deleteAllByRole(role);
    }
}
