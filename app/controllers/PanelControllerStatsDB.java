package controllers;

import com.google.gson.Gson;
import play.db.Database;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
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
public class PanelControllerStatsDB extends Controller implements PanelControllerInterfaceDB {

    public Database db;

    private Gson gson;

    @Inject
    public PanelControllerStatsDB(Database db) {
        this.db = db;
    }

    public Result get(String queryBranchID) throws SQLException {
        String query = "CALL getBranches(NULL)";
        if (!queryBranchID.equals("all")) query = "call getBranches(" + queryBranchID + ")";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> branchesTable = new ArrayList<>();
        Map<String, String> branchesTableRow = null;

        System.out.println(query);

        while (rs.next()) {
            branchesTableRow = new HashMap<>();
            branchesTableRow.put("branchID", rs.getString("id"));
            branchesTableRow.put("branchName", rs.getString("branch"));
            branchesTableRow.put("branchAddress", rs.getString("address"));
            branchesTableRow.put("branchRegion", rs.getString("region"));
            branchesTableRow.put("branchRegionID", rs.getString("id_region"));
            branchesTableRow.put("branchNumOfPackages", rs.getString("number_of_packages"));
            branchesTable.add(branchesTableRow);
        }
        gson = new Gson();
        String jsonBranchesTable;

        if (queryBranchID.equals("all")) {
            jsonBranchesTable = gson.toJson(branchesTable);
        } else {
            jsonBranchesTable = gson.toJson(branchesTableRow);
        }

        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonBranchesTable);
    }

    @Override
    public void delete(String branchID) throws SQLException {
        String query = "call removeBranch(" + branchID + ")";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }

    public void modify(Map<String, String> properties) throws SQLException {

        String query;
        if (properties.get("branchID").equals("0")) {
            return;
//            query = "call addBranch('" +
//                    properties.get("branchName") + "', '" +
//                    properties.get("branchAddress") + "', " +
//                    properties.get("branchRegion") + ")";

        } else {
            query = "call editBranch(" +
                    properties.get("branchID") + ", '" +
                    properties.get("branchName") + "', '" +
                    properties.get("branchAddress") + "', " +
                    properties.get("branchRegion") + ")";
        }

        System.out.println(query);
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
        System.out.println("executed");

    }

}
