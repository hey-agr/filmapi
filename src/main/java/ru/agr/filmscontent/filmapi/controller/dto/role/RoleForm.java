package ru.agr.filmscontent.filmapi.controller.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleForm implements Serializable {
    private String name;

    private String description;

    private List<RolePermissionData> rolePermissionsData;

    @Data
    public static class RolePermissionData implements Serializable {
        private String authority;
    }
}
