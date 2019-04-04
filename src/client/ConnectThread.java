package client;

import common.AuthorizationRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_AUTHORIZATION;

public class ConnectThread extends ChatThread implements Runnable {

    private String username;
    private String password;

    public ConnectThread(Socket socket, String authToken, String username, String password) throws IOException {
        super(socket, authToken);
        this.username = username;
        this.password = password;
    }

    public void run() {

        String responseId = null;
        AuthorizationRequestBody requestBody = new AuthorizationRequestBody(username, password);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_AUTHORIZATION, requestBody, authToken);

        try {
            out.println(request);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
