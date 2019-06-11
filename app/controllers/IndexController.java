package controllers;

import controllers.routes;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by thomp on 18.01.2017.
 */
public class IndexController extends Controller {
    public Result index() {
        if (session("user") != null) {
            return redirect(controllers.routes.PanelController.index(null));
        } else {
            return ok(views.html.basic.index.render());

        }
    }
}
