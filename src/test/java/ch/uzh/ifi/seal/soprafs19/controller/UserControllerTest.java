package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.LoginService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allPass() throws Exception {
        String username = "testUserController1";

        Assert.assertNull(userRepository.findByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        testUser = userService.createUser(testUser);
        testUser = loginService.login(testUser);

        this.mockMvc.perform(
                get("/users")
                        .header("Authorization", testUser.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void allFailAuthorization() throws Exception {
        this.mockMvc.perform(
                get("/users")
                        .header("Authorization", "asdfdas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    public void createUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String username = "testUserController2";

        Assert.assertNull(userRepository.findByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        testUser = this.userService.createUser(testUser);
        String json = mapper.writeValueAsString(testUser);

        this.mockMvc.perform(
                post("/users")
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("users/" + testUser.getId()));
    }

    @Test
    public void getUserPass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String username = "testUserController4";

        Assert.assertNull(userRepository.findByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        testUser = this.userService.createUser(testUser);
        testUser = this.loginService.login(testUser);

        this.mockMvc.perform(
                get("/users/" + testUser.getId())
                        .header("Authorization", testUser.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    public void getUserFailUserNotExists() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String username = "testUserController5";

        Assert.assertNull(userRepository.findByUsername(username));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername(username);
        testUser.setBirthday(new Date());
        testUser.setPassword("testPassword");

        testUser = this.userService.createUser(testUser);
        testUser = this.loginService.login(testUser);

        this.mockMvc.perform(
                get("/user/9999")
                        .header("Authorization", testUser.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void getUserFailAuthorization() throws Exception {
        this.mockMvc.perform(
                get("/users/1")
                        .header("Authorization", "asdfdas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }
}