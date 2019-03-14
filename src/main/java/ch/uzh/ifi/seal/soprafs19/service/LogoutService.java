package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LogoutService {

    private final UserRepository userRepository;

    @Autowired
    public LogoutService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Logs the user out => Sets token to null and status to OFFLINE
    public void logout(String token) {
        var user = this.userRepository.findByToken(token);
        user.setToken(null);
        user.setStatus(UserStatus.OFFLINE);
        this.userRepository.save(user);
    }
}
