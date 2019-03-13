package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.Logout;
import ch.uzh.ifi.seal.soprafs19.service.AuthorizationService;
import ch.uzh.ifi.seal.soprafs19.service.LogoutService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
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
    private final UserService userSvc;

    LogoutController(AuthorizationService authorizationService,  LogoutService logoutSvc, UserService userSvc) {
        this.authorizationService = authorizationService;
        this.logoutSvc = logoutSvc;
        this.userSvc = userSvc;
    }

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestHeader(value="Authorization") String token, @RequestBody Logout logout) {
        try {
            this.authorizationService.checkAuthorization(token);
            this.logoutSvc.logout(logout.getToken());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            if (ex instanceof ResponseStatusException) {
                throw ex;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with this token");
            }
        }
    }
}
