package provider;

import Entity.Message;
import Entity.User;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class ApiCrud {

    public ApiCrud(){
        System.out.println( "connection base de donnée" );
    }

    public static boolean verification_data_user(String username, String password) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        try{

            String query = password == null ? "select * from " + Environment.userPath + " where " + Environment.USERNAMEDB + "='" + username + "'" : "select * from " + Environment.userPath + " where " + Environment.USERNAMEDB + "='" + username + "' AND " + Environment.PASSWORDUSERDB + "='" + password + "'";
            ResultSet rs = con.createStatement().executeQuery(query);

            if(rs.next()){
                rs.close();
                return true;
            }
            rs.close();
            return false;
    }catch (Exception e){
        System.out.println(e);
    }finally {
        System.out.println("close connection");
        db.close_Connection();
        con.close();
    }

        return false;
}

    public static List<Message> get_message(User user1, User user2) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        List<Message> messages = new ArrayList<>();
        try{

            String query = "select message.userfrom,message.userto,message.message,message.dateenvoi" +
                    " from message where " +
                    "(message.userfrom=" + user1.getId() +
                    " and message.userto=" + user2.getId() + ") or" +
                    "(message.userfrom=" + user2.getId() +
                    " and message.userto=" + user1.getId() + ")" +
                    " order by message.dateenvoi asc";

            ResultSet rs = con.createStatement().executeQuery(query);
            while(rs.next()){
                User userfrom = new User(rs.getInt("userfrom"));
                User userto = new User(rs.getInt("userto"));
                Message message = new Message(userfrom.get_User_By_Id(),userto.get_User_By_Id(),rs.getString("message"));
                messages.add(message);
            }
            rs.close();
            return messages;

        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
        return null;
    }

    public static List<Integer> get_Friend_Request(int to) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        List<Integer> request = new ArrayList<>();
        try{

            String query = "select " + Environment.USERSIDINV + " from " + Environment.invitedPath +" where status='"
                    + Environment.LOADING_INV +"' and " + Environment.USERIDINV + " in (" + to + ")";

            ResultSet rs = con.createStatement().executeQuery(query);
            while(rs.next()){
                request.add(rs.getInt("fromuser"));
            }
            rs.close();
            return request;

        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
        return null;
    }

    public static boolean add_message(int from, int to, String message) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();

        try{
            Calendar calendar = Calendar.getInstance();
            java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
            String query = "insert into message (" + Environment.USERFROMMESDB + "," + Environment.USERTOMESDB + ","
                + Environment.MESDB + ", " + Environment.MESDATEDB +") values (? , ? , ? , ?)";
            PreparedStatement post = con.prepareStatement(query);
            post.setInt(1,from);
            post.setInt(2,to);
            post.setString(3,message);
            post.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            post.executeUpdate();
            post.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
    }

    public static boolean add_friend(int from , int to) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();

        try{
            String query = "insert into " + Environment.invitedPath + "(fromuser,touser,status) values (" + from
                    + "," + to +",'" + Environment.LOADING_INV + "')";
            PreparedStatement post = con.prepareStatement(query);
            post.executeUpdate();
            post.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
    }

    public static String[] get_user_by_id(int id) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        String[] data = new String[8];
        try{

            String query = "select * from " + Environment.userPath + " where " + Environment.IDUSERDB + "='" + id + "'";
            ResultSet rs = con.createStatement().executeQuery(query);

            if(rs.next()){
                data[0] = rs.getString("id");
                data[1] = rs.getString("username");
                data[2] = rs.getString("password");
                data[3] = rs.getString("email");
                data[4] = rs.getString("nom");
                data[5] = rs.getString("sexe");
                data[6] = rs.getString("photo");
                data[7] = rs.getString("birth");

                rs.close();
                db.close_Connection();
                con.close();

                return data;
            }
            else{
                rs.close();
                return null;
            }

        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }

        return null;
    }

    public static String[] get_data_user(String username, String password) throws SQLException {

        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        String[] data = new String[8];

        try{

            String query = password == null ? "select * from " + Environment.userPath + " where " + Environment.USERNAMEDB + "='" + username + "'" : "select * from " + Environment.userPath + " where " + Environment.USERNAMEDB + "='" + username + "' AND " + Environment.PASSWORDUSERDB + "='" + password + "'";
            ResultSet rs = con.createStatement().executeQuery(query);

            if(rs.next()){
                    data[0] = rs.getString("id");
                    data[1] = rs.getString("username");
                    data[2] = rs.getString("password");
                    data[3] = rs.getString("email");
                    data[4] = rs.getString("nom");
                    data[5] = rs.getString("sexe");
                    data[6] = rs.getString("photo");
                    data[7] = rs.getString("birth");

                    rs.close();
                return data;
            }
            else{
                rs.close();
                return null;
            }

        }catch (Exception e){
                System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }

        return null;
    }

    public static boolean update_user_data(User user) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        Statement stmt = null;
        try{

            String query = user.getBirth()==null ?
                    "UPDATE " + Environment.userPath + " SET "
                            + Environment.USERNAMEDB + "='" + user.getUsername() + "', "
                            + Environment.PASSWORDUSERDB + "='" + user.getPassword() + "', "
                            + Environment.EMAILUSERDB + "='" + user.getEmail() + "', "
                            + Environment.NOMUSERDB + "='" + user.getNom() + "', "
                            + Environment.GENDERUSERDB + "='" + user.isSexe() + "', "
                            + Environment.PHOTOUSERDB + "='" + user.getPhoto() + "', "
                            + Environment.BIRTHUSERDB + "= " + user.getBirth() + "  "
                            + "WHERE " + Environment.IDUSERDB + "=" + user.getId()
            :
                    "UPDATE " + Environment.userPath + " SET "
                            + Environment.USERNAMEDB + "='" + user.getUsername() + "', "
                            + Environment.PASSWORDUSERDB + "='" + user.getPassword() + "', "
                            + Environment.EMAILUSERDB + "='" + user.getEmail() + "', "
                            + Environment.NOMUSERDB + "='" + user.getNom() + "', "
                            + Environment.GENDERUSERDB + "='" + user.isSexe() + "', "
                            + Environment.PHOTOUSERDB + "='" + user.getPhoto() + "', "
                            + Environment.BIRTHUSERDB + "= '" + user.getBirth() + " ' "
                            + "WHERE " + Environment.IDUSERDB + "=" + user.getId();

            stmt = con.createStatement();
            stmt.executeUpdate(query);
            return true;

        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }

        return false;
    }

    public static String[] get_data_user(int id) throws SQLException {

        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        String[] data = new String[8];

        try{

            String query = "select * from " + Environment.userPath + " where " + Environment.IDUSERDB + "='" + id + "'";
            ResultSet rs = con.createStatement().executeQuery(query);

            if(rs.next()){
                data[0] = rs.getString("id");
                data[1] = rs.getString("username");
                data[2] = rs.getString("password");
                data[3] = rs.getString("email");
                data[4] = rs.getString("nom");
                data[5] = rs.getString("sexe");
                data[6] = rs.getString("photo");
                data[7] = rs.getString("birth");

                rs.close();
                return data;
            }
            else{
                rs.close();
                return null;
            }

        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }

        return null;
    }

    public boolean add_data_user(String username, String password , String email , boolean sexe) throws SQLException {

        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        try {

            String query = "INSERT INTO " + Environment.userPath + " (username,password,email,sexe) VALUES ('" + username + "', '" + password + "', '" + email + "', '" + sexe +"')";

            PreparedStatement post = con.prepareStatement(query);
            post.executeUpdate();
            post.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
        return false;
    }

    public int[] get_Inv_userID(int id){
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        int[] data = new int[200];
        String query = "select * from " + Environment.invitedPath + " where" + Environment.USERIDINV + "='" + id + "' AND status='" + Environment.LOADING_INV + "'";
        try {
            ResultSet rs = con.createStatement().executeQuery(query);
            int i=0;
            while(rs.next()){
                data[i]= Integer.parseInt(rs.getString(Environment.USERSIDINV));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<Integer> getUserFriend(int id) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        List<Integer> ints = new ArrayList<Integer>();
        try{
            String query = "select friend.user2 from " + Environment.friendPath + "," + Environment.userPath + " where " + Environment.userPath + ".id=" + Environment.friendPath +".user1 and " + Environment.userPath    + ".id in (" + id +")" ;
            ResultSet rs = con.createStatement().executeQuery(query);

            while(rs.next()){
                ints.add(rs.getInt(1));
            }
            rs.close();
            return ints;
        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
        return ints;
    }

    public static boolean response_friend_data(int id1,int id2,boolean response,int idInv) throws SQLException {
        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        Statement stmt1 = null;
        Statement stmt2= null;
        Statement stmt3 = null;
        try{

            if(response){
                String query = "insert into " + Environment.friendPath + "(user1,user2) values (" + id1 + "," + id2 + ")";
                String query2 = "insert into " + Environment.friendPath + "(user1,user2) values (" + id2 + "," + id1 + ")";
                String query3 = "update " + Environment.invitedPath + " set status='" + Environment.ACCEPTED_INV
                        + "' where id in (" + idInv + ")";
                stmt1 = con.createStatement();
                stmt2 = con.createStatement();
                stmt3 = con.createStatement();
                stmt1.executeUpdate(query);
                stmt2.executeUpdate(query2);
                stmt3.executeUpdate(query3);
                return true;
            }
            else{
                String query3 = "update " + Environment.invitedPath + " set status='" + Environment.REJECTED_INV
                        + "' where id in (" + idInv + ")";
                stmt3 = con.createStatement();
                stmt3.executeUpdate(query3);
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }

        return false;
    }
    public static int get_id_inv(int id1, int id2) throws SQLException {

        DB_connection db = new DB_connection();
        Connection con = db.get_Connection();
        int i = 0;
        try{

            String query = "select id from " + Environment.invitedPath + " where fromuser=" + id1 +" and touser=" + id2  +
                    " and status='loading'";
            ResultSet rs = con.createStatement().executeQuery(query);

            if(rs.next()){
                i = rs.getInt("id");
                rs.close();
                return i;
            }
            else{
                rs.close();
                return 0;
            }

        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("close connection");
            db.close_Connection();
            con.close();
        }
        return 0;
    }


        public static void main(String[] args) throws SQLException, ParseException {
            List list = new ArrayList();
            list.add(1);
            list.add("mdskq");
            list.add("mdskq");

        }

}
