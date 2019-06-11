package controllers;

import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by thomp on 28.01.2017.
 */
public class PanelControllerStats extends Controller implements PanelControllerInterface {

    @Inject
    private PanelControllerStatsDB panelControllerStatsDB;

    @Inject
    private FormFactory formFactory;


    private static final int panelPermissionLevel = 2;


    @Override
    public Result show(String error) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;

        return ok(views.html.panel.stats.stats.render(session().get("user"), error));
    }

    @Override
    public Result get(String BranchID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;

        try {
            return panelControllerStatsDB.get(BranchID);
        } catch (SQLException e) {
            try {
                panelControllerStatsDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return ok(e.getMessage());
        }
    }

    @Override
    public Result edit(String BranchID) {
        return ok(views.html.panel.stats.edit.render(session().get("user"), BranchID));

    }

    @Override
    public Result modify() {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;

        DynamicForm requestForm = formFactory.form().bindFromRequest();
        Map<String, String> branchProperties = requestForm.data();
        System.out.println(branchProperties.toString());
        try {
            panelControllerStatsDB.modify(branchProperties);
        } catch (SQLException e) {
            try {
                panelControllerStatsDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerStats.show("Invalid input data provided"));
        }
        return redirect(routes.PanelControllerStats.show("Success"));
    }

    @Override
    public Result delete(String branchID) {

        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        if (branchID.equals("null")) {
            return redirect(routes.PanelControllerVehicle.show("No BranchID specified"));
        }
        try {
            panelControllerStatsDB.delete(branchID);
        } catch (SQLException e) {
            try {
                panelControllerStatsDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerVehicle.show("This branch is not empty"));
        }

        return redirect(routes.PanelControllerVehicle.show("Branch deleted"));
    }
}
