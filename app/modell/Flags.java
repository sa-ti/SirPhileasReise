package modell;

/**
 * Created by Felix on 06.03.2017.
 */

import java.util.List;
import java.util.LinkedList;
import java.sql.*;

public class Flags {

    List<String> flaglist;

    /**
     * initialisiert Flaggen mit Flaggen aus Datenbank
     * @throws SQLException
     */
    public Flags() throws SQLException {
        this.flaglist = fromDB();
    }

    /**
     * wählt zufällige Flaggen aus und gibt sie in einem Array zurück
     * @return
     */
    public String[] pickRandomFlags() {
        String[] random = new String[6];
        for (int i = 0; i < 6; i++) {
            int temp = (int) (Math.random() * flaglist.size()) + 0;
            random[i] = flaglist.get(temp);
            flaglist.remove(temp);
            }
        return random;
    }

    /**
     * gibt Liste mit allen Flaggen zurück
     * @return
     * @throws SQLException
     */
    public List<String> fromDB() throws SQLException {
        List<String> res = new LinkedList<String>();
        Connection con = controllers.Application.db.getConnection();
        String query = "SELECT flag FROM Country WHERE name <> 'China'";
        PreparedStatement statement;
        ResultSet rs;
        try {
            statement = con.prepareStatement(query);
            rs = statement.executeQuery();
        while (rs.next()) {
            res.add(rs.getString("flag"));
        }
        con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}

