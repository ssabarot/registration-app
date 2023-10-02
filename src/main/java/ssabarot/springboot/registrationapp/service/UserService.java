package ssabarot.springboot.registrationapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ssabarot.springboot.registrationapp.dto.UserDto;
import ssabarot.springboot.registrationapp.mapper.UserMapper;
import ssabarot.springboot.registrationapp.model.User;
import ssabarot.springboot.registrationapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> findUserById(Long id) {

        return userRepository.findById(id)
                .map(userMapper::mapUserToDto)
                .or(Optional::empty);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> usersList = Streamable.of(userRepository.findAll()).toList();

        return usersList.stream().map(userMapper::mapUserToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        final User userToSave = userMapper.mapDtoToUser(userDto);
        final User savedUser = userRepository.save(userToSave);

        return userMapper.mapUserToDto(savedUser);
    }
}
