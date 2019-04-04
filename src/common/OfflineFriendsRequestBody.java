package common;

import Entity.User;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_USER;

public class OfflineFriendsRequestBody extends PayloadBody {

    private User user;

    public OfflineFriendsRequestBody() {
    }

    public OfflineFriendsRequestBody(User user) {
        this.user = user;
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
        obj.put(KEY_USER,user);
        return obj;

    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        OfflineFriendsRequestBody offlineFriendsRequestBody= new OfflineFriendsRequestBody();
        offlineFriendsRequestBody.user = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER));
        return offlineFriendsRequestBody;
    }
}