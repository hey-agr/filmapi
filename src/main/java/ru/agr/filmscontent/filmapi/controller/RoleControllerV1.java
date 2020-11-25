package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agr.filmscontent.filmapi.controller.dto.DtoConverter;
import ru.agr.filmscontent.filmapi.controller.dto.user.RoleForm;
import ru.agr.filmscontent.filmapi.controller.dto.user.UserBaseForm;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.db.entity.User;
import ru.agr.filmscontent.filmapi.service.AuthenticationService;
import ru.agr.filmscontent.filmapi.service.RolePermissionService;
import ru.agr.filmscontent.filmapi.service.RoleService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
public class RoleControllerV1 {

    private final RoleService roleService;

    private final AuthenticationService authenticationService;

    private final DtoConverter dtoConverter;

    private final RolePermissionService rolePermissionService;

    public RoleControllerV1(RoleService roleService,
                            AuthenticationService authenticationService,
                            RolePermissionService rolePermissionService,
                            DtoConverter dtoConverter) {
        this.roleService = roleService;
        this.authenticationService = authenticationService;
        this.dtoConverter = dtoConverter;
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        log.debug("Find all roles");
        if (!authenticationService.hasAuthority(request, RolePermission.Authority.USER_ADMIN)) {
            return authenticationService.authorityException(RolePermission.Authority.USER_ADMIN);
        }
        return ResponseEntity.ok(
                roleService.getAll().stream()
                        .map(dtoConverter::convertRoleToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request) {
        log.debug("Find role by id = " + id);
        if (!authenticationService.hasAuthority(request, RolePermission.Authority.USER_ADMIN)) {
            return authenticationService.authorityException(RolePermission.Authority.USER_ADMIN);
        }
        Role role = roleService.findById(id).orElseThrow(() -> new EntityNotFoundException("Role with id = " + id + " not found!"));
        return ok(dtoConverter.convertRoleToDTO(role));
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody RoleForm form, HttpServletRequest request) {
        log.debug("Save role");
        if (!authenticationService.hasAuthority(request, RolePermission.Authority.USER_ADMIN)) {
            return authenticationService.authorityException(RolePermission.Authority.USER_ADMIN);
        }

        final Role newRole = Role.builder()
                .name(form.getName())
                .description(form.getDescription())
                .build();

        Set<RolePermission.Authority> authorities =
                dtoConverter.convertRolePermissionDataToAuthority(form.getRolePermissionsData());

        if (authorities.isEmpty()) {
            throw new EntityNotFoundException("Role must have authority !");
        }

        Role savedRole = roleService.save(newRole);
        authorities.forEach(authority ->
                rolePermissionService.save(new RolePermission(null, authority, savedRole)));

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/api/v1/roles/{id}")
                        .buildAndExpand(savedRole.getId())
                        .toUri())
                .build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody RoleForm form, HttpServletRequest request) {
        log.debug("Update role with id = " + id);
        if (!authenticationService.hasAuthority(request, RolePermission.Authority.USER_ADMIN)) {
            return authenticationService.authorityException(RolePermission.Authority.USER_ADMIN);
        }

        final Role role = roleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with id = " + id + " not found!"));
        role.setName(form.getName());
        role.setDescription(form.getDescription());

        Set<RolePermission.Authority> authorities =
                dtoConverter.convertRolePermissionDataToAuthority(form.getRolePermissionsData());

        if (authorities.isEmpty()) {
            throw new EntityNotFoundException("Role must have authority !");
        }

        Role savedRole = roleService.save(role);
        rolePermissionService.deleteByRole(savedRole);
        authorities.forEach(authority ->
                rolePermissionService.save(new RolePermission(null, authority, savedRole)));

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/api/v1/roles/{id}")
                        .buildAndExpand(savedRole.getId())
                        .toUri())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        log.debug("Delete role by id = " + id);
        if (!authenticationService.hasAuthority(request, RolePermission.Authority.USER_ADMIN)) {
            return authenticationService.authorityException(RolePermission.Authority.USER_ADMIN);
        }
        Role role = roleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with id = " + id + " not found!"));
        roleService.delete(role);
        return noContent().build();
    }
}
