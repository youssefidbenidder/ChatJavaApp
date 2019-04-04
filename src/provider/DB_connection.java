package provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_connection {

    public Connection con = null;

    public Connection get_Connection() {

        try {
            Class.forName("org.postgresql.Driver");
            try {
                con = DriverManager.getConnection(Environment.environement, Environment.user, Environment.password);
            } catch (Exception e1) {
                System.out.println("can't connect to db");
            }
        } catch (ClassNotFoundException e1) {
            System.out.println("erreur class not found");
        }

        return con;

    }

    public void close_Connection() throws SQLException {
        con.close();
    }

}
