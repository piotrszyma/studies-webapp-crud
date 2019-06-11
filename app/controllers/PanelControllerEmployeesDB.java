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

public class PanelControllerEmployeesDB extends Controller implements PanelControllerInterfaceDB {

    public Database db;

    private Gson gson;

    @Inject
    public PanelControllerEmployeesDB(Database db) {
        this.db = db;
    }

    @Override
    public Result get(String employeeID) throws SQLException {
        String query = "CALL getEmployees(NULL)";
        if (!employeeID.equals("all")) query = "call getEmployees(" + employeeID + ")";


        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> employeesTable = new ArrayList<>();
        Map<String, String> employeesTableRow = null;

        while (rs.next()) {
            employeesTableRow = new HashMap<>();
            employeesTableRow.put("employeeID", rs.getString("id_employee"));
            employeesTableRow.put("employeeFirstName", rs.getString("first_name"));
            employeesTableRow.put("employeeLastName", rs.getString("last_name"));
            employeesTableRow.put("employeeSSN", rs.getString("ssn"));
            employeesTableRow.put("employeePhone", rs.getString("phone"));
            employeesTableRow.put("employeeAddress", rs.getString("address"));
            employeesTableRow.put("employeeRegionID", rs.getString("id_region"));
            employeesTableRow.put("employeeRegion", rs.getString("region"));
            employeesTableRow.put("employeeVehicleID", rs.getString("id_vehicle"));
            employeesTableRow.put("employeeVehicle", rs.getString("vehicle"));
            employeesTableRow.put("employeeBranchID", rs.getString("id_branch"));
            employeesTableRow.put("employeeBranch", rs.getString("branch"));
            employeesTableRow.put("employeePackages", rs.getString("number_of_packages"));
            employeesTable.add(employeesTableRow);
        }
        gson = new Gson();
        String jsonEmployeesTable;
        if (employeeID.equals("all")) {
            jsonEmployeesTable = gson.toJson(employeesTable);
        } else {
            jsonEmployeesTable = gson.toJson(employeesTableRow);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonEmployeesTable);
    }

    @Override
    public void modify(Map<String, String> properties) throws SQLException {


        if (properties.get("employeeVehicle").equals("-1")) {
            properties.put("employeeVehicle", "null");
        }

        System.out.println(properties);

        String query;
        if (properties.get("employeeID").equals("0")) {
            query = "call addEmployee('" +
                    properties.get("employeeFirstName") + "', '" +
                    properties.get("employeeLastName") + "', '" +
                    properties.get("employeeSSN") + "', '" +
                    properties.get("employeePhone") + "', '" +
                    properties.get("employeeAddress") + "', " +
                    properties.get("employeeBranch") + ", " +
                    properties.get("employeeVehicle") + ")";
            System.out.println(query);
            ;
        } else {
            query = "call editEmployee(" +
                    properties.get("employeeID") + ", '" +
                    properties.get("employeeFirstName") + "', '" +
                    properties.get("employeeLastName") + "', '" +
                    properties.get("employeeSSN") + "', '" +
                    properties.get("employeePhone") + "', '" +
                    properties.get("employeeAddress") + "', " +
                    properties.get("employeeVehicle") + ", " +
                    properties.get("employeeBranch") + ")"; //ID
        }

        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public void delete(String employeeID) throws SQLException {
        String query = "call dumpEmployee(" + employeeID + ")";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }
}
