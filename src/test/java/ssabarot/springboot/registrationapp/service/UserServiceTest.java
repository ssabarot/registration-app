package ssabarot.springboot.registrationapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ssabarot.springboot.registrationapp.dto.UserDto;
import ssabarot.springboot.registrationapp.mapper.UserMapper;
import ssabarot.springboot.registrationapp.mapper.UserMapperImpl;
import ssabarot.springboot.registrationapp.model.Gender;
import ssabarot.springboot.registrationapp.model.User;
import ssabarot.springboot.registrationapp.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {UserService.class, UserMapperImpl.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test findUserById Success")
    void test_findUserById_ok() {
        // given
        User user = User.builder().id(1L).name("brichard").birthdate(LocalDate.of(2001, 9, 11)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();
        doReturn(Optional.of(user)).when(userRepository).findById(1L);

        // when
        Optional<UserDto> optionalUser = userService.findUserById(1L);

        // then
        assertTrue(optionalUser.isPresent(), "User was not found.");
        assertThat(optionalUser.get()).isInstanceOf(UserDto.class);
        assertEquals(optionalUser.get(), userMapper.mapUserToDto(user), "The user found was not the same as the mock.");
    }

    @Test
    @DisplayName("Test findUserById NotFound")
    void test_findUserById_notFound() {
        // given
        User user = User.builder().id(1L).name("brichard").birthdate(LocalDate.of(2001, 9, 11)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();
        doReturn(Optional.of(user)).when(userRepository).findById(1L);

        // when
        Optional<UserDto> optionalUser = userService.findUserById(2L);

        // then
        assertFalse(optionalUser.isPresent(), "User was not found.");
        assertThat(optionalUser).isEmpty();
    }

    @Test
    @DisplayName("Test findAllUsers Success")
    void test_findAllUsers_ok() {
        // given
        List<User> userList = new ArrayList<>();
        User userOne = User.builder().id(1L).name("brichard").birthdate(LocalDate.of(2001, 9, 11)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();
        User userTwo = User.builder().id(2L).name("ldupuis").birthdate(LocalDate.of(1987, 6, 25)).country("France").phoneNumber("0657779149").gender(Gender.FEMALE).build();
        User userThree = User.builder().id(3L).name("dpetit").birthdate(LocalDate.of(1996, 2, 8)).country("France").phoneNumber("0755749447").gender(Gender.OTHER).build();

        userList.add(userOne);
        userList.add(userTwo);
        userList.add(userThree);

        when(userRepository.findAll()).thenReturn(userList);

        // when
        List<UserDto> userListResult = userService.findAllUsers();

        // then
        assertEquals(3, userListResult.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test findAllUsers Empty")
    void test_findAllUsers_empty() {
        // given
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        // when
        List<UserDto> userListResult = userService.findAllUsers();

        // then
        assertEquals(0, userListResult.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test createUser Success")
    void test_createUser_ok() {
        // given
        UserDto userToCreate = UserDto.builder().id(1L).name("lrobert").birthdate(LocalDate.of(1993, 8, 30)).country("France").phoneNumber("0552749249").gender(Gender.FEMALE).build();

        // when
        userService.createUser(userToCreate);

        // then
        verify(userRepository, times(1)).save(any());
    }
}