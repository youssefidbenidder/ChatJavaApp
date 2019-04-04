package client;

import Entity.User;
import common.OldMessageRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_OLD_MESSAGES;

public class OldMessageThread extends ChatThread implements Runnable {


    private User userFrom;
    private User userTo;

    public OldMessageThread(Socket socket, String authToken, User userFrom, User userTo) throws IOException {
        super(socket, authToken);
        this.userFrom = userFrom;
        this.userTo = userTo;
    }

    @Override
    public void run() {

        String responseId = null;
        OldMessageRequestBody requestBody = new OldMessageRequestBody(userFrom, userTo);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_OLD_MESSAGES, requestBody, authToken);

        try {
            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

