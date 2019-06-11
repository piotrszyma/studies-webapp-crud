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
public class PanelControllerMiscDB extends Controller implements PanelControllerInterfaceMiscDB {

    private Database db;

    private Gson gson;

    @Inject
    public PanelControllerMiscDB(Database db) {
        this.db = db;
    }


    public Result getBranches() throws SQLException {
        String query = "CALL getBranches(NULL)";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> branchesTable = new ArrayList<>();
        Map<String, String> branchesRow = null;
        while (rs.next()) {
            branchesRow = new HashMap<>();
            branchesRow.put("branchID", rs.getString("id"));
            branchesRow.put("branchName", rs.getString("branch"));
            branchesRow.put("branchAddress", rs.getString("address"));
            branchesRow.put("branchRegion", rs.getString("region"));
            branchesRow.put("branchNumOfPackages", rs.getString("number_of_packages"));
            branchesTable.add(branchesRow);
        }

        gson = new Gson();
        String jsonVehicleTable = gson.toJson(branchesTable);
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonVehicleTable);
    }

    public Result getRegions() throws SQLException {
        String query = "CALL getRegions(NULL)";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> regionsTable = new ArrayList<>();
        Map<String, String> regionsRow = null;
        while (rs.next()) {
            regionsRow = new HashMap<>();
            regionsRow.put("regionID", rs.getString("id"));
            regionsRow.put("regionName", rs.getString("region"));
            regionsRow.put("regionNumOfPackages", rs.getString("count(jm_delivery.id)"));
            regionsTable.add(regionsRow);
        }
        gson = new Gson();
        String jsonVehicleTable = gson.toJson(regionsTable);
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonVehicleTable);
    }

}
