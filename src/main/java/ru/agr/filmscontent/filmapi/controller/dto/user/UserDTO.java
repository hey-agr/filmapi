package ru.agr.filmscontent.filmapi.controller.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.db.entity.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    @ApiModelProperty(value = "ID", position = 1, dataType = "long")
    private Long id;

    @ApiModelProperty(value = "Username", position = 2)
    private String username;

    @ApiModelProperty(value = "Account blocked", position = 3, allowableValues = "true, false")
    private Boolean blocked;

    @ApiModelProperty(value = "Date created", position = 4)
    private LocalDateTime dateCreated;

    @ApiModelProperty(value = "Roles ids", position = 5)
    private Set<RoleDTO> roles;

    @ApiModelProperty(value = "Name", position = 6)
    private String name;

    @ApiModelProperty(value = "Last Name", position = 7)
    private String lastName;

    @ApiModelProperty(value = "Middle Name", position = 8)
    private String middleName;

    @ApiModelProperty(value = "Gender", position = 9, allowableValues = "MAN, WOMAN")
    private User.Gender gender;

    @ApiModelProperty(value = "E-Mail", position = 10)
    private String email;

    @ApiModelProperty(value = "Avatar's filename", position = 11)
    private String avatarFilename;

    @ApiModelProperty(value = "Theme", position = 12)
    private String theme;
}
