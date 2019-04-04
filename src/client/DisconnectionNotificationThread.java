package client;

import Entity.User;
import common.ConnectionNotificationBody;
import common.DisconnectionNotificationBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_CONNECTION_NOTIFICATION;
import static common.Constants.TYPE_DISCONNECTION_NOTIFICATION;

public class DisconnectionNotificationThread extends ChatThread implements Runnable  {

    private User user;

    public DisconnectionNotificationThread (Socket socket, String authToken ,User user) throws IOException {
        super(socket, authToken);
        this.user = user;
    }

    @Override
    public void run() {

        String responseId = null;
        DisconnectionNotificationBody requestBody = new DisconnectionNotificationBody(user);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_DISCONNECTION_NOTIFICATION, requestBody, authToken);

        try {
            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
