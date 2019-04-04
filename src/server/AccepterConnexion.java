package server;

import Entity.Message;
import Entity.User;
import common.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static common.Constants.*;


public class AccepterConnexion implements Runnable {

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    public static final String AUTHTOKEN_TEST = "jkloduurejfksg126g5sfg489g3sdg";

    public AccepterConnexion(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        boolean isConnected = !socket.isClosed() && socket.isConnected();
        while (isConnected) {
            try {
                Request request = Request.fromJSON(in.readLine());


                if (TYPE_AUTHORIZATION.equals(request.type)) {
                    authenticate(request);
                }
                if (TYPE_SEND_ONLINE_FRIENDS_LIST.equals(request.type)) {
                    sendConnectedFriends(request);
                }
                if (TYPE_SEND_OFFLINE_FRIENDS_LIST.equals(request.type)) {
                    sendOfflineFriends(request);
                }
                if (TYPE_INVITATION_REQUEST.equals(request.type)) {
                    dispatchInvitation(request);
                }
                if (TYPE_MESSAGE.equals(request.type)) {
                    sendMessage(request);
                }
                if (TYPE_CONNECTION_NOTIFICATION.equals(request.type)) {
                    sendConnectionNotification(request);
                }
                if (TYPE_DISCONNECTION_NOTIFICATION.equals(request.type)) {
                    sendDisconnectionNotification(request);
                }
                if (TYPE_INSCRIPTION.equals(request.type))
                    inscription(request);
                if(TYPE_INVITATION_RESPONSE.equals(request)){
                    sendInvitation(request);
                }if(TYPE_OLD_MESSAGES.equals(request.type)){
                    sendOldMessages(request);
                }

            } catch (SocketException e) {
                isConnected = false;
            } catch (IOException e) {
                isConnected = false;
            }
        }

        //peer disconnected ....

    }

    public boolean authenticate(Request request) {

        String username = ((AuthorizationRequestBody) request.body).getUsername();
        String password = ((AuthorizationRequestBody) request.body).getPassword();
        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();

        try {
            if (Verfification_data_User(username, password)) {
                User user = new User(username, password);
                user = user.get_Data_User();
                new Server().AddClient(new ServerClient(socket, user));
                System.out.println(user);
                response.body = new AuthorizationResponseBody(AUTHTOKEN_TEST, user.get_Data_User());
                response.status = STATUS_SUCCESS;
                response.type = TYPE_AUTHORIZATION;
            } else {
                response.status = STATUS_FORBIDDEN;
                response.type = TYPE_AUTHORIZATION;
                response.body = new AuthorizationResponseBody();
            }

        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }

        out.println(response);
        out.flush();

        return true;
    }

    public static void main(String[] args) throws SQLException, ParseException, IOException {

    }

    public boolean inscription(Request request) {

        String username = ((InscriptionRequestBody) request.body).getUsername();
        String password = ((InscriptionRequestBody) request.body).getPassword();
        String email = ((InscriptionRequestBody) request.body).getEmail();
        Boolean sexe = ((InscriptionRequestBody) request.body).isSexe();
        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();

        try {
            if (!Verfification_data_User(username, null)) {
                User user = new User(username, password, email, sexe.toString());
                if (user.Add_data_user()) {
                    response.body = new InscriptionResponseBody(AUTHTOKEN_TEST, username);
                    response.status = STATUS_SUCCESS;
                    response.type = TYPE_INSCRIPTION;
                } else {
                    response.status = STATUS_FORBIDDEN;
                    response.type = TYPE_INSCRIPTION;
                    response.body = new InscriptionResponseBody();
                }
            } else {
                response.status = STATUS_FORBIDDEN;
                response.type = TYPE_INSCRIPTION;
                response.body = new InscriptionResponseBody();
            }
        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }

        out.println(response);
        out.flush();

        return true;
    }

