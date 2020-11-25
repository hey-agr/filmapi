package ru.agr.filmscontent.filmapi.service;

import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.db.entity.User;
import ru.agr.filmscontent.filmapi.security.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;

/**
 * Authentication service
 *
 * @author Arslan Rabadanov
 */
@Service
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public AuthenticationService(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public boolean hasAuthority(@NotNull String token, @NotNull RolePermission.Authority authority) {
        User user = getUser(token);
        return hasAuthority(user, authority);
    }

    public boolean hasAuthority(@NotNull User user, @NotNull RolePermission.Authority authority) {
        return user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority.name()));
    }

    public boolean hasAuthority(HttpServletRequest request, @NotNull RolePermission.Authority authority) {
        String token = jwtTokenProvider.resolveToken(request);
        return hasAuthority(token, authority);
    }

    public User getUser(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        return getUser(token);
    }

    public User getUser(String token) {
        return userService.findByUsername(jwtTokenProvider.getUsername(token))
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user by token"));
    }

    public ResponseEntity<?> authorityException(RolePermission.Authority authority) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("User doesn't have authority: " + authority);
    }
}
