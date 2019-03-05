package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class LogoutService {

    private final UserRepository userRepository;

    @Autowired
    public LogoutService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logout(String token) {
        var user = this.userRepository.findByToken(token);
        user.setToken(null);
        user.setStatus(UserStatus.OFFLINE);
        this.userRepository.save(user);
    }
}
