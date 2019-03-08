package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.UserEdit;
import ch.uzh.ifi.seal.soprafs19.service.EditService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EditUserController {

    private final EditService editSvc;

    EditUserController(EditService editSvc) {
        this.editSvc = editSvc;
    }

    @PostMapping("/edit")
    Boolean canEditUser(@RequestBody UserEdit editInfo) {
        try {
            return this.editSvc.canEditUser(editInfo);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
