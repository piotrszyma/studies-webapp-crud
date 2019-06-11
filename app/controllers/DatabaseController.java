package controllers;

import com.google.gson.Gson;
import models.forms.SendPackage;
import play.db.Database;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomp on 21.01.2017.
 */
public class DatabaseController extends Controller {

    public Database db;

    @Inject
    public DatabaseController(Database db) {
        this.db = db;
    }

    boolean addDelivery(SendPackage sendPackage) throws SQLException {
        Statement stmt;
        Connection connection = db.getConnection();
        try {
            stmt = connection.createStatement();
            String query = "call addDelivery(" +
                    "'" + sendPackage.getSenderFirstName() +
                    "', '" + sendPackage.getSenderLastName() +
                    "', '" + sendPackage.getSenderRegion() +
                    "', '" + sendPackage.getSenderAddress() +
                    "', '" + sendPackage.getSenderPhone() +
                    "', '" + sendPackage.getSenderEmail() +
                    "', '" + sendPackage.getReceiverFirstName() +
                    "', '" + sendPackage.getReceiverLastName() +
                    "', '" + sendPackage.getReceiverRegion() +
                    "', '" + sendPackage.getReceiverAddress() +
                    "', '" + sendPackage.getReceiverPhone() +
                    "', '" + sendPackage.getReceiverEmail() +
                    "', '" + sendPackage.getPackageInsurance() +
                    "', '" + sendPackage.getPackageValue() +
                    "', '" + sendPackage.getPackageWeight() +
                    "')";
//            System.out.println(query);
            stmt.executeQuery(query);
            stmt.close();
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            //finally block used to close resources
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
//        System.out.println(stmt.getResultSet());
        return true;
    }
}
