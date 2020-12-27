package ru.agr.filmscontent.filmapi.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordBase {
    private String newPassword;

    private String newPasswordConfirmation;
}
