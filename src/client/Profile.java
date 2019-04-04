package client;

public class Profile {
    private String userId;
    private String username;
    private String token;

    public Profile(String userId, String username, String token) {
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

    public Profile() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Profile(String user, String token) {
        this.username = user;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
