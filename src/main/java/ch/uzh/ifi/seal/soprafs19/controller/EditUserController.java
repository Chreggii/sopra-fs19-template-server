package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.service.AuthorizationService;
import ch.uzh.ifi.seal.soprafs19.service.EditService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EditUserController {

    private final AuthorizationService authorizationService;
    private final EditService editService;

    EditUserController(AuthorizationService authorizationService, EditService editSvc) {
        this.authorizationService = authorizationService;
        this.editService = editSvc;
    }

    @PostMapping("/edit")
    Boolean canEditUser(@RequestHeader(value="Authorization") String token, @RequestBody Long id) {
        try {
            this.authorizationService.tryToAuthorize(token);
            return this.editService.canEditUser(id, token);
        } catch (Exception ex) {
            if (ex instanceof ResponseStatusException) {
                throw ex;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }
    }
}
