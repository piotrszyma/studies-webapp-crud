package controllers;

import javafx.scene.layout.Pane;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by thomp on 18.01.2017.
 */
public class PanelController extends Controller {

//    @Inject
//    DatabaseController databaseController;

    //TODO: Check cookies/session for user for all methods beneath

    public Result index(String error) {
        if (session().get("user") == null) return redirect(routes.IndexController.index());
        return ok(views.html.panel.panel.render(session().get("user"), error));
    }
}
