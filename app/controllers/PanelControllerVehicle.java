package controllers;

import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PanelControllerVehicle extends Controller implements PanelControllerInterface {

    @Inject
    private FormFactory formFactory;

    @Inject
    private PanelControllerVehicleDB panelControllerVehicleDB;

    private static final int panelPermissionLevel = 2;

    @Override
    public Result show(String error) {
        Result permissions = checkPermissions();
        if (permissions != null) return permissions;

        return ok(views.html.panel.vehicles.vehicles.render(session().get("user"), error));
    }

    @Override
    public Result edit(String vehicleID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        return ok(views.html.panel.vehicles.edit.render(session().get("user"), vehicleID));
    }

    @Override
    public Result get(String vehicleID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        try {
            return panelControllerVehicleDB.get(vehicleID);
        } catch (SQLException e) {
            try {
                panelControllerVehicleDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return ok(e.getMessage());
        }
    }

    @Override
    public Result modify() {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        DynamicForm requestForm = formFactory.form().bindFromRequest();
        Map<String, String> vehicleProperties = requestForm.data();

        try {
            panelControllerVehicleDB.modify(vehicleProperties);
        } catch (SQLException e) {
            try {
                panelControllerVehicleDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerVehicle.show("Invalid input data provided"));
        }
        return redirect(routes.PanelControllerVehicle.show("Vehicle properly edited"));
    }


    @Override
    public Result delete(String vehicleID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        if (vehicleID.equals("null")) {
            return redirect(routes.PanelControllerVehicle.show("No VehicleID specified"));
        }
        try {
            panelControllerVehicleDB.delete(vehicleID);
        } catch (SQLException e) {
            try {
                panelControllerVehicleDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerVehicle.show("This vehicle is occupied"));
        }

        return redirect(routes.PanelControllerVehicle.show("Vehicle deleted"));

    }

}
