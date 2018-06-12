package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import modell.Country;
import modell.User;
import play.libs.Json;
import play.mvc.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Sarah on 06.03.2017.
 */
public class MapApplication extends Controller{




    public Country getCountry(String flag) throws SQLException{
        String [] countries = new String [6];
        int id = 0;
        Connection con = controllers.Application.db.getConnection();
        PreparedStatement statement;
        ResultSet rs;
        String query = "";

        //getName
        query = "SELECT * FROM Country WHERE flag = \"" + flag + "\";";
        statement = con.prepareStatement(query);
        rs = statement.executeQuery();
        while (rs.next()) {
            countries[0] = rs.getString("name");
            countries[1] = rs.getString("capital");
            countries[2] = rs.getString("language");
            countries[3] = rs.getString("continent");
            countries[4] = rs.getString("sight");
            countries[5] = rs.getString("currency");
            id = rs.getInt("idCountry");
        }
        con.close();
        Country country = new Country(id, flag,countries[0],countries[1],countries[2],countries[3],countries[4],countries[5]);
        return country;
    }





}
