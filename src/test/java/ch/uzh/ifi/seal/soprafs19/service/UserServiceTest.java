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

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertNotNull(createdUser.getId());
        Assert.assertEquals(createdUser.getName(), userRepository.findById(createdUser.getId().longValue()).getName());
        Assert.assertEquals(createdUser.getUsername(), userRepository.findById(createdUser.getId().longValue()).getUsername());
        Assert.assertNotNull(createdUser.getCreateDate());
        Assert.assertEquals(createdUser.getPassword(), userRepository.findById(createdUser.getId().longValue()).getPassword());
        Assert.assertNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.OFFLINE);
    }

    @Test
    public void existsUserByUsername() {
        String username = "testUsername1";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        userService.createUser(testUser);

        Assert.assertTrue(userService.existsUserByUsername(username));
    }

    @Test
    public void getUserById() {
        String username = "testUsername2";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertEquals(createdUser.getId(), userService.getUserById(createdUser.getId().longValue()).getId());
    }

    @Test
    public void getUserByUsername() {
        String username = "testUsername3";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertEquals(createdUser.getUsername(), userRepository.findById(createdUser.getId().longValue()).getUsername());
    }

    @Test
    public void getUsers() {
        User firstUser = new User();
        firstUser.setName("testName");
        firstUser.setUsername("firstUser");
        firstUser.setBirthday(new Date());
        firstUser.setPassword("testPassword");
        User createdFirstUser = userService.createUser(firstUser);

        User secondUser = new User();
        secondUser.setName("testName");
        secondUser.setUsername("secondUser");
        secondUser.setBirthday(new Date());
        secondUser.setPassword("testPassword");
        User createdSecondUser = userService.createUser(secondUser);

        User thirdUser = new User();
        thirdUser.setName("testName");
        thirdUser.setUsername("thirdUser");
        thirdUser.setBirthday(new Date());
        thirdUser.setPassword("testPassword");
        User createdThirdUser = userService.createUser(thirdUser);

        var users = this.userService.getUsers();

        Boolean[] founds = new Boolean[3];
        founds[0] = false;
        founds[1] = false;
        founds[2] = false;

        users.forEach(user -> {
            if (user.getUsername().equals("firstUser")) {
                founds[0] = true;
            }
            if (user.getUsername().equals("secondUser")) {
                founds[1] = true;
            }
            if (user.getUsername().equals("thirdUser")) {
                founds[2] = true;
            }
        });

        Assert.assertTrue(founds[0]);
        Assert.assertTrue(founds[1]);
        Assert.assertTrue(founds[2]);
    }

    @Test
    public void updateUser() {
        String username = "testUsername4";

        Assert.assertFalse(userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = userService.createUser(testUser);

        Assert.assertEquals(createdUser.getUsername(), userRepository.findById(createdUser.getId().longValue()).getUsername());

        User updatedRequestedUser = new User();
        updatedRequestedUser.setUsername(username+"Updated");
        updatedRequestedUser.setBirthday(new Date());

        userService.updateUser(createdUser.getId(), updatedRequestedUser);

        User updatedUser = this.userService.getUserById(createdUser.getId());

        Assert.assertEquals(updatedRequestedUser.getUsername(), updatedUser.getUsername());
    }
}
