package ru.agr.filmscontent.filmapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    List<RolePermission> deleteAllByRole(Role role);
}
