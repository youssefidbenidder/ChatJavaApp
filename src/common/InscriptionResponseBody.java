package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_TOKEN;
import static common.Constants.KEY_USERNAME;

public class InscriptionResponseBody extends PayloadBody {
    private String token;
    private String user;

    public InscriptionResponseBody() {

    }

    public InscriptionResponseBody(String token, String user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_TOKEN, token);
        obj.put(KEY_USERNAME, user);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), InscriptionRequestBody.class);
    }
}
