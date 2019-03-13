package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AuthorizationService {

    private final UserRepository userRepository;

    @Autowired
    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkAuthorization(String token) {
        if (token == null || !this.userRepository.existsUserByToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorize to perform this action");
        }
    }
}
