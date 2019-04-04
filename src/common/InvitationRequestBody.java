package common;

import Entity.User;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.KEY_ANSWER;
import static common.Constants.KEY_USER_FROM;
import static common.Constants.KEY_USER_TO;

public class InvitationRequestBody extends PayloadBody {
    private User userFrom;
    private String userTo;
    private String answer;

    public InvitationRequestBody() {
    }

    public InvitationRequestBody(User userFrom, String userTo , String answer) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
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
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), InvitationRequestBody.class);
    }
}
