package common;

import Entity.User;
import org.json.simple.JSONObject;

import static common.Constants.*;

public class OldMessageRequestBody extends PayloadBody {
    User userFrom ;
    User userTo;

    public OldMessageRequestBody() {
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

    public OldMessageRequestBody(User userFrom, User userTo) {

        this.userFrom = userFrom;
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

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        OldMessageRequestBody oldMessageRequestBody= new OldMessageRequestBody();
        oldMessageRequestBody.userFrom = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER_FROM));
        oldMessageRequestBody.userTo = new User().fromJSON((JSONObject)jsonObj.get(KEY_USER_TO));
        return oldMessageRequestBody;
    }

}
