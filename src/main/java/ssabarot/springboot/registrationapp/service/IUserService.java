package ssabarot.springboot.registrationapp.service;

import ssabarot.springboot.registrationapp.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<UserDto> findUserById(Long id);

    List<UserDto> findAllUsers();

    UserDto createUser(UserDto userDto);
}
