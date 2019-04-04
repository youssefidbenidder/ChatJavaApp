package common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static common.Constants.*;

public class Request {

    public String id;
    public String responseId;
    public String type;
    public PayloadBody body;
    public String authToken;

    public Request() {
    }

    public Request(String id, String responseId, String type, PayloadBody body, String authToken) {
        this.id = id;
        this.responseId = responseId;
        this.type = type;
        this.body = body;
        this.authToken = authToken;
    }

    public static Request fromJSON(String json) {
        System.out.println(" request json = " + json);
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObj = (JSONObject)parser.parse(json);

            Object responseId = jsonObj.get(KEY_RESPONSE_ID);
            Object authToken = jsonObj.get(KEY_AUTH_TOKEN);

            Request request = new Request(
                    jsonObj.get(KEY_ID).toString(),
                    responseId != null ? responseId.toString() : null,
                    jsonObj.get(KEY_TYPE).toString(),
                    null,
                    authToken != null ? authToken.toString() : null);

            if(TYPE_AUTHORIZATION.equals(request.type)) {
                request.body = new AuthorizationRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if(TYPE_INVITATION_REQUEST.equals(request.type)){
                request.body = new InvitationRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if(TYPE_MESSAGE.equals(request.type)){
                request.body = new MessageRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }if (TYPE_CONNECTION_NOTIFICATION.equals(request.type)){
                request.body = new ConnectionNotificationBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if (TYPE_DISCONNECTION_NOTIFICATION.equals(request.type)){
                request.body = new DisconnectionNotificationBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if(TYPE_INSCRIPTION.equals(request.type)){
                request.body = new InscriptionRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if(TYPE_SEND_ONLINE_FRIENDS_LIST.equals(request.type)){
                request.body = new ConnectedFriendsRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }if(TYPE_SEND_OFFLINE_FRIENDS_LIST.equals(request.type)){
                request.body = new OfflineFriendsRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }if(TYPE_OLD_MESSAGES.equals(request.type)){
                request.body = new OldMessageRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }

            return request;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PayloadBody getBody() {
        return body;
    }

    public void setBody(PayloadBody body) {
        this.body = body;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_ID, id);
        obj.put(KEY_RESPONSE_ID, responseId);
        obj.put(KEY_TYPE, type);
        obj.put(KEY_BODY, body!= null ? body.toJSON() : null);
        obj.put(KEY_AUTH_TOKEN, authToken);

        return obj.toString();
    }
}
