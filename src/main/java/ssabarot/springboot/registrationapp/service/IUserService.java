package ssabarot.springboot.registrationapp.service;

import ssabarot.springboot.registrationapp.dto.UserDto;

import java.util.Optional;

public interface IUserService {
    Optional<UserDto> findUserById(Long id);

    UserDto createUser(UserDto userDto);
}
