package Entity;

import com.google.gson.Gson;
import common.Constants;
import org.json.simple.JSONObject;
import provider.ApiCrud;

import java.sql.Date;
import java.sql.SQLException;

import static common.Constants.KEY_MESSAGE;
import static common.Constants.KEY_USER_FROM;
import static common.Constants.KEY_USER_TO;

public class Message {
    private int id;
    private int userfrom;
    private int userto;
    private String message;
    private Date dateenvoi;
    private User userTo;
    private User userFrom;

    public Message() {
    }

    public Message(User userfrom, User userto, String message, Date dateenvoi) {
        this.userfrom = userfrom.getId();
        this.userto = userto.getId();
        this.message = message;
        this.dateenvoi = dateenvoi;
    }

    public Message(int userfrom, int userto, String message, Date dateenvoi) {
        this.userfrom = userfrom;
        this.userto = userto;
        this.message = message;
        this.dateenvoi = dateenvoi;
    }

    public Message(int userfrom, int userto, String message) {
        this.userfrom = userfrom;
        this.userto = userto;
        this.message = message;
    }

    /*public Message(User userfrom, User userto, String message) {
        this.userFrom = userfrom.getId();
        this.userTo = userto.getId();
        this.message = message;
    }*/

    public Message(User userFrom, User userTo, String message) {
        this.message = message;
        this.userTo = userTo;
        this.userFrom = userFrom;
    }

    public boolean add_message(User userfrom, User userto) throws SQLException {
        return ApiCrud.add_message(userfrom.getId(), userto.getId(), this.message);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserfrom() {
        return userfrom;
    }

    public void setUserfrom(int userfrom) {
        this.userfrom = userfrom;
    }

    public int getUserto() {
        return userto;
    }

    public void setUserto(int userto) {
        this.userto = userto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateenvoi() {
        return dateenvoi;
    }

    public void setDateenvoi(Date dateenvoi) {
        this.dateenvoi = dateenvoi;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USER_TO, userTo);
        obj.put(KEY_USER_FROM, userFrom);
        obj.put(KEY_MESSAGE, message);

        return obj;
    }


    public Message fromJSON(JSONObject jsonObj) {
        final Gson gson = new Gson();
        if (jsonObj != null)
            return gson.fromJson(jsonObj.toString(), Message.class);
        else return null;
    }
    @Override
    public String toString() { return toJSON().toString(); }

}
