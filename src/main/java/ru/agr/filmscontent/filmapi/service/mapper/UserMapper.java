package ru.agr.filmscontent.filmapi.service.mapper;

import org.mapstruct.Mapper;
import ru.agr.filmscontent.filmapi.controller.dto.user.UserDTO;
import ru.agr.filmscontent.filmapi.db.entity.User;

/**
 * @author Arslan Rabadanov
 */
@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserDTO toDto(User user);
}
