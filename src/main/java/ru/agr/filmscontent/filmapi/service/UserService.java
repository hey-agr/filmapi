package ru.agr.filmscontent.filmapi.service;

import ru.agr.filmscontent.filmapi.db.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Arslan Rabadanov
 */
public interface UserService {
    List<User> getAll();

    User save(User user);

    User register(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    void delete(User user);
}
