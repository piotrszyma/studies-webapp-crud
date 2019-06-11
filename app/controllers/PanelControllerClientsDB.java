package controllers;

import com.google.gson.Gson;
import play.db.Database;
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
public class PanelControllerClientsDB implements PanelControllerInterfaceDB {

    public Database db;

    private Gson gson;

    @Inject
    public PanelControllerClientsDB(Database db) {
        this.db = db;
    }

    public Result get(String ClientID) throws SQLException {
        String query = "CALL getClients(NULL)";
        if (!ClientID.equals("all")) query = "call getClients(" + ClientID + ")";

        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Map<String, String>> clientsTable = new ArrayList<>();
        Map<String, String> clientsTableRow = null;

        while (rs.next()) {
            clientsTableRow = new HashMap<>();
            clientsTableRow.put("clientID", rs.getString("id"));
            clientsTableRow.put("clientFirstName", rs.getString("first_name"));
            clientsTableRow.put("clientLastName", rs.getString("last_name"));
            clientsTableRow.put("clientAddress", rs.getString("address"));
            clientsTableRow.put("clientRegion", rs.getString("region"));
            clientsTableRow.put("clientRegionID", rs.getString("id_region"));
            clientsTableRow.put("clientPhone", rs.getString("phone"));
            clientsTable.add(clientsTableRow);
        }
        gson = new Gson();
        String jsonClientTable;
        if (ClientID.equals("all")) {
            jsonClientTable = gson.toJson(clientsTable);
        } else {
            jsonClientTable = gson.toJson(clientsTableRow);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return ok(jsonClientTable);
    }


    public void delete(String clientID) throws SQLException {
        String query = "call removeClient(" + clientID + ")";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }

    public void modify(Map<String, String> properties) throws SQLException {
        System.out.println(properties);
        String query = "call editClient(" +
                properties.get("clientID") + ", '" +
                properties.get("clientFirstName") + "', '" +
                properties.get("clientLastName") + "', '" +
                properties.get("clientAddress") + "', '" +
                properties.get("clientRegionID") + "')";
        System.out.println(query);
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();
    }
}
