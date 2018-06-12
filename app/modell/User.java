package modell;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sarah on 06.03.2017.
 */
public class User {

    private String username;
    private String password;
    private List<Country> map = new ArrayList();
    private int idUser;
    private String email;
    private int level;
    private QRMap qrmap = new QRMap();

    //*****************************************Getter, Setter & Konstruktoren*********************************************
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getEmail(){
        return email;
    }

    public List<Country> getMap() {
        return map;
    }
    public void setMap(List<Country> map) {
        this.map = map;
    }

    public String getUsername(){
        return username;
    }
    public QRMap getQrmap(){
        return qrmap;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password){
        this.username=username;
        this.password =password;
    }


    //******************************************************QR MAP *************************************************************

    /**
     * speicher in QRMAP Würzburg mit 1(eingescannt)
     * @throws SQLException
     */
    public void addQRWürzburg() throws SQLException{
        if(qrmap.getWuerzburg() == 0){
            qrmap.setWuerzburg(1);
            Connection con = controllers.Application.db.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO QRMap (`User_idUser`,`Würzburg`) VALUES (?,?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, idUser);
            statement.setInt(2, qrmap.getWuerzburg());
            statement.executeUpdate();
            con.close();
        }


    }

    /**
     * lädt QrcodeMapstand des Users aus DB
     * @throws SQLException
     */
    public void getQRFromDB()throws SQLException {
        Connection con = controllers.Application.db.getConnection();
        Statement statement;
        ResultSet rs;
        String query = "SELECT * FROM QRMap WHERE User_idUser = \"" + idUser + "\";";
        statement = con.createStatement();
        rs = statement.executeQuery(query);
        while (rs.next()) {
            qrmap.setWuerzburg(rs.getInt("Würzburg"));

        }
        con.close();


    }





//************************************************MAP**********************************************************************

    /**
     * Lädt alle gesammelten Länder des entsprechenden User aus der DB un gibt sie in einer Liste zurück
     * @throws SQLException
     */
    public void getCountriesFromDB() throws SQLException{

        Connection con = controllers.Application.db.getConnection();
        Statement statement;
        ResultSet rs;
        String query = "";
        query = "SELECT * FROM User_has_Country WHERE User_idUser = \"" + idUser + "\";";
        statement = con.createStatement();
        rs = statement.executeQuery(query);
        while (rs.next()) {
            int idCountry = rs.getInt("Country_idCountry");
            String sql = "SELECT * FROM Country WHERE idCountry =" + idCountry;
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            result.next();
            String [] countries = new String[7];
            countries[0] = result.getString("name");
            countries[1] = result.getString("capital");
            countries[2] = result.getString("language");
            countries[3] = result.getString("continent");
            countries[4] = result.getString("sight");
            countries[5] = result.getString("currency");
            countries[6] = result.getString("flag");
            int id = result.getInt("idCountry");
            Country country = new Country(id,countries[6], countries[0],countries[1],countries[2],countries[3],countries[4],countries[5]);
                map.add(country);
        }
        con.close();

    }


    /**
     * Fügt dem User das gesammelte Land hinzu
     * @param country
     * @throws SQLException
     */
    public void addCountry (Country country) throws SQLException{
        for (int i = 0; i<map.size();i++){
            System.out.println("Map: " + map.get(i).getName());
        }

       if(!map.contains(country)){
           map.add(country);
           Connection con = controllers.Application.db.getConnection();
           PreparedStatement statement = con.prepareStatement(
                   "INSERT INTO User_has_Country (`User_idUser`, `Country_idCountry`) VALUES (?,?);",
                   Statement.RETURN_GENERATED_KEYS);
           statement.setInt(1, idUser);
           statement.setInt(2, country.getIdCountry());
           statement.executeUpdate();
           con.close();
       }

    }

//***************************************************USER***************************************************************

    /**
     * Creates a new user, inserts it into the User table, and returns a corresponding (proxy) User instance.
     */
    public static User create(String email, String username, String password) throws SQLException {
        Connection con = controllers.Application.db.getConnection();
        PreparedStatement statement = con.prepareStatement(
                "INSERT INTO User (`email`, `username`, `password`) VALUES (?,?,?);",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, email);
        statement.setString(2, username);
        statement.setString(3, password);
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            User res = new User(generatedKeys.getInt(1), email, username, password);
            con.close();
            return res;
        } else {
            con.close();
            throw new SQLException("Creating User failed, no ID obtained.");
        }
    }

    /**
     * Creates a new User instance with the given properties.
     */
    private User(int idUser, String email, String username, String password) {
        this.idUser = idUser;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns all users from the Users table.
     */
    public static List<User> fromDB() throws SQLException {
        return fromDBWhere (null);
    }

    /**
     * Updates the row corresponding to this.userId with the properties of this user instance.
     */
    public void update() throws SQLException {
        Connection con = controllers.Application.db.getConnection();
        PreparedStatement statement = con.prepareStatement(
                "UPDATE user SET `email`=?, `username`=?, `password`=? WHERE id=" + idUser + ";");
        statement.setString(1, email);
        statement.setString(2, username);
        statement.setString(3, password);
        statement.executeUpdate();
        con.close();
    }

    /**
     * Deletes the row corresponding to this.userId.
     */
    public void delete() throws SQLException {
        Connection con = controllers.Application.db.getConnection();
        PreparedStatement statement = con.prepareStatement(
                "DELETE FROM User WHERE idUser=" + idUser + ";");
        statement.executeUpdate();
        con.close();
    }

    /**
     * Queries the User with idUser from the database and create a corresponding (proxy) User instance.
     */
    public static User fromDBWithID (int idUser) throws SQLException {
        return fromDBWhere("idUser=" + idUser).get(0);
    }

    /**
     * Returns all users from the Users table, that have the given email.
     */
    public static List<User> fromDBWithEmail(String email) throws SQLException {
        return fromDBWhere("email='" + email + "'");
    }

    /**
     * Returns all users from the Users table, that have the given name.
     */
    public static List<User> fromDBWithName(String username) throws SQLException {
        return fromDBWhere("username='" + username + "'");
    }

    /**
     * Returns all users from the Users table, that have the given name.
     */
    public static List<User> fromDBWithCredentials(String username, String password) throws SQLException {
        return fromDBWhere("username='" + username + "' AND password='" + password + "'" );
    }


    /**
     * Returns all users that meet a given condition.
     * @param where An SQL where condition for User.
     * @return All database entries that meet the condition.
     * @throws SQLException
     */
    private static List<User> fromDBWhere(String where) throws SQLException {
        Connection con = controllers.Application.db.getConnection();
        String query = "SELECT * FROM User";

        if(where == null) query += ";";
        else query += " WHERE " + where + ";";

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(query);
        LinkedList<User> res = new LinkedList<User>();

        while (rs.next()) {
            res.add(new User(rs.getInt("idUser"), rs.getString("email"), rs.getString("username"), rs.getString("password")));
        }

        con.close();
        return res;
    }




}
