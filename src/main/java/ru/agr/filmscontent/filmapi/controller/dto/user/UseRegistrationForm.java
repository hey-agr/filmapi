package ru.agr.filmscontent.filmapi.controller.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.agr.filmscontent.filmapi.db.entity.User;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UseRegistrationForm implements Serializable {
    @ApiModelProperty(value = "Username", position = 1, required = true)
    private String username;

    @ApiModelProperty(value = "Username", position = 2, required = true)
    private String password;

    @ApiModelProperty(value = "Name", position = 3)
    private String name;

    @ApiModelProperty(value = "Last Name", position = 4)
    private String lastName;

    @ApiModelProperty(value = "Middle Name", position = 5)
    private String middleName;

    @ApiModelProperty(value = "Gender", position = 6, allowableValues = "MAN, WOMAN")
    private User.Gender gender;

    @ApiModelProperty(value = "E-Mail", position = 7)
    private String email;

    @ApiModelProperty(value = "Theme", position = 8)
    private String theme;
}
