package ru.agr.filmscontent.filmapi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.agr.filmscontent.filmapi.controller.dto.role.RoleDTO;
import ru.agr.filmscontent.filmapi.controller.dto.role.RolePermissionDTO;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDto(Role role);
    @Mappings({
            @Mapping(target = "authorityDescription", source = "rolePermission.authority.description")
    })
    RolePermissionDTO toDto(RolePermission rolePermission);
}
