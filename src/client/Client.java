package client;

import Entity.Message;
import Entity.User;
import common.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static common.Constants.*;

public class Client implements Runnable {

    public Socket socket;
    private BufferedReader in;
    private String authToken = null;
    User user;
    private List<ChatEventListener> listeners = new ArrayList<>();

    public void addChatEventListener(ChatEventListener listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                listeners.add((listener));
            }
        }
    }

    public static void main(String[] args) {

    }

    @Override
    public void run() {
        try {
            socket = new Socket("127.0.0.1", 2009);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                Response response = Response.fromJSON(in.readLine());
                System.out.println(response);
                if (TYPE_AUTHORIZATION.equals(response.type)) {
                    if (STATUS_SUCCESS.equals(response.status)) {
                        user = ((AuthorizationResponseBody) response.body).getUser();
                        authToken = ((AuthorizationResponseBody) response.body).getToken();
                        if (authToken != null) {
                            for (ChatEventListener listener :
                                    listeners) {
                                listener.onConnectSuccess(authToken,user);
                            }
                            continue;
                        }
                    }else if(STATUS_FORBIDDEN.equals(response.status)){
                        for (ChatEventListener listener :
                        listeners) {
                            listener.onConnectFailed();
                        }
                        continue;
                    }
                }
                if (TYPE_INSCRIPTION.equals(response.type)) {
                    if (STATUS_SUCCESS.equals(response.status)) {
                            for (ChatEventListener listener :
                                    listeners) {
                                listener.onConnectSuccess(authToken,null);
                            }
                    }else if(STATUS_FORBIDDEN.equals(response.status)){
                        for (ChatEventListener listener :
                                listeners) {
                            listener.onConnectFailed();
                        }
                        continue;
                    }
                }

                if(TYPE_INVITATION_REQUEST.equals(response.type)){
                    if (STATUS_SUCCESS.equals(response.status)) {
                        if(response.body != null){
                            String fromId = ((InvitationResponseBody)response.body).getUserFrom().getUsername();
                            for (ChatEventListener listener : listeners){
                                listener.onInvitationReceived(fromId,null);
                            }
                        }else {

                        }
                    }
                }
                if(TYPE_SEND_ONLINE_FRIENDS_LIST.equals(response.type)){
                    if(STATUS_SUCCESS.equals(response.status)){
                        List<User> users = ((ConnectedFriendsResponseBody)response.body).getUsers();
                        for (ChatEventListener listener : listeners){
                            listener.onConnectedFriendsListReceived(users);
                        }
                    }
                }
                if(TYPE_SEND_OFFLINE_FRIENDS_LIST.equals(response.type)){
                    if(STATUS_SUCCESS.equals(response.status)){
                        List<User> users = ((OfflineFriendsResponseBody)response.body).getUsers();
                        for (ChatEventListener listener : listeners){
                            listener.onOfflineFriendsListReceived(users);
                        }
                    }
                }
                if (TYPE_MESSAGE.equals(response.type)){
                    if(STATUS_SUCCESS.equals(response.status)){
                        User userFrom = ((MessageResponseBody)response.body).getUserFrom();
                        String content = ((MessageResponseBody)response.body).getMessage();
                        for (ChatEventListener listener : listeners){
                            listener.onMessageReceived(userFrom, content);
                        }
                    }
                }
                if (TYPE_CONNECTION_NOTIFICATION.equals(response.type)){
                    if (STATUS_SUCCESS.equals(response.status)){
                        User user = ((ConnectionNotificationBody)response.body).getUser();
                        for (ChatEventListener listener : listeners){
                            listener.onUserConnect(user);
                        }
                    }
                }
                if(TYPE_DISCONNECTION_NOTIFICATION.equals(response.type)){
                    if(STATUS_SUCCESS.equals(response.status)){
                        User user = ((DisconnectionNotificationBody)response.body).getUser();
                        for(ChatEventListener listener : listeners){
                            listener.onUserLeave(user);
                        }
                    }
                }if(TYPE_OLD_MESSAGES.equals(response.type)){
                    if(STATUS_SUCCESS.equals(response.status)){
                        User userfrom = ((OldMessageResponseBody)response.body).getUserFrom();
                        User userto = ((OldMessageResponseBody)response.body).getUserTo();
                        List<Message> messages = ((OldMessageResponseBody)response.body).getMessage();
                        for(ChatEventListener listener : listeners){
                            listener.onOldMessagesReceived(userfrom,userto,messages);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(String username, String password) throws IOException {
        new Thread(new ConnectThread(socket, authToken, username, password)).start();
        try {
            new Thread(new ConnectThread(socket, authToken, username, password)).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void invite(User userFrom, String userTo) throws IOException {
        new Thread(new InvitationThread(socket, authToken, userFrom, userTo)).start();
    }
    public void answerInvite(User userFrom, String userTo,String answer) throws IOException {
        new Thread(new InvitationAnswerThread(socket, authToken, userFrom, userTo ,answer)).start();
    }

    public void sendMessage(Message message) throws IOException {
        new Thread(new MessageThread(socket, authToken,message)).start();
    }
    public void loadOldMessage(User user1,User user2) throws IOException {
        new Thread(new OldMessageThread(socket, authToken,user1,user2)).start();
    }

    public void sendConnectedFriendsList(User user) throws IOException {
        new Thread(new SendConnectedFriendsListThread(socket , authToken, user)).start();
        try {
            new Thread(new SendConnectedFriendsListThread(socket , authToken, user)).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void sendOfflineFriendsList(User user) throws IOException {
        new Thread(new SendOfflineFriendsListThread(socket , authToken, user)).start();
    }
    public void ConnectionNotification(User user) throws IOException {
        new Thread(new ConnectionNotificationThread(socket , authToken, user)).start();
    }
    public void DisconnectionNotification(User user) throws IOException {
        new Thread(new DisconnectionNotificationThread(socket , authToken , user)).start();
    }
    public void inscriptionUser(String username,String password,String email,boolean sexe) throws IOException {
        new Thread(new InscriptionThread(socket,authToken,username,password,email,sexe)).start();
    }

}
