package controllers;

import models.forms.SendPackage;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.sql.SQLException;

/**
 * Created by thomp on 18.01.2017.
 */
public class ShipmentController extends Controller {

    @Inject
    FormFactory formFactory;

    @Inject
    private DatabaseController databaseController;

    public Result index() {
        if (session("user") != null) {
            return redirect(controllers.routes.PanelController.index(null));
        } else {
            Form<SendPackage> packageForm = formFactory.form(SendPackage.class);
            return ok(views.html.basic.shipment.render(packageForm, null));
        }
    }

    public Result submit() throws SQLException {
        Form<SendPackage> formData = formFactory.form(SendPackage.class).bindFromRequest();
        if (formData.hasErrors()) {
            return ok(views.html.basic.shipment.render(formData, "Wrong data provided!"));
        }
        SendPackage sendPackage = formData.get();
        if (!databaseController.addDelivery(sendPackage)) {
            return ok(views.html.basic.shipment.render(formData, "Wrong statement provided!"));
        } else {
            return ok(views.html.basic.shipment.render(formData, "Successfully sent!"));
        }

    }

}
