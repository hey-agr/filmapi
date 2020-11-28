package ru.agr.filmscontent.filmapi.controller.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.agr.filmscontent.filmapi.db.entity.User;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public class UserBaseForm implements Serializable {
    @ApiModelProperty(value = "Username", position = 1)
    private String username;

    @ApiModelProperty(value = "User blocked", allowableValues = "true, false", position = 3)
    private Boolean blocked;

    @ApiModelProperty(value = "User role ids", position = 4)
    private List<RoleData> roles;

    @Data
    public static class RoleData implements Serializable {
        private Long id;
    }

    @ApiModelProperty(value = "Name", position = 5)
    private String name;

    @ApiModelProperty(value = "Last Name", position = 6)
    private String lastName;

    @ApiModelProperty(value = "Middle Name", position = 7)
    private String middleName;

    @ApiModelProperty(value = "Gender", position = 8, allowableValues = "MAN, WOMAN")
    private User.Gender gender;

    @ApiModelProperty(value = "E-Mail", position = 9)
    private String email;

    @ApiModelProperty(value = "Theme", position = 11)
    private String theme;
}
