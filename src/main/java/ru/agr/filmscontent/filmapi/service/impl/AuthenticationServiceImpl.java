package ru.agr.filmscontent.filmapi.service.impl;

import com.sun.istack.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.entity.User;
import ru.agr.filmscontent.filmapi.security.jwt.JwtTokenProvider;
import ru.agr.filmscontent.filmapi.service.AuthenticationService;
import ru.agr.filmscontent.filmapi.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * Authentication service
 *
 * @author Arslan Rabadanov
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticationServiceImpl(JwtTokenProvider jwtTokenProvider,
                                     PasswordEncoder passwordEncoder,
                                     UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public User getUser(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        return getUser(token);
    }

    @Override
    public User getUser(String token) {
        return userService.findByUsername(jwtTokenProvider.getUsername(token))
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user by token: " + token));
    }

    @Override
    public User changePassword(@NotNull String currentPassword, @NotNull String newPassword, @NotNull User user) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }
        return changePassword(newPassword, user);
    }

    @Override
    public User changePassword(@NotNull String newPassword, @NotNull User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userService.save(user);
    }

    @Override
    public String createToken(User user) {
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }
}
