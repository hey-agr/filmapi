package ru.agr.filmscontent.filmapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.agr.filmscontent.filmapi.db.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * User repository
 *
 * @author Arslan Rabadanov
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT DISTINCT e FROM User e " +
            "LEFT JOIN FETCH e.roles rls " +
            "LEFT JOIN FETCH rls.rolePermissions " +
            "WHERE e.username = :username ")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT DISTINCT e FROM User e " +
            "LEFT JOIN FETCH e.roles rls " +
            "LEFT JOIN FETCH rls.rolePermissions ")
    List<User> findAll();
}
