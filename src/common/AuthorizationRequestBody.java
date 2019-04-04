package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_PASSWORD;
import static common.Constants.KEY_USERNAME;


public class AuthorizationRequestBody extends PayloadBody {

    private String username;
    private String password;

    public AuthorizationRequestBody() {
    }

    public AuthorizationRequestBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_PASSWORD, password);
        obj.put(KEY_USERNAME, username);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), AuthorizationRequestBody.class);
    }
}
