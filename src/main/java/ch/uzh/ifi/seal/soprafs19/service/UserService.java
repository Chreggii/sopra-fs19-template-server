package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User newUser) {
        newUser.setCreateDate(new Date());
        newUser.setStatus(UserStatus.OFFLINE);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    // Returns if user exists by username
    public Boolean existsUserByUsername(String username) {
        return this.userRepository.existsUserByUsername(username);
    }

    // Returns user by id
    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    // Returns user by username
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    // Returns all users
    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    // Updates the user
    public void updateUser(long userId, User user) {
        User updatedUser = this.userRepository.findById(userId);
        updatedUser.setUsername(user.getUsername());
        updatedUser.setBirthday(user.getBirthday());
        this.userRepository.save(updatedUser);
    }
}
