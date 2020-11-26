package ru.agr.filmscontent.filmapi.controller.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data()
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserForm extends UserBaseForm {
    @ApiModelProperty(value = "Password", position = 2)
    private String password;
}
