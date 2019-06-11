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

/**
 * Created by thomp on 21.01.2017.
 */
public class PanelControllerVehicleDB extends Controller implements PanelControllerInterfaceDB {

    public Database db;

    private Gson gson;

    @Inject
    public PanelControllerVehicleDB(Database db) {
        this.db = db;
    }


    public void delete(String vehicleID) throws SQLException {
        String query = "call deleteVehicle(" + vehicleID + ")";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }


    public Result get(String queryVehicleID) throws SQLException {
        String query = "CALL getVehicles(NULL)";
        if (!queryVehicleID.equals("all")) query = "call getVehicles(" + queryVehicleID + ")";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> vehicleTable = new ArrayList<>();
        Map<String, String> vehicleTableRow = null;
        while (rs.next()) {
            vehicleTableRow = new HashMap<>();
            vehicleTableRow.put("vehicleID", rs.getString("id"));
            vehicleTableRow.put("vehicleName", rs.getString("vehicle"));
            vehicleTableRow.put("vehicleInsurance", rs.getString("insurance"));
            vehicleTableRow.put("vehicleName", rs.getString("vehicle"));
            vehicleTableRow.put("vehicleTechnicalCheck", rs.getString("tech_check"));
            vehicleTableRow.put("vehicleFuelType", rs.getString("fuel_type"));
            vehicleTableRow.put("vehicleBranchID", rs.getString("id_branch"));
            vehicleTableRow.put("vehicleBranch", rs.getString("branch"));
            vehicleTableRow.put("vehicleRegion", rs.getString("region"));
            vehicleTableRow.put("vehicleRegionID", rs.getString("id_region"));
            vehicleTable.add(vehicleTableRow);
        }
        gson = new Gson();
        String jsonVehicleTable;
        if (queryVehicleID.equals("all")) {
            jsonVehicleTable = gson.toJson(vehicleTable);
        } else {
            jsonVehicleTable = gson.toJson(vehicleTableRow);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonVehicleTable);
    }

    public void modify(Map<String, String> properties) throws SQLException {

        String query;
        if (properties.get("vehicleID").equals("0")) {
            query = "call addVehicle('" +
                    properties.get("vehicleName") + "', '" +
                    properties.get("vehicleInsurance") + "', '" +
                    properties.get("vehicleTechnicalCheck") + "', '" +
                    properties.get("vehicleFuelType") + "', " +
                    properties.get("vehicleBranchID") + ")";
            ;
        } else {
            query = "call editVehicle(" +
                    properties.get("vehicleID") + ", '" +
                    properties.get("vehicleName") + "', '" +
                    properties.get("vehicleInsurance") + "', '" +
                    properties.get("vehicleTechnicalCheck") + "', '" +
                    properties.get("vehicleFuelType") + "', " +
                    properties.get("vehicleBranchID") + ")";
        }


        System.out.println(query);
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }
}
