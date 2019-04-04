package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatThread {

    protected  String authToken;
    protected Socket socket;
    protected PrintWriter out;

    public String getAuthToken() {
        return authToken;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public ChatThread(Socket socket, String authToken) throws IOException {
        this.socket = socket;
        this.authToken = authToken;
        this.out = new PrintWriter(socket.getOutputStream());
    }
}
