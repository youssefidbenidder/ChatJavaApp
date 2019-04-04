package client;

import Entity.User;
import common.InvitationRequestBody;
import common.InvitationResponseBody;
import common.Request;
import common.Response;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.STATUS_SUCCESS;
import static common.Constants.TYPE_INVITATION_REQUEST;
import static common.Constants.TYPE_INVITATION_RESPONSE;

public class InvitationAnswerThread extends ChatThread implements Runnable {
    private User userFrom;
    private String userTo;
    private String answer;

    public InvitationAnswerThread(Socket socket, String authToken, User userFrom, String userTo ,String answer) throws IOException {
        super(socket, authToken);
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.answer = answer;
    }

    @Override
    public void run() {

        String responseId = null;
        InvitationRequestBody requestBody= new InvitationRequestBody(userFrom, userTo,answer);
        Request request = new Request(UUID.randomUUID().toString(), responseId,TYPE_INVITATION_RESPONSE, requestBody,authToken);
        try {

            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
