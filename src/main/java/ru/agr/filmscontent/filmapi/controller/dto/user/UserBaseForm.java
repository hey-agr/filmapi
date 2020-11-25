package ru.agr.filmscontent.filmapi.controller.dto.user;

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
public class UserBaseForm implements Serializable {
    private String username;

    private Boolean blocked;

    private List<RoleData> roles;

    @Data
    public static class RoleData implements Serializable {
        private Long id;
    }
}
