package controllers;

import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.Map;

import static play.mvc.Controller.session;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by thomp on 28.01.2017.
 */
public class PanelControllerUsers extends Controller implements PanelControllerInterface {


    @Inject
    private FormFactory formFactory;

    @Inject
    private PanelControllerUsersDB panelControllerUsersDB;

    private static final int panelPermissionLevel = 3;


    @Override
    public Result show(String error) {
        if (session().get("user") == null) return redirect(routes.IndexController.index());
        return ok(views.html.panel.users.users.render(session().get("user"), error));
    }

    @Override
    public Result get(String userID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        try {
            return panelControllerUsersDB.get(userID);
        } catch (SQLException e) {
            try {
                panelControllerUsersDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return ok(e.getMessage());
        }
    }

    @Override
    public Result edit(String userID) {
        return ok(views.html.panel.users.edit.render(session().get("user"), userID));
    }

    @Override
    public Result modify() {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        DynamicForm requestForm = formFactory.form().bindFromRequest();
        Map<String, String> userProperties = requestForm.data();
        try {
            panelControllerUsersDB.modify(userProperties);
        } catch (SQLException e) {
            try {
                panelControllerUsersDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerUsers.show(e.getMessage()));

        }
        return redirect(routes.PanelControllerUsers.show("Success"));
    }

    @Override
    public Result delete(String userID) {
        if (userID.equals("null")) return ok("Wrong parameters");
        try {
            panelControllerUsersDB.delete(userID);
        } catch (SQLException e) {
            try {
                panelControllerUsersDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerUsers.show("Cannot delete user"));
        }
        return redirect(routes.PanelControllerUsers.show("User deleted"));
    }


}
