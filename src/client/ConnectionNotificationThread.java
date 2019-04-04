package client;

import Entity.User;
import common.ConnectionNotificationBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_CONNECTION_NOTIFICATION;

public class ConnectionNotificationThread extends ChatThread implements Runnable  {

    private User username;

    public ConnectionNotificationThread(Socket socket, String authToken ,User username) throws IOException {
        super(socket, authToken);
        this.username = username;
    }

    @Override
    public void run() {

        String responseId = null;
        ConnectionNotificationBody requestBody = new ConnectionNotificationBody(username);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_CONNECTION_NOTIFICATION, requestBody, authToken);

        try {
            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
