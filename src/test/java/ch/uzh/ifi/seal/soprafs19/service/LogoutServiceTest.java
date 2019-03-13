package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class LogoutServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private UserService userService;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Test
    public void logout() {
        String username = "testUsernameLogout2";
        String password = "testPassword";

        Assert.assertFalse(this.userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword(password);

        User createdUser = this.userService.createUser(testUser);

        Assert.assertNull(createdUser.getToken());
        if (createdUser.getStatus() != UserStatus.OFFLINE) {
            Assert.fail();
        }

        createdUser = this.loginService.login(createdUser);

        Assert.assertNotNull(createdUser.getToken());
        if (createdUser.getStatus() != UserStatus.ONLINE) {
            Assert.fail();
        }

        this.logoutService.logout(createdUser.getToken());
        createdUser = this.userRepository.findById(createdUser.getId().longValue());

        Assert.assertNull(createdUser.getToken());
        if (createdUser.getStatus() != UserStatus.OFFLINE) {
            Assert.fail();
        }
    }
}