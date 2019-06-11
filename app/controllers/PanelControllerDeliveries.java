package controllers;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.SQLException;

/**
 * Created by thomp on 28.01.2017.
 */
public class PanelControllerDeliveries extends Controller implements PanelControllerInterface {

    @Inject
    private PanelControllerDeliveriesDB panelControllerDeliveriesDB;

    private static final int panelPermissionLevel = 1;

    @Override
    public Result show(String error) {
        if (session().get("user") == null) return redirect(routes.IndexController.index());
        return ok(views.html.panel.deliveries.deliveries.render(session().get("user"), error));
    }

    public Result get(String deliveryID) {
        Result permissions = checkPermissions(panelPermissionLevel);
        if (permissions != null) return permissions;

        if (session().get("permissionsID").equals("1") && deliveryID.equals("all")) {
            try {
                return panelControllerDeliveriesDB.getByEmployee(session().get("employeeID"));
            } catch (SQLException e) {
                try {
                    panelControllerDeliveriesDB.db.getConnection().close();
                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
                return redirect(routes.PanelControllerDeliveries.show(e.getMessage()));
            }
        } else {
            try {
                return panelControllerDeliveriesDB.get(deliveryID);
            } catch (SQLException e) {
                return redirect(routes.PanelControllerDeliveries.show(e.getMessage()));

            }
        }

    }

    @Override
    public Result edit(String deliveryID) {
        return ok(views.html.panel.deliveries.edit.render(session().get("user"), deliveryID));
    }

    @Override
    public Result modify() {
        return null;
    }

    public Result modify(String deliveryID, String CourierID, String statusName) {
        System.out.println(deliveryID);
        try {
            panelControllerDeliveriesDB.modify(deliveryID, CourierID, statusName);
        } catch (SQLException e) {
            try {
                panelControllerDeliveriesDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerDeliveries.show(e.getMessage()));

        }
        return redirect(routes.PanelControllerDeliveries.show("Status updated"));
    }

    @Override
    public Result delete(String deliveryID) {
        if (deliveryID.equals("null")) return ok("Wrong parameters");
        try {
            panelControllerDeliveriesDB.delete(deliveryID);
        } catch (SQLException e) {
            try {
                panelControllerDeliveriesDB.db.getConnection().close();
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
            return redirect(routes.PanelControllerDeliveries.show("Can't delete"));
        }
        return redirect(routes.PanelControllerDeliveries.show("Delivery deleted"));
    }


}
