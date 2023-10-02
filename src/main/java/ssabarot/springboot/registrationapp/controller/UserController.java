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

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id).map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtosList = new ArrayList<>(userService.findAllUsers());

        if (userDtosList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userDtosList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }
}
