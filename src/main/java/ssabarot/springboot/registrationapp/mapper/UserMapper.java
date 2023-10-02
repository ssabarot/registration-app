package ssabarot.springboot.registrationapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ssabarot.springboot.registrationapp.dto.UserDto;
import ssabarot.springboot.registrationapp.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User mapDtoToUser(final UserDto userDto);

    UserDto mapUserToDto(final User user);
}
