package controllers;

import models.forms.User;
import play.api.data.validation.ValidationError;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.crypto.Data;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static play.data.Form.form;

public class LoginController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private LoginControllerDB loginControllerDB;


    public Result index() {
        Form<User> userForm = formFactory.form(User.class);

        if (session().get("user") != null /*TODO: if session is not outdated */) {
            session().clear();
        }
        //TODO: check user type in database (and replace with "none")
        return ok(views.html.basic.login.render(userForm, null));
    }

    public Result submit() throws SQLException, NoSuchAlgorithmException {
        Form<User> formData = formFactory.form(User.class).bindFromRequest();
        //TODO: validation check with DB in User.class

        if (formData.hasErrors()) {
            return ok(views.html.basic.login.render(formData, "Fields cannot be empty!"));
        }
        User userData = formData.get();

        if (!loginControllerDB.checkUserdata(userData.getName(), userData.getPassword())) {
            return ok(views.html.basic.login.render(formData, "Wrong username and password combination!"));
        }
        return redirect(controllers.routes.PanelController.index(null));

    }

    public Result logout() {
        //Clear cookies
        session().clear();
        return redirect(controllers.routes.IndexController.index());

    }

}

