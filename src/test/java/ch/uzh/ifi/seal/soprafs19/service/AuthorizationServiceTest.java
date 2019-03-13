package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class AuthorizationServiceTest {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Test
    public void checkAuthorization() {
        String username = "testUsernameAuth1";

        Assert.assertFalse(this.userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = this.userService.createUser(testUser);;

        try {
            this.authorizationService.checkAuthorization(createdUser.getToken());
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }

        createdUser = this.loginService.login(createdUser);

        try {
            this.authorizationService.checkAuthorization(createdUser.getToken());
            Assert.assertTrue(true);
        } catch (Exception ex) {
            Assert.fail();
        }

    }
}