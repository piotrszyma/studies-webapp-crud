package controllers;

import play.db.Database;
import play.mvc.Controller;

import javax.inject.Inject;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginControllerDB extends Controller {

    private Database db;


    private static final Map<String, String> permissions = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put("CEO", "3");
                put("manager", "2");
                put("worker", "1");
            }});


    @Inject
    public LoginControllerDB(Database db) {
        this.db = db;
    }


    boolean checkUserdata(String name, String password) throws SQLException, NoSuchAlgorithmException {
        Connection connection = db.getConnection();
        Statement stmt;
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(password.getBytes(), 0, password.length());
        String hashed = new BigInteger(1, m.digest()).toString(16);
        String query = "select jm_users.user_type, jm_users.id_employee from jm_users where login = '" + name + "' and password = '" + hashed + "'";
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        boolean result = rs.isBeforeFirst();
        if (result) {
            rs.next();
            session("user", rs.getString("user_type"));
            session("employeeID", rs.getString("id_employee"));
            session("permissionsID", permissions.getOrDefault(rs.getString("user_type"), "1"));
        }
        rs.close();
        stmt.close();
        connection.close();
        return result;
    }
}
