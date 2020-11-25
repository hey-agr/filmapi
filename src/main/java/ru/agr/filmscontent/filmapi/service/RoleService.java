package ru.agr.filmscontent.filmapi.service;

import org.springframework.stereotype.Service;
import ru.agr.filmscontent.filmapi.db.entity.Role;
import ru.agr.filmscontent.filmapi.db.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
