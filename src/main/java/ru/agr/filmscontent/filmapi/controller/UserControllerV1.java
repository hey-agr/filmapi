package ru.agr.filmscontent.filmapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agr.filmscontent.filmapi.controller.dto.ResponseHelper;
import ru.agr.filmscontent.filmapi.controller.dto.user.*;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.User;
import ru.agr.filmscontent.filmapi.service.AuthenticationService;
import ru.agr.filmscontent.filmapi.service.UserService;
import ru.agr.filmscontent.filmapi.service.mapper.UserMapper;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.springframework.http.ResponseEntity.*;

/**
 * User REST controller v1
 *
 * @author Arslan Rabadanov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final ResponseHelper dtoConverter;
    private final UserMapper userMapper;

    public UserControllerV1(UserService userService,
                            AuthenticationService authenticationService,
                            ResponseHelper dtoConverter,
                            UserMapper userMapper) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.dtoConverter = dtoConverter;
        this.userMapper = userMapper;
    }

    @GetMapping("/current")
    public ResponseEntity<UserDTO> currentUser(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Get current user");
        User currentUser = authenticationService.getUser(request);
        response.addHeader("Authorization", "Bearer " + authenticationService.createToken(currentUser));
        return ok(userMapper.toDto(currentUser));
    }

    @PostMapping("/current/change/password")
    public ResponseEntity<?> changePassword(@RequestBody UserPassword userPassword,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        log.debug("Change current user password");
        if (!userPassword.getNewPassword().equals(userPassword.getNewPasswordConfirmation())) {
            throw new BadCredentialsException("New password and password confirmation doesn't match !");
        }
        User currentUser = authenticationService.getUser(request);
        currentUser = authenticationService.changePassword(userPassword.getCurrentPassword(), userPassword.getNewPassword(), currentUser);
        response.addHeader("Authorization", "Bearer " + authenticationService.createToken(currentUser));
        return ok(userMapper.toDto(currentUser));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("Find all users");
        return ok(
                userService.getAll().stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        log.debug("Find user by id = " + id);
        User user = userService.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found!"));
        return ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("Delete user by id");
        User user = userService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found!"));
        userService.delete(user);
        return noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UserForm form, HttpServletRequest request) {
        log.debug("Save user");
        User newUser = User.builder()
                .blocked(false)
                .dateCreated(LocalDateTime.now())
                .username(form.getUsername())
                .password(form.getPassword())
                .name(form.getName())
                .lastName(form.getLastName())
                .middleName(form.getMiddleName())
                .gender(form.getGender())
                .email(form.getEmail())
                .theme(form.getTheme())
                .build();

        Set<Role> roles = dtoConverter.convertRoleDataToRoles(form.getRoles());

        if (roles.isEmpty()) {
            throw new EntityNotFoundException("User must have roles!");
        }

        newUser.setRoles(roles);
        newUser = userService.register(newUser);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/api/v1/users/{id}")
                        .buildAndExpand(newUser.getId())
                        .toUri())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserBaseForm form, HttpServletRequest request) {
        log.debug("Update user with id = " + id);

        final User user = userService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found!"));

        dtoConverter.fillOnlyNonNullFields(form, user, field -> !field.getName().equals(User.Fields.roles));

        if (nonNull(form.getRoles())) {
            Set<Role> roles = dtoConverter.convertRoleDataToRoles(form.getRoles());
            if (roles.isEmpty()) {
                throw new EntityNotFoundException("User must have roles!");
            }
            user.setRoles(roles);
        }

        userService.save(user);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/api/v1/users/{id}")
                        .buildAndExpand(user.getId())
                        .toUri())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UseRegistrationForm form, HttpServletRequest request) {
        log.debug("Register new user");
        User newUser = User.builder()
                .blocked(false)
                .dateCreated(LocalDateTime.now())
                .username(form.getUsername())
                .password(form.getPassword())
                .name(form.getName())
                .lastName(form.getLastName())
                .middleName(form.getMiddleName())
                .gender(form.getGender())
                .email(form.getEmail())
                .theme(form.getTheme())
                .build();

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/api/v1/users/{id}")
                        .buildAndExpand(userService.register(newUser).getId())
                        .toUri())
                .build();
    }

}
