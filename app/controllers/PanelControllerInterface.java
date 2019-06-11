package controllers;

import play.mvc.Result;

import static play.mvc.Controller.session;
import static play.mvc.Results.redirect;

/**
 * Created by thomp on 29.01.2017.
 */
public interface PanelControllerInterface {

    Result show(String objectID);

    Result get(String objectID);

    Result edit(String objectID);

    Result modify();

    Result delete(String objectID);

    default Result checkPermissions() {
        if (session().get("user") == null) {
            return redirect(routes.IndexController.index());
        }
        return null;
    }

    default Result checkPermissions(int permissionLevel) {
        if (session().get("user") == null) {
            return redirect(routes.IndexController.index());
        }

        if (Integer.parseInt(session().get("permissionsID")) < permissionLevel) {
            return redirect(routes.PanelController.index("No permission"));
        }
        return null;
    }
}
