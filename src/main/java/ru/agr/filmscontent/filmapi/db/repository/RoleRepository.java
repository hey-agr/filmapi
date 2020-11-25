package ru.agr.filmscontent.filmapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.agr.filmscontent.filmapi.db.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
