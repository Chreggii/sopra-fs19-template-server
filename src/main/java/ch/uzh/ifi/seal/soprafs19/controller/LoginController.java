package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserTransfer;
import ch.uzh.ifi.seal.soprafs19.service.LoginService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginController {

    private final LoginService loginSvc;
    private final UserService userSvc;

    LoginController(LoginService loginSvc, UserService userSvc) {
        this.loginSvc = loginSvc;
        this.userSvc = userSvc;
    }

    @PostMapping("/login")
    UserTransfer login(@RequestBody User user) {
        // Checks if user already exists and raises exception otherwise
        if (this.userSvc.existsUserByUsername(user.getUsername())) {
            // Checks if username and password matches and raises exception otherwise
            if (this.loginSvc.canLogin(user.getUsername(), user.getPassword())) {
                return new UserTransfer(this.loginSvc.login(user), true);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password is wrong");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please sign in first");
        }
    }
}
