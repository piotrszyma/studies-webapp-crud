package models.forms;

import com.avaje.ebean.Model;
import controllers.DatabaseController;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.db.jpa.JPA;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.crypto.Data;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomp on 19.01.2017.
 */
public class User extends Model {


    @Constraints.Required
    protected String name;
    protected String password;

    public List<ValidationError> validate() throws SQLException, NoSuchAlgorithmException {
        List<ValidationError> errors = new ArrayList<ValidationError>();

        if (name == null || name.length() == 0) {
            errors.add(new ValidationError("name", "Name is empty"));
        }

        if (password == null || password.length() == 0) {
            errors.add(new ValidationError("password", "Password is empty"));
        }
        return errors.isEmpty() ? null : errors;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
