package common;

import Entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.Constants.KEY_USERS;

public class OfflineFriendsResponseBody extends PayloadBody {
    private List<User> users;

    public OfflineFriendsResponseBody() {
    }

    public OfflineFriendsResponseBody(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USERS, users);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        Object users = jsonObj.get(KEY_USERS);

        ObjectMapper objectMapper = new ObjectMapper();
        List<User> userList = new ArrayList<>();
        try {
            userList = objectMapper.readValue(users.toString(), new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new OfflineFriendsResponseBody(userList);
    }

}