package ch.uzh.ifi.seal.soprafs19.entity;

public class UserTransfer {

    public UserTransfer(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setUsername(user.getUsername());
        this.setToken(user.getToken());
    }

    private Long id;

    private String name;

    private String username;

    private String token;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
