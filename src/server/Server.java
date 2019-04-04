package server;

import Entity.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    public static ServerSocket sServerSocket = null;
    private static HashMap<String, ServerClient> connectedClients = new HashMap<String, ServerClient>();

    public  static void AddClient(ServerClient serverClient) {
        if(!connectedClients.containsKey(serverClient.getUser())) {
            connectedClients.put(serverClient.getUser().getUsername(), serverClient);
        }
    }

    public static ServerClient getServerClient(String userId) {
        if(connectedClients.containsKey(userId)) {
           return connectedClients.get(userId);
        }

        return  null;
    }
    public static void deleteServerClient(String username){
        connectedClients.remove(username);
    }
    public static List<User> getUsers(){
        List<User> users = new ArrayList<>();
        for(String key: connectedClients.keySet()){
            users.add(connectedClients.get(key).getUser());
        }
        return users;
    }

    public static void main(String[] args) {

        try {
            sServerSocket = new ServerSocket(2009);
            System.out.println("Le serveur est à l'écoute du port "+ sServerSocket.getLocalPort());

            while(true) {
                (new Thread(new AccepterConnexion(sServerSocket.accept()))).start();
            }

        } catch (IOException e) {
            System.err.println("Le port "+ sServerSocket.getLocalPort()+" est déjà utilisé !");
        }

    }


}

