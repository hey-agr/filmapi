package ru.agr.filmscontent.filmapi.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data()
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserForm extends UserBaseForm {
    private String password;

    private LocalDateTime dateCreated;
}
