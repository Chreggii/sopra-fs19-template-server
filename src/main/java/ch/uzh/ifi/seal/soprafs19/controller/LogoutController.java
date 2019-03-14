package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.service.AuthorizationService;
import ch.uzh.ifi.seal.soprafs19.service.LogoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LogoutController {

    private final AuthorizationService authorizationService;
    private final LogoutService logoutSvc;

    LogoutController(AuthorizationService authorizationService,  LogoutService logoutSvc) {
        this.authorizationService = authorizationService;
        this.logoutSvc = logoutSvc;
    }

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestHeader(value="Authorization") String token) {
        try {
            this.logoutSvc.logout(token);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with this token");
        }
    }
}
