package common;

import Entity.User;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.*;

public class InvitationResponseBody extends PayloadBody {
    private User userFrom;
    private String userTo;
    private String answer;

    public InvitationResponseBody() {
    }

    public InvitationResponseBody(User userFrom, String userTo, String answer) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.answer = answer;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
        obj.put(KEY_ANSWER,answer);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        Object answer = jsonObj.get(KEY_ANSWER);
        Object userFrom = jsonObj.get(KEY_USER_FROM);
        Object userTo = jsonObj.get(KEY_USER_TO);
        InvitationResponseBody invitationResponseBody = new InvitationResponseBody();
        invitationResponseBody.userFrom = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER_FROM));
        invitationResponseBody.userTo = userTo.toString();
        invitationResponseBody.answer = null;
        return invitationResponseBody;
    }
}
