package client;

import Entity.User;
import common.OfflineFriendsRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_SEND_OFFLINE_FRIENDS_LIST;

public class SendOfflineFriendsListThread extends ChatThread implements Runnable {

    private User user;
    public SendOfflineFriendsListThread(Socket socket, String authToken, User user) throws IOException {
        super(socket, authToken);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        String responseId = null;
        OfflineFriendsRequestBody requestBody = new OfflineFriendsRequestBody(user);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_SEND_OFFLINE_FRIENDS_LIST, requestBody, authToken);

        try {
            out.println(request);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}