package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserTransfer;
import ch.uzh.ifi.seal.soprafs19.exception.HttpConflictException;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        //TODO CH: Return UserTransfer object
       return this.service.getUsers();
    }

    @PostMapping("/users")
    UserTransfer createUser(@RequestBody User newUser) {
        try {
             return new UserTransfer(this.service.createUser(newUser));
        } catch (Exception e) {
            throw new HttpConflictException();
        }
    }
}
