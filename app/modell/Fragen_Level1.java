package modell;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sarah on 06.03.2017.
 */
public class Fragen_Level1 {

    private List<Fragen_Level1> fragenListe;
    private int questionAmount;
    private String frage;
    private String antwort1;
    private String antwort2;
    private String antwort3;
    private String antwort4;
    private String korrekte_Antwort;


    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public String getAntwort1() {
        return antwort1;
    }

    public void setAntwort1(String antwort1) {
        this.antwort1 = antwort1;
    }

    public String getAntwort2() {
        return antwort2;
    }

    public void setAntwort2(String antwort2) {
        this.antwort2 = antwort2;
    }

    public String getAntwort3() {
        return antwort3;
    }

    public void setAntwort3(String antwort3) {
        this.antwort3 = antwort3;
    }

    public String getAntwort4() {
        return antwort4;
    }

    public void setAntwort4(String antwort4) {
        this.antwort4 = antwort4;
    }


    public String getKorrekte_Antwort() {
        return korrekte_Antwort;
    }

    public void setKorrekte_Antwort(String korrekte_Antwort) {
        this.korrekte_Antwort = korrekte_Antwort;
    }


    /**
     *  Konstruktor speichert Anzahl an fragen des zugehörigen Land & Fragen in liste
      */
    public Fragen_Level1(int idCountry) throws SQLException{
        try{
            questionAmount = getAmountFromDB(idCountry);
            fragenListe = getQuestionsFromDB(idCountry);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * wählt aus allen möglichen Fragen zu einem Land drei aus & gibt sie zurück
     * @return
     * @throws SQLException
     */
    public List<Fragen_Level1> randomFrage()throws SQLException{
        List<Fragen_Level1> threeQuestions = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            int random = random(fragenListe.size() - 1);
            threeQuestions.add(fragenListe.get(random));
            fragenListe.remove(random);
        }
        return threeQuestions;
    }


    /**
     *  erstellt Zufallszahl
      */
    public int random(int max) {
        int range = (max - 1) + 1;
        System.out.println("random" + range);
        return (int) (Math.random() * range) + 1;
    }


    /**
     *  speichert in "numberOfRows" Anzahl aller möglichen Quizfragen
     *  zu einem Land
      */
    public int getAmountFromDB(int idCountry) throws SQLException {
        Connection con = controllers.Application.db.getConnection();
        PreparedStatement statement;
        ResultSet rs;
        String query = "SELECT COUNT(*) FROM Fragen_Level1 WHERE Country_idCountry = " + idCountry;
        int numberOfRows = 0;
        try {
            statement = con.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                numberOfRows = rs.getInt(1);
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("getamountfromdb: " + numberOfRows);
        return numberOfRows;
    }

    public Fragen_Level1(String frage, String antwort1, String antwort2, String antwort3, String antwort4, String korrekte_Antwort) {
        this.frage = frage;
        this.antwort1 = antwort1;
        this.antwort2 = antwort2;
        this.antwort3 = antwort3;
        this.antwort4 = antwort4;
        this.korrekte_Antwort = korrekte_Antwort;
    }

    /**
     * liest Quiz aus Datenbank und speichert Komponenten in Liste
     */
    public List<Fragen_Level1> getQuestionsFromDB(int idCountry) throws SQLException {
        List<Fragen_Level1> liste = new LinkedList();
        Connection con = controllers.Application.db.getConnection();
        PreparedStatement statement;
        ResultSet rs;
        String query = "";
        //getQuiz
        query = "SELECT * FROM Fragen_Level1 WHERE Country_idCountry =" + idCountry;
        statement = con.prepareStatement(query);
        rs = statement.executeQuery();
        while (rs.next()) {
            Fragen_Level1 frage = new Fragen_Level1(rs.getString("Frage"), rs.getString("Antwort1"),
                    rs.getString("Antwort2"), rs.getString("Antwort3"), rs.getString("Antwort4"),
                    rs.getString("korrekte_Antwort"));
            liste.add(frage);
        }
        System.out.println("liste: " + liste);
        con.close();
        return liste;
    }

}