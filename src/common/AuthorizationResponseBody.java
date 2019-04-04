package common;

import Entity.User;
import org.json.simple.JSONObject;

import static common.Constants.KEY_AUTH_TOKEN;
import static common.Constants.KEY_USER;

public class AuthorizationResponseBody extends PayloadBody {

    private String token;
    private User user;

    public AuthorizationResponseBody() {

    }

    public AuthorizationResponseBody(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_AUTH_TOKEN, token);
        obj.put(KEY_USER, user);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        Object authToken = jsonObj.get(KEY_AUTH_TOKEN);
        AuthorizationResponseBody authorizationRequestBody = new AuthorizationResponseBody(
                authToken != null ? authToken.toString() : null,null);
        authorizationRequestBody.user = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER));
        return authorizationRequestBody;
    }

}
