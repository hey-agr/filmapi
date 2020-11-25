package ru.agr.filmscontent.filmapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.User;
import ru.agr.filmscontent.filmapi.db.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        if (passwordEncoder.upgradeEncoding(user.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.saveAndFlush(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
