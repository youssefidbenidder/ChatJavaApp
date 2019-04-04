package common;

import Entity.Message;
import Entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;

public class OldMessageResponseBody extends PayloadBody {
    User userFrom;
    User userTo;
    List<Message> messages;

    public OldMessageResponseBody() {
    }

    public OldMessageResponseBody(User userFrom, User userTo, List<Message> message) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.messages = message;
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

    public List<Message> getMessage() {
        return messages;
    }

    public void setMessage(List<Message> messages) {
        this.messages = messages;
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
        obj.put(KEY_OLD_MESSAGES, messages);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        User userFrom = new User().fromJSON((JSONObject) jsonObj.get(KEY_USER_FROM));
        User userTo = new User().fromJSON((JSONObject) jsonObj.get(KEY_USER_TO));

        Object messages = jsonObj.get(KEY_OLD_MESSAGES);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> oldMessages = new ArrayList<>();
        try {
            oldMessages = objectMapper.readValue(messages.toString(), new TypeReference<List<Message>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new OldMessageResponseBody(userFrom, userTo, oldMessages);
    }
}
