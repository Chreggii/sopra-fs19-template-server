package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserTransfer;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }

    @GetMapping("/users/{id}")
    UserTransfer getUser(@PathVariable("id") long id){
        var user = this.service.getUserById(id);

        if (user != null) {
            return new UserTransfer(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/users/{id}")
    ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable("id") long id) {
        // TODO CH: use existsuserbyid method
        var updatedUser = this.service.getUserById(id);
        if (updatedUser != null) {
            try {
                this.service.updateUser(id, user);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
            }
        } else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
