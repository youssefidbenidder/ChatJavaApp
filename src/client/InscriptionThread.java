package client;

import common.InscriptionRequestBody;
import common.Request;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.TYPE_INSCRIPTION;

public class InscriptionThread extends ChatThread implements Runnable{

    private String username;
    private String password;
    private String email;
    private boolean sexe;

    public InscriptionThread(Socket socket, String authToken, String username, String password,String email,boolean sexe) throws IOException {
        super(socket, authToken);
        this.username = username;
        this.password = password;
        this.email = email;
        this.sexe = sexe;
    }

    public void run() {

        String responseId = null;
        InscriptionRequestBody requestBody = new InscriptionRequestBody(username, password , email, sexe);
        Request request = new Request(UUID.randomUUID().toString(), responseId, TYPE_INSCRIPTION, requestBody, authToken);
        try {
            out.println(request);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