    public void sendConnectedFriends(Request request) {
        User user = ((ConnectedFriendsRequestBody) request.body).getUser();
        List<User> friends = new ArrayList();
        try {
            friends = user.get_User_Friend();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<User> connectedUsers = Server.getUsers();
        List<User> connectedFriends = new ArrayList<>();
        for (User friend : friends) {
            if (connectedUsers.contains(friend)) {
                connectedFriends.add(friend);
            }
        }
        Response response = null;
        response = new Response(UUID.randomUUID().toString(), null, TYPE_SEND_ONLINE_FRIENDS_LIST, STATUS_SUCCESS,
                new ConnectedFriendsResponseBody(connectedFriends), null);

        out.println(response);
        out.flush();
    }

    public void sendOfflineFriends(Request request) {
        User user = ((OfflineFriendsRequestBody) request.body).getUser();
        List<User> friends = new ArrayList<>();
        try {
            friends = user.get_User_Friend();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<User> connectedUsers = Server.getUsers();
        List<User> offlineFriends = new ArrayList<>();
        for (User friend : friends) {
            if (!connectedUsers.contains(friend)) {
                offlineFriends.add(friend);
            }
        }
        Response response = new Response(UUID.randomUUID().toString(), null, TYPE_SEND_OFFLINE_FRIENDS_LIST, STATUS_SUCCESS,
                new ConnectedFriendsResponseBody(offlineFriends), null);
        out.println(response);
        out.flush();
    }

    public void sendConnectionNotification(Request request) {
        User userRequest = ((ConnectionNotificationBody) request.body).getUser();
        Response response = new Response(UUID.randomUUID().toString(), null, TYPE_CONNECTION_NOTIFICATION, STATUS_SUCCESS,
                null, null);
        List<User> users = Server.getUsers();
        List<User> friends = new ArrayList<>();
        try {
            friends = userRequest.get_User_Friend();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (User user : users) {
            if (!user.getUsername().equals(userRequest.getUsername()) && friends.contains(user)) {
                ServerClient serverClient = Server.getServerClient(user.getUsername());
                response.body = new ConnectionNotificationBody(user);
                PrintWriter out;
                try {
                    out = new PrintWriter(serverClient.getSocket().getOutputStream());
                    out.println(response);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendDisconnectionNotification(Request request) {
        User userRequest = ((DisconnectionNotificationBody) request.body).getUser();
        Response response = new Response(UUID.randomUUID().toString(), null, TYPE_DISCONNECTION_NOTIFICATION, STATUS_SUCCESS,
                null, null);
        Server.deleteServerClient(userRequest.getUsername());
        List<User> users = Server.getUsers();
        List<User> friends = new ArrayList<>();
        try {
            friends = userRequest.get_User_Friend();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (User user : users) {
            if (!user.getUsername().equals(userRequest.getUsername()) && friends.contains(user)) {
                ServerClient serverClient = Server.getServerClient(user.getUsername());
                response.body = new DisconnectionNotificationBody(user);
                PrintWriter out;
                try {
                    out = new PrintWriter(serverClient.getSocket().getOutputStream());
                    out.println(response);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dispatchInvitation(Request request) {

        User userFrom = ((InvitationRequestBody) request.body).getUserFrom();
        String userTo = ((InvitationRequestBody) request.body).getUserTo();
        //verification si userTo exist ou nn
        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();
        response.type = TYPE_INVITATION_REQUEST;
        ServerClient serverClient = Server.getServerClient(userTo);
        try {

            if (serverClient != null) {
                userFrom.Add_Friend(userTo);
                response.body = new InvitationResponseBody(userFrom, userTo, null);
                response.status = STATUS_SUCCESS;

            } else {
                response.status = STATUS_NOT_FOUND;
            }

        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }
        try {
            out = new PrintWriter(serverClient.getSocket().getOutputStream());
            out.println(response);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInvitation(Request request){
        User userFrom = ((InvitationRequestBody) request.body).getUserFrom();
        String userTo = ((InvitationRequestBody) request.body).getUserTo();
        String answer = ((InvitationRequestBody) request.body).getAnswer();
        User user;
        try {
            user = new User(userTo,null);
            if(answer.equals(INVITATION_ACCEPTED)){
                userFrom.response_Friend_request(user.get_Data_User_By_Username(),true);
            }if (answer.equals(INVITATION_REJECTED)){
                userFrom.response_Friend_request(user.get_Data_User_By_Username(),false);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

       Response response = new Response(UUID.randomUUID().toString(), null, TYPE_INVITATION_RESPONSE, STATUS_SUCCESS,
                new InvitationResponseBody(userFrom, null, null), null);
        try {

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(response);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Request request) {

        User userFrom = ((MessageRequestBody) request.body).getUserFrom();
        User userTo = ((MessageRequestBody) request.body).getUserTo();

        String message = ((MessageRequestBody) request.body).getMessage();
        try {
            System.out.println("thiiis is what we have " +userFrom);
            System.out.println("thiiis is what we have " +userTo);
            new Message(userFrom, userTo, message).add_message(userFrom, userTo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<User> connectedUsers = Server.getUsers();
        if (!connectedUsers.contains(userTo)) return;
        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();
        ServerClient serverClient = Server.getServerClient(userTo.getUsername());
        System.out.println(userTo.getUsername());
        try {
            if (serverClient != null) {
                response.body = new MessageResponseBody(userFrom, userTo, message);
                response.status = STATUS_SUCCESS;
                response.type = TYPE_MESSAGE;
            }

        } catch (Exception e) {
            response.status = STATUS_INTERNAL_SERVER_ERROR;
            response.message = e.getMessage();
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(serverClient.getSocket().getOutputStream());
            out.println(response);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendOldMessages(Request request){
        User userFrom = ((OldMessageRequestBody) request.body).getUserFrom();
        User userTo = ((OldMessageRequestBody) request.body).getUserTo();
        Response response = new Response();
        response.requestId = request.id;
        response.id = UUID.randomUUID().toString();
        try {
            List<Message> messages = userFrom.get_Data_Message(userTo);
            System.out.println(messages);
            response.body = new OldMessageResponseBody(userFrom, userTo,messages);
            response.status = STATUS_SUCCESS;
            response.type = TYPE_OLD_MESSAGES;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        out.println(response);
        out.flush();
    }

    private boolean Verfification_data_User(String info, String info1) throws SQLException, ParseException {
        User user = new User(info, info1);
        return user.Verification_data_user();
    }

}
