package server;

import Entity.User;

import java.net.Socket;

public class ServerClient {

    private Socket socket;
    private User user;

    public ServerClient(Socket socket, User user) {
        this.socket = socket;
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
