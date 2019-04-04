package common;

import Entity.User;
import org.json.simple.JSONObject;

import static common.Constants.KEY_USER;

public class DisconnectionNotificationBody extends PayloadBody {

    private User user;

    public DisconnectionNotificationBody() {
    }

    public DisconnectionNotificationBody(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USER, user);
        return obj;

    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        DisconnectionNotificationBody disconnectionNotificationBody= new DisconnectionNotificationBody();
        disconnectionNotificationBody.user = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER));
        return disconnectionNotificationBody;
    }
}
