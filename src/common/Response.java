package common;


import Entity.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;

import static common.Constants.*;

public class Response {
    public String id;
    public String requestId;
    public String type;
    public String status;
    public PayloadBody body;
    public String message;

    public Response() {

    }

    public Response(String id, String requestId, String type, String status, PayloadBody body, String message) {
        this.id = id;
        this.requestId = requestId;
        this.type = type;
        this.status = status;
        this.body = body;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PayloadBody getBody() {
        return body;
    }

    public void setBody(PayloadBody body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Response fromJSON(String json) {
        System.out.println(" response json = " + json);
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObj = (JSONObject)parser.parse(json);

            Object requestId = jsonObj.get(KEY_REQUEST_ID);
            Object message = jsonObj.get(KEY_MESSAGE);

            Response response = new Response(
                    jsonObj.get(KEY_ID).toString(),
                    requestId != null ? requestId.toString() : null,
                    jsonObj.get(KEY_TYPE).toString(),
                    jsonObj.get(KEY_STATUS).toString(),
                    null,
                    message != null ? message.toString() : null);

            if(TYPE_AUTHORIZATION.equals(response.type)) {
                response.body = new AuthorizationResponseBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if(TYPE_INSCRIPTION.equals(response.type)){
                response.body = new InscriptionRequestBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if(TYPE_INVITATION_REQUEST.equals(response.type)){
                response.body  = new InvitationResponseBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }
            if(TYPE_MESSAGE.equals(response.type)){
                response.body  = new MessageResponseBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }if(TYPE_SEND_ONLINE_FRIENDS_LIST.equals(response.type)){
                response.body  = new ConnectedFriendsResponseBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }if (TYPE_CONNECTION_NOTIFICATION.equals(response.type)){
                response.body = new ConnectionNotificationBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }if (TYPE_DISCONNECTION_NOTIFICATION.equals(response.type)) {
                response.body = new DisconnectionNotificationBody().fromJSON((JSONObject) jsonObj.get(KEY_BODY));
            }
            if(TYPE_SEND_OFFLINE_FRIENDS_LIST.equals(response.type)){
                response.body  = new OfflineFriendsResponseBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }if(TYPE_OLD_MESSAGES.equals(response.type)){
                response.body = new OldMessageResponseBody().fromJSON((JSONObject)jsonObj.get(KEY_BODY));
            }

            return response;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public String toString() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_ID, id);
        obj.put(KEY_REQUEST_ID, requestId);
        obj.put(KEY_TYPE, type);
        obj.put(KEY_STATUS, status);
        obj.put(KEY_BODY, body!= null ? body.toJSON() : null);
        obj.put(KEY_MESSAGE, message);

        return obj.toString();
    }


    public static void main(String[] args) throws SQLException, java.text.ParseException {
        User user = new User("ouail",null);

    }
}
