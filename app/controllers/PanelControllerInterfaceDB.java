package controllers;

import play.mvc.Result;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by thomp on 29.01.2017.
 */
public interface PanelControllerInterfaceDB {
    Result get(String objectID) throws SQLException;

    void delete(String objectID) throws SQLException;

    void modify(Map<String, String> objectProperties) throws SQLException;
}
