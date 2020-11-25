package ru.agr.filmscontent.filmapi.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private Long id;

    private String username;

    private LocalDateTime dateCreated;

    private Boolean blocked;

    private Set<RoleDTO> roles;
}
