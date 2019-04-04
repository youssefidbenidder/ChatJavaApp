package Entity;

import java.sql.SQLException;

public class Friend {

    private int id;
    private int user1;
    private int user2;

    public Friend(int id, int user1, int user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public Friend(int user1, int user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Friend() {
        System.out.println("Creation Invitation");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    public static void main(String[] args) throws SQLException {

    }

}
