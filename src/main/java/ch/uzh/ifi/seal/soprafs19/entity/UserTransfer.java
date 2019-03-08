package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.util.Date;

public class UserTransfer {

    public UserTransfer(User user, Boolean passToken) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setStatus(user.getStatus());
        this.setCreateDate(user.getCreateDate());
        this.setBirthday(user.getBirthday());

        if (passToken) {
            this.setToken(user.getToken());
        }
    }

    private Long id;

    private String username;

    private String token;

    private UserStatus status;

    private Date createDate;

    private Date birthday;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
