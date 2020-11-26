package ru.agr.filmscontent.filmapi.controller.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {
    @ApiModelProperty(value = "Username", position = 1)
    private String username;

    @ApiModelProperty(value = "Password", position = 2)
    private String password;
}
