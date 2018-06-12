package modell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Sarah on 06.03.2017.
 */
public class Country {


    private int idCountry;
    private String flag;
    private String name;
    private String capital;
    private String language;
    private String continent;
    private String sight;
    private String currency;



    public Country(int idCountry, String flag, String name, String capital, String language, String continent, String sight, String currency) {
        this.idCountry = idCountry;
        this.flag = flag;
        this.name = name;
        this.capital = capital;
        this.language = language;
        this.continent = continent;
        this.sight = sight;
        this.currency = currency;
    }

    public Country(String flag, String name, String capital, String language, String continent, String sight, String currency){
        this.flag = flag;
        this.name = name;
        this.capital = capital;
        this.language = language;
        this.continent = continent;
        this.sight = sight;
        this.currency = currency;
    }

    public int getIdCountry() throws SQLException{return idCountry;}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getSight() {
        return sight;
    }

    public void setSight(String sight) {
        this.sight = sight;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }





}
