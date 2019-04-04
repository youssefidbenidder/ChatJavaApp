package client;

import Entity.User;
import common.InvitationRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_INVITATION_REQUEST;

public class InvitationThread extends ChatThread implements Runnable {
    private User userFrom;
    private String userTo;

    public InvitationThread(Socket socket, String authToken, User userFrom, String userTo) throws IOException {
        super(socket, authToken);
        this.userFrom = userFrom;
        this.userTo = userTo;
    }

    @Override
    public void run() {

        String responseId = null;
        InvitationRequestBody requestBody = new InvitationRequestBody(userFrom, userTo,null);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_INVITATION_REQUEST, requestBody, authToken);
        try {

            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
