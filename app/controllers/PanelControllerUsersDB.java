package controllers;

import com.google.gson.Gson;
import play.db.Database;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.mvc.Results.ok;

/**
 * Created by thomp on 28.01.2017.
 */
public class PanelControllerUsersDB extends Controller implements PanelControllerInterfaceDB {

    public Database db;

    private Gson gson;

    @Inject
    public PanelControllerUsersDB(Database db) {
        this.db = db;
    }

    public Result get(String userID) throws SQLException {
        String query = "CALL getUsers(NULL)";
        if (!userID.equals("all")) query = "call getUsers(" + userID + ")";

        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> usersTable = new ArrayList<>();
        Map<String, String> usersTableRow = null;

        while (rs.next()) {
            usersTableRow = new HashMap<>();
            usersTableRow.put("userID", rs.getString("id_user"));
            usersTableRow.put("userLogin", rs.getString("login"));
            usersTableRow.put("userEmployeeID", rs.getString("id_employee"));
            usersTableRow.put("userType", rs.getString("user_type"));
            usersTableRow.put("userFirstName", rs.getString("first_name"));
            usersTableRow.put("userLastName", rs.getString("last_name"));
            usersTable.add(usersTableRow);
        }
        gson = new Gson();
        String jsonUsersTable;
        if (userID.equals("all")) {
            jsonUsersTable = gson.toJson(usersTable);
        } else {
            jsonUsersTable = gson.toJson(usersTableRow);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonUsersTable);
    }

    public void modify(Map<String, String> userProperties) throws SQLException {

        System.out.println(userProperties);

        if (!userProperties.get("userPassword").equals("")) {
            MessageDigest m;
            try {
                m = MessageDigest.getInstance("MD5");
                m.update(userProperties.get("userPassword").getBytes(), 0, userProperties.get("userPassword").length());
                String hashed = new BigInteger(1, m.digest()).toString(16);
                userProperties.put("userPassword", "'" + hashed + "'");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            userProperties.put("userPassword", null);
        }

        if (userProperties.get("userID").equals("0")) {
            String query = "CALL addUser('" +
                    userProperties.get("userLogin") + "', " +
                    userProperties.get("userPassword") + ", " +
                    userProperties.get("userEmployeeID") + ", '" +
                    userProperties.get("userType") + "')";
            System.out.println(query);
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();

            preparedStatement.close();
            connection.close();
        } else {
            String query = "CALL editUser(" +
                    userProperties.get("userID") + ", '" +
                    userProperties.get("userLogin") + "', " +
                    userProperties.get("userPassword") + ", " +
                    userProperties.get("userEmployeeID") + ", '" +
                    userProperties.get("userType") + "')";
            System.out.println(query);
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();

            preparedStatement.close();
            connection.close();
        }
    }

    public void delete(String userID) throws SQLException {
        String query = "CALL removeUser(" + userID + ")";
        System.out.println(query);
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }

}
