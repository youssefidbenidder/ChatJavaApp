package common;

import Entity.User;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;

import static common.Constants.KEY_BODY;
import static common.Constants.KEY_USER;
import static common.Constants.KEY_USERNAME;

public class ConnectedFriendsRequestBody extends PayloadBody {

    private User user;

    public ConnectedFriendsRequestBody(User user) {
        this.user = user;
    }

    public ConnectedFriendsRequestBody() {
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
        ConnectedFriendsRequestBody connectedFriendsRequestBody = new ConnectedFriendsRequestBody();
        connectedFriendsRequestBody.user = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER));
        return connectedFriendsRequestBody;
    }

    public static void main(String[] args){

            ConnectedFriendsRequestBody connectedFriendsRequestBody = new ConnectedFriendsRequestBody();
            User user = connectedFriendsRequestBody.getUser();
            System.out.println(user);
    }
}
