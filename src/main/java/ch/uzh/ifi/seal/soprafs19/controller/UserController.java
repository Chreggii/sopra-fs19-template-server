package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserTransfer;
import ch.uzh.ifi.seal.soprafs19.service.AuthorizationService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final AuthorizationService authorizationService;
    private final UserService service;

    UserController(AuthorizationService authorizationService, UserService service) {
        this.authorizationService = authorizationService;
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<UserTransfer> all(@RequestHeader(value="Authorization") String token) {
        this.authorizationService.tryToAuthorize(token);
        List<UserTransfer> userTransfers = new ArrayList<>();

        this.service.getUsers().forEach(user -> {
            userTransfers.add(new UserTransfer(user, false));
        });
        return userTransfers;
    }

    @PostMapping("/users")
    String createUser(@RequestBody User newUser) {
        try {
            // Creates a new user and returns its url
             return "users/" + this.service.createUser(newUser).getId();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }

    @GetMapping("/users/{userId}")
    UserTransfer getUser(@PathVariable("userId") long id, @RequestHeader(value="Authorization") String token){
        this.authorizationService.tryToAuthorize(token);

        var user = this.service.getUserById(id);
        if (user != null) {
            // Returns the corresponding user if id matches
            return new UserTransfer(user, false);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000", "https://sopra-fs19-chreggii-client.herokuapp.com"})
    @PutMapping("/users/{userId}")
    ResponseEntity<Void> updateUser(@PathVariable("userId") long id, @RequestHeader(value="Authorization") String token, @RequestBody User user) {
        this.authorizationService.tryToAuthorize(token);

        var updatedUser = this.service.getUserById(id);
        if (updatedUser != null) {
            try {
                // Updating user
                this.service.updateUser(id, user);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                // Raises exception if username already exists
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
            }
        } else {
            // Raises exception if user id not matches
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
