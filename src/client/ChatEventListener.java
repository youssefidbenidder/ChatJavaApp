package client;

import Entity.Message;
import Entity.User;

import java.util.List;

public interface ChatEventListener {
    void onConnectSuccess(String token, User user);
    void onConnectFailed();
    void onMessageReceived(User fromId, String content);
    void onOldMessagesReceived(User userFrom , User userTp , List<Message> messages);
    void onConnectedFriendsListReceived(List<User> users);
    void onOfflineFriendsListReceived(List<User> users);
    void onInvitationReceived(String fromId, String content);
    void onUserConnect(User username);   //void onUserConnect(String userId, String profile);
    void onUserLeave(User user);
}
