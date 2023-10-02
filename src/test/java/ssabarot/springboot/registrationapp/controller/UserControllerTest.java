package ssabarot.springboot.registrationapp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ssabarot.springboot.registrationapp.dto.UserDto;
import ssabarot.springboot.registrationapp.exception.ResourceNotFoundException;
import ssabarot.springboot.registrationapp.mapper.UserMapper;
import ssabarot.springboot.registrationapp.mapper.UserMapperImpl;
import ssabarot.springboot.registrationapp.model.Gender;
import ssabarot.springboot.registrationapp.model.User;
import ssabarot.springboot.registrationapp.repository.UserRepository;
import ssabarot.springboot.registrationapp.service.UserService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {UserController.class, UserMapperImpl.class})
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Test findUserById Success")
    void test_findUserById_ok() {
        // given
        UserDto user = UserDto.builder().id(1L).name("dtintin").birthdate(new Date(1990, Calendar.JANUARY,11)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();
        doReturn(Optional.of(user)).when(userService).findUserById(1L);

        // when
        ResponseEntity<UserDto> response = userController.findUserById(1L);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
        verify(userService, times(1)).findUserById(any());
    }

    @Test
    @DisplayName("Test findUserById ResourceNotFoundException")
    void test_findUserById_not_found() {
        // given
        UserDto user = UserDto.builder().id(1L).name("dtintin").birthdate(new Date(1990, Calendar.JANUARY,11)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();
        doReturn(Optional.of(user)).when(userService).findUserById(1L);

        // when + then
        final Long id = 2L;

        Throwable exception = assertThrows(
                ResourceNotFoundException.class, () -> {
                    userController.findUserById(id);
                }
        );

        assertEquals("User with id " + id + " not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Test getAllUsers Success")
    void test_getAllUsers_ok() {
        // given
        List<UserDto> userList = new ArrayList<>();
        UserDto userOne = UserDto.builder().id(1L).name("brichard").birthdate(new Date(2001, Calendar.SEPTEMBER,11)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();
        UserDto userTwo = UserDto.builder().id(2L).name("ldupuis").birthdate(new Date(1987, Calendar.JUNE,25)).country("France").phoneNumber("0657779149").gender(Gender.FEMALE).build();
        UserDto userThree = UserDto.builder().id(3L).name("dpetit").birthdate(new Date(1996, Calendar.FEBRUARY,8)).country("France").phoneNumber("0755749447").gender(Gender.OTHER).build();

        userList.add(userOne);
        userList.add(userTwo);
        userList.add(userThree);

        when(userService.findAllUsers()).thenReturn(userList);

        // when
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userList);
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    @DisplayName("Test createUser Success")
    void test_createUser_ok() {
        // given
        UserDto userToCreate = UserDto.builder().id(1L).name("lrobert").birthdate(new Date(1993, Calendar.AUGUST,30)).country("France").phoneNumber("0552749249").gender(Gender.FEMALE).build();
        when(userService.createUser(userToCreate)).thenReturn(userToCreate);

        // when
        ResponseEntity<UserDto> response = userController.createUser(userToCreate);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userToCreate);
        verify(userService, times(1)).createUser(any());
    }
}