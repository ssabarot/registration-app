package ssabarot.springboot.registrationapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssabarot.springboot.registrationapp.dto.UserDto;
import ssabarot.springboot.registrationapp.exception.ResourceNotFoundException;
import ssabarot.springboot.registrationapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * A controller to receive the requests for the path /api/users
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;

    /**
     * GET method to retrieve a user by id
     *
     * @param id of the user we are looking for
     * @return a {@link UserDto} corresponding to the id or a ResourceNotFoundException if no user was found
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id).map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
    }

    /**
     * GET method to retrieve all users
     *
     * @return a {@link List<UserDto>} corresponding to the list of all users or a NO_CONTENT status if the list is empty
     */
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtosList = new ArrayList<>(userService.findAllUsers());

        if (userDtosList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userDtosList, HttpStatus.OK);
    }

    /**
     * POST method to create a new user
     *
     * @param userDto {@link UserDto} to create
     * @return the {@link UserDto} if successfully created or a 500 status response if userDto is invalid
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }
}
