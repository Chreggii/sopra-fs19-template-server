package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserTransfer;
import ch.uzh.ifi.seal.soprafs19.exception.HttpUnauthorizedException;
import ch.uzh.ifi.seal.soprafs19.service.LoginService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        if (this.userSvc.existsUserByUsername(user.getUsername())) {
            if (this.loginSvc.canLogin(user.getUsername(), user.getPassword())) {
                return new UserTransfer(this.loginSvc.login(user));
            } else {
                throw new HttpUnauthorizedException("Username or Password is wrong!");
            }
        } else {
            throw new HttpUnauthorizedException("Please sign in first!");
        }
    }
}
