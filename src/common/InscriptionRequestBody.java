package common;

import com.google.gson.Gson;
import org.json.simple.JSONObject;

import static common.Constants.*;

public class InscriptionRequestBody extends PayloadBody {

    private String username;
    private String password;
    private String email;
    private boolean sexe;

    public InscriptionRequestBody(String username, String password, String email, boolean sexe) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sexe = sexe;
    }

    public InscriptionRequestBody() {

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSexe() {
        return sexe;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USERNAME, username);
        obj.put(KEY_PASSWORD, password);
        obj.put(KEY_EMAIL, email);
        obj.put(KEY_SEXE, sexe);

        return obj;
    }

    @Override
    public PayloadBody fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        return gson.fromJson(jsonObj.toString(), InscriptionRequestBody.class);
    }

}
