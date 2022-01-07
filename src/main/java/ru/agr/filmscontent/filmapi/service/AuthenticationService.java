package ru.agr.filmscontent.filmapi.service;

import com.sun.istack.NotNull;
import ru.agr.filmscontent.filmapi.db.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Arslan Rabadanov
 */
public interface AuthenticationService {
    User getUser(HttpServletRequest request);

    User getUser(String token);

    User changePassword(@NotNull String currentPassword,
                        @NotNull String newPassword,
                        @NotNull User user);

    User changePassword(@NotNull String newPassword, @NotNull User user);

    String createToken(User user);
}
