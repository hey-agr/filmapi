package ru.agr.filmscontent.filmapi.controller.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserPassword extends UserPasswordBase {
    @Getter
    private String currentPassword;

    public UserPassword(String newPassword, String newPasswordConfirmation, String currentPassword) {
        super(newPassword, newPasswordConfirmation);
        this.currentPassword = currentPassword;
    }
}
