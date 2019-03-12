package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserEdit;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EditService {

    private final UserRepository userRepository;

    @Autowired
    public EditService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean canEditUser(UserEdit editInfo) {
        User user = this.userRepository.findById(editInfo.getId().longValue());
        if (user != null && user.getToken() != null) {
            return user.getToken().equals(editInfo.getToken());
        }
        return false;
    }
}
