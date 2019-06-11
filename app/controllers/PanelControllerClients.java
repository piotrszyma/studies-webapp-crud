package controllers;

import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;
import java.util.Map;

public class PanelControllerClients extends Controller implements PanelControllerInterface {

    @Inject
    private
    FormFactory formFactory;
    @Inject
    private
    PanelControllerClientsDB panelControllerClientsDB;

    private static final int panelPermissionLevel = 3;


    @Override
    public Result show(String error) {
        if (session().get("user") == null) return redirect(routes.IndexController.index());
        return ok(views.html.panel.clients.clients.render(session().get("user"), error));
    }

    @Override
    public Result get(String ClientID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        try {
            return panelControllerClientsDB.get(ClientID);
        } catch (SQLException e) {
            try {
                panelControllerClientsDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return ok(e.getMessage());
        }
    }

    @Override
    public Result edit(String clientID) {
        return ok(views.html.panel.clients.edit.render(session().get("user"), clientID));
    }

    @Override
    public Result modify() {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        DynamicForm requestForm = formFactory.form().bindFromRequest();
        Map<String, String> clientProperties = requestForm.data();

        try {
            panelControllerClientsDB.modify(clientProperties);
        } catch (SQLException e) {
            try {
                panelControllerClientsDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerClients.show(e.getMessage()));
        }
        return redirect(routes.PanelControllerClients.show("Client data properly edited"));
    }

    @Override
    public Result delete(String ClientID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;
        try {
            panelControllerClientsDB.delete(ClientID);
        } catch (SQLException e) {
            try {
                panelControllerClientsDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            System.out.println("Bleble");
            return redirect(routes.PanelControllerClients.show("Client got a delivery, cannot delete"));
        }
        return redirect(routes.PanelControllerClients.show("Client removed from company database"));
    }

}
