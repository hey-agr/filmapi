package ru.agr.filmscontent.filmapi.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDTO {
    private Long id;

    private String authority;

    private String authorityDescription;
}
