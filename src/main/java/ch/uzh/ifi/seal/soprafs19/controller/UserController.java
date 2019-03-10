package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserTransfer;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<UserTransfer> all() {
        //TODO CH: Return UserTransfer object
        List<UserTransfer> userTransfers = new ArrayList<>();
        this.service.getUsers().forEach(user -> {
            userTransfers.add(new UserTransfer(user, false));
        });
       return userTransfers;
    }

    @PostMapping("/users")
    String createUser(@RequestBody User newUser) {
        try {
             return "users/" + this.service.createUser(newUser).getId();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }

    @GetMapping("/users/{userId}")
    UserTransfer getUser(@PathVariable("userId") long id){
        var user = this.service.getUserById(id);

        if (user != null) {
            return new UserTransfer(user, false);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000", "https://sopra-fs19-chreggii-client.herokuapp.com"})
    @PutMapping("/users/{userId}")
    ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable("userId") long id) {
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
