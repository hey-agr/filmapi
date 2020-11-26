package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agr.filmscontent.filmapi.controller.dto.DtoConverter;
import ru.agr.filmscontent.filmapi.controller.dto.user.RoleForm;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.service.AuthenticationService;
import ru.agr.filmscontent.filmapi.service.RolePermissionService;
import ru.agr.filmscontent.filmapi.service.RoleService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
public class RoleControllerV1 {

    private final RoleService roleService;

    private final DtoConverter dtoConverter;

    private final RolePermissionService rolePermissionService;

    public RoleControllerV1(RoleService roleService,
                            RolePermissionService rolePermissionService,
                            DtoConverter dtoConverter) {
        this.roleService = roleService;
        this.dtoConverter = dtoConverter;
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        log.debug("Find all roles");

        return ok(
                roleService.getAll().stream()
                        .map(dtoConverter::convertRoleToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id, HttpServletRequest request) {
        log.debug("Find role by id = " + id);

        Role role = roleService.findById(id).orElseThrow(() -> new EntityNotFoundException("Role with id = " + id + " not found!"));
        return ok(dtoConverter.convertRoleToDTO(role));
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody RoleForm form, HttpServletRequest request) {
        log.debug("Save role");

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

        Role role = roleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with id = " + id + " not found!"));
        roleService.delete(role);
        return noContent().build();
    }
}
