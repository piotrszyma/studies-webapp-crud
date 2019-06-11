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
import java.sql.*;
import java.util.*;

import play.mvc.Controller;

/**
 * Created by thomp on 28.01.2017.
 */
public class PanelControllerDeliveriesDB extends Controller implements PanelControllerInterfaceDB {

    public Database db;

    private Gson gson;

    @Inject
    public PanelControllerDeliveriesDB(Database db) {
        this.db = db;
    }

    @Override
    public Result get(String deliveryID) throws SQLException {
        String query = "CALL getDeliveries(NULL)";
        if (!deliveryID.equals("all")) query = "call getDeliveries(" + deliveryID + ")";


        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> deliveriesTable = new ArrayList<>();
        Map<String, String> deliveriesTableRow = null;

        while (rs.next()) {
            deliveriesTableRow = new HashMap<>();
            deliveriesTableRow.put("deliveryID", rs.getString("id"));
            deliveriesTableRow.put("deliveryPackageID", rs.getString("id_package"));
            deliveriesTableRow.put("deliveryStatus", rs.getString("status"));
            deliveriesTableRow.put("deliverySent", rs.getString("date"));

            deliveriesTableRow.put("senderID", rs.getString("id_sender"));
            deliveriesTableRow.put("senderFirstName", rs.getString("first_name_sender"));
            deliveriesTableRow.put("senderLastName", rs.getString("last_name_sender"));
            deliveriesTableRow.put("senderAddress", rs.getString("address_sender"));

            deliveriesTableRow.put("receiverID", rs.getString("id_receiver"));
            deliveriesTableRow.put("receiverFirstName", rs.getString("first_name_receiver"));
            deliveriesTableRow.put("receiverLastName", rs.getString("last_name_receiver"));
            deliveriesTableRow.put("receiverAddress", rs.getString("address_receiver"));

            deliveriesTableRow.put("employeeID", rs.getString("id_employee"));
            deliveriesTableRow.put("employeeFirstName", rs.getString("first_name"));
            deliveriesTableRow.put("employeeLastName", rs.getString("last_name"));
            deliveriesTable.add(deliveriesTableRow);
        }
        gson = new Gson();
        String jsonDeliveriesTable;
        if (deliveryID.equals("all")) {
            jsonDeliveriesTable = gson.toJson(deliveriesTable);
        } else {
            jsonDeliveriesTable = gson.toJson(deliveriesTableRow);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonDeliveriesTable);
    }

    public Result getByEmployee(String EmployeeID) throws SQLException {
        String query = "call getDeliveriesByEmployee(" + EmployeeID + ")";


        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> deliveriesTable = new ArrayList<>();
        Map<String, String> deliveriesTableRow = null;

        while (rs.next()) {
            deliveriesTableRow = new HashMap<>();
            deliveriesTableRow.put("deliveryID", rs.getString("id"));
            deliveriesTableRow.put("deliveryPackageID", rs.getString("id_package"));
            deliveriesTableRow.put("deliveryStatus", rs.getString("status"));
            deliveriesTableRow.put("deliverySent", rs.getString("date"));

            deliveriesTableRow.put("senderID", rs.getString("id_sender"));
            deliveriesTableRow.put("senderFirstName", rs.getString("first_name_sender"));
            deliveriesTableRow.put("senderLastName", rs.getString("last_name_sender"));
            deliveriesTableRow.put("senderAddress", rs.getString("address_sender"));

            deliveriesTableRow.put("receiverID", rs.getString("id_receiver"));
            deliveriesTableRow.put("receiverFirstName", rs.getString("first_name_receiver"));
            deliveriesTableRow.put("receiverLastName", rs.getString("last_name_receiver"));
            deliveriesTableRow.put("receiverAddress", rs.getString("address_receiver"));

            deliveriesTableRow.put("employeeID", rs.getString("id_employee"));
            deliveriesTableRow.put("employeeFirstName", rs.getString("first_name"));
            deliveriesTableRow.put("employeeLastName", rs.getString("last_name"));
            deliveriesTable.add(deliveriesTableRow);
        }
        gson = new Gson();
        String jsonDeliveriesTable;
        jsonDeliveriesTable = gson.toJson(deliveriesTable);
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonDeliveriesTable);
    }

    public void modify(String deliveryID, String courierID, String status) throws SQLException {
        String query = "CALL editDelivery(" + deliveryID + ", " + courierID + ", '" + status + "')";
        System.out.println(query);
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();

        preparedStatement.close();
        connection.close();
    }

    @Override
    public void delete(String deliveryID) throws SQLException {
        String query = "CALL deleteDelivery(" + deliveryID + ")";
        System.out.println(query);
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public void modify(Map<String, String> objectProperties) throws SQLException {

    }
}
