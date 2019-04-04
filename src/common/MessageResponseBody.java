package common;

import Entity.User;
import org.json.simple.JSONObject;

import static common.Constants.*;

public class MessageResponseBody extends PayloadBody {
    User userFrom;
    User userTo;
    String message;

    public MessageResponseBody() {
    }

    public MessageResponseBody(User userFrom, User userTo, String message) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.message = message;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USER_FROM, userFrom);
        obj.put(KEY_USER_TO, userTo);
        obj.put(KEY_MESSAGE, message);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        Object message = jsonObj.get(KEY_MESSAGE);
        MessageResponseBody messageResponseBody= new MessageResponseBody(null,null,message.toString());
        messageResponseBody.userFrom = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER_FROM));
        messageResponseBody.userTo = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER_TO));
        return messageResponseBody;
    }
}
