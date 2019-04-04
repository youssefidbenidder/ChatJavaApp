package Entity;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import provider.ApiCrud;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static common.Constants.*;

public class User {
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String nom;
    protected boolean sexe;
    protected String photo;
    protected Date birth;

    public User(String username, String password, String email , String sexe) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.sexe = Boolean.parseBoolean(sexe);
        System.out.println("nouvelle utilisateur");
    }

    public User(String username, String password) throws SQLException, ParseException {
        this.username=username;
        this.password=password;
    }

    public User(){
        System.out.println("verification utilisateur");
    }

    public User(int id){
        this.id = id;
    }

    public User(int id, String username, String password,
                String email,String nom,boolean sexe,String photo
                ,Date birth) throws ParseException {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nom = nom;
        this.sexe = sexe;
        this.photo = photo;
        this.birth = birth;
    }

    public boolean Verification_data_user() throws SQLException, ParseException {
        return ApiCrud.verification_data_user(this.username,this.password);
    }

    public User get_User_By_Id() throws ParseException, SQLException {
        String[] data = ApiCrud.get_data_user(this.getId());
        return retour(data);
    }

    public User get_Data_User_By_Username() throws SQLException, ParseException {
        String[] data = ApiCrud.get_data_user(this.username,null);
        return retour(data);
    }


    public List<Message> get_Data_Message(User user2) throws SQLException, ParseException {
        return ApiCrud.get_message(this,user2);
    }

    public List<User> get_User_Friend() throws SQLException, ParseException {
        List<Integer> array = ApiCrud.getUserFriend(this.get_Data_User_By_Username().getId());
        List<User> users = new ArrayList<User>() ;
        for(int i=0;i<array.size();i++){
            User friend = new User(array.get(i));
            users.add(friend.get_User_By_Id());
        }
        return users;
    }

    public User get_Data_User() throws SQLException, ParseException {
        String[] data = ApiCrud.get_data_user(this.username,this.password);
        return retour(data);
    }

    public boolean Add_Friend(String username) throws SQLException, ParseException {
        String[] data = ApiCrud.get_data_user(username,null);
        System.out.println(data);
        if(data == null)
            return false;
        else return ApiCrud.add_friend(this.getId(),Integer.parseInt(data[0]));
    }

    public List<User> get_friend_request() throws SQLException, ParseException {
        List<Integer> data = ApiCrud.get_Friend_Request(this.getId());
        if(data==null)
            return null;
        else {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                User user = new User(data.get(i));
                users.add(user.get_User_By_Id());
            }
            return users;
        }
    }

    public User retour(String[] data) throws ParseException {
        if(data[7] != null)
            return new User(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4],Boolean.parseBoolean(data[5]),data[6],new SimpleDateFormat("yyyy-MM-dd").parse(data[7]));
        else
            return new User(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4],Boolean.parseBoolean(data[5]),data[6],null);
    }

    public boolean Add_data_user() throws SQLException, ParseException {
        ApiCrud crud = new ApiCrud();
        return crud.add_data_user(this.username,this.password,this.email,this.sexe);
    }

    public boolean update_user() throws SQLException, ParseException {
        if(!this.Verification_data_user())
            return ApiCrud.update_user_data(this);
        else return false;
    }

    public int getId() {
        return id;
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

    public String getNom() {
        return nom;
    }

    public boolean isSexe() {
        return sexe;
    }

    public String getPhoto() {
        return photo;
    }

    public Date getBirth() {
        return birth;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public boolean response_Friend_request(User user,boolean response) throws SQLException, ParseException {

        if(this == null || user == null)
            return false;
        else {
            Invited inv = new Invited(user.getId(),this.getId());
            int x = inv.get_inv_id();
            System.out.println(x);
            if(x==0){
                return false;
            }
            else{
                return ApiCrud.response_friend_data(user.getId(),this.getId(),response,x);
            }
        }
    }
    @Override
    public String toString() { return toJSON().toString(); }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put(KEY_USERNAME, username);
        obj.put(KEY_EMAIL, email);
        obj.put(KEY_ID,id);

        return obj;
    }

    @Override
    public boolean equals(Object object){
        boolean isEqual= false;
        if (object != null && object instanceof User)
        {
            isEqual = (this.username.equals(((User) object).username));
        }
        return isEqual;
    }

    public User fromJSON(JSONObject jsonObj)  {
        final Gson gson = new Gson();
        if(jsonObj!=null)
            return gson.fromJson(jsonObj.toString(),User.class);
        else return null;
    }

    public static void main(String[] args) throws SQLException, ParseException {
        User user = new User(1);
        User user1  = new User(3);
        System.out.println(user.get_Data_Message(user1));
    }

}
