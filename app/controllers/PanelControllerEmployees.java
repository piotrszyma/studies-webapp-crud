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
public class PanelControllerEmployees extends Controller implements PanelControllerInterface {

    @Inject
    private PanelControllerEmployeesDB panelControllerEmployeesDB;

    @Inject
    private FormFactory formFactory;

    private static final int panelPermissionLevel = 2;


    @Override
    public Result delete(String EmployeeID) {
        if (EmployeeID.equals("null")) {
            return redirect(routes.PanelControllerEmployees.show("No EmployeeID specified"));
        }
        try {
            panelControllerEmployeesDB.delete(EmployeeID);
        } catch (SQLException e) {
            try {
                panelControllerEmployeesDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerEmployees.show("This EmployeeID is busy"));
        }

        return redirect(routes.PanelControllerEmployees.show("Employee fired"));
    }

    @Override
    public Result modify() {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        DynamicForm requestForm = formFactory.form().bindFromRequest();
        Map<String, String> employeeProperties = requestForm.data();

        try {
            panelControllerEmployeesDB.modify(employeeProperties);
        } catch (SQLException e) {
            try {
                panelControllerEmployeesDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerEmployees.show("Wrong data provided for employee form"));
        }
        return redirect(routes.PanelControllerEmployees.show("Success!"));
    }

    @Override
    public Result show(String error) {
        if (session().get("user") == null) return redirect(routes.IndexController.index());
        return ok(views.html.panel.employees.employees.render(session().get("user"), error));
    }

    @Override
    public Result get(String employeeID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        try {
            return panelControllerEmployeesDB.get(employeeID);
        } catch (SQLException e) {
            try {
                panelControllerEmployeesDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return ok(e.getMessage());
        }
    }

    @Override
    public Result edit(String employeeID) {
        return ok(views.html.panel.employees.edit.render(session().get("user"), employeeID));
    }


}
