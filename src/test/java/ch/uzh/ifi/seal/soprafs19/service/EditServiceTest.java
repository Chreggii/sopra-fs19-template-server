package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.UserEdit;
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
public class EditServiceTest {

    @Autowired
    private EditService editService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Test
    public void canEditUser() {
        String username = "testUsernameEdit1";

        Assert.assertFalse(this.userService.existsUserByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        User createdUser = this.userService.createUser(testUser);

        var loggedUser = this.loginService.login(createdUser);

        var editInfo = new UserEdit();
        editInfo.setId(createdUser.getId());
        editInfo.setToken(loggedUser.getToken());

        Assert.assertTrue(this.editService.canEditUser(editInfo));
        editInfo.setId(Long.valueOf(0));
        Assert.assertFalse(this.editService.canEditUser(editInfo));
    }
}