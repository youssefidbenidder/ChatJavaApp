package client;


import Entity.Message;
import Entity.User;
import common.MessageRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_MESSAGE;

public class MessageThread extends ChatThread implements Runnable {

    private User userTo;
    private User userFrom;
    private String message;

    public MessageThread(Socket socket, String authToken, Message message) throws IOException {
        super(socket, authToken);
        this.userTo = message.getUserTo();
        this.userFrom = message.getUserFrom();
        this.message = message.getMessage();
    }

    @Override
    public void run() {

        String responseId = null;
        MessageRequestBody requestBody = new MessageRequestBody(userFrom, userTo, message);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_MESSAGE, requestBody, authToken);

        try {
            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
