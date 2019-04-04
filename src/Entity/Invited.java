package Entity;

import provider.ApiCrud;

import java.sql.SQLException;

public class Invited {
    private int id, fromuser, touser;
    private String status;

    public Invited(int id, int fromuser, int touser, String status) {
        this.id = id;
        this.fromuser = fromuser;
        this.touser = touser;
        this.status = status;
    }

    public Invited(int fromuser, int touser, String status) {
        this.fromuser = fromuser;
        this.touser = touser;
        this.status = status;
    }

    public Invited(int fromuser, int touser) {
        this.fromuser = fromuser;
        this.touser = touser;
    }

    public int get_inv_id() throws SQLException {
        return ApiCrud.get_id_inv(this.fromuser, this.touser);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromuser() {
        return fromuser;
    }

    public void setFromuser(int fromuser) {
        this.fromuser = fromuser;
    }

    public int getTouser() {
        return touser;
    }

    public void setTouser(int touser) {
        this.touser = touser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}