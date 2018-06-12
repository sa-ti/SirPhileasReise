package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import modell.QRMap;
import modell.User;
import modell.Country;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sarah on 06.03.2017.
 */
public class UserApplication extends Controller {

    private HashMap<String, User> userList = new HashMap<>();
    private MapApplication mapapp = new MapApplication();
    public HashMap<String, User> getUserList() {
        return userList;
    }

    /**
     * gibt Username des aktuellen Spielers zurück
     * @return
     */
    public Result getUsername() {
        String username = session("connected");
        User user = userList.get(username);
        user.getUsername();
        return ok(username);
    }

    /**
     * erstellt eine Session für aktuellen Spieler
     * @param username
     * @return
     */
    public Result createSession(String username) {
        session().clear();
        session("connected", username);
        return ok();


    }

    /**
     * prüft Logindaten des Spielers
     * @param username
     * @param pwd
     * @return
     */
    public Result loginUser(String username, String pwd) {
        System.out.println("UserLogins: " + username + " " + pwd);
        if (username == "" || pwd == "") {
            return unauthorized("Leere Felder sind unzulässig!");
        }
        try {
            List<User> users = User.fromDBWithCredentials(username, pwd);
            if (users.size() == 1) {
                userList.put(users.get(0).getUsername(), users.get(0));
                users.get(0).getCountriesFromDB();
                users.get(0).getQRFromDB();
                createSession(username);
            } else {
                return unauthorized("Dieser User noch nicht vorhanden. Bitte lege einen neuen Account an!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return unauthorized("SQLException");
        }
        return ok();
    }


    /**
     * Erstellt neuen User, & überprüft Korrektheit der Eingaben
     * @param username
     * @param pwd
     * @param email
     * @return
     */
    public Result createUser(String username, String pwd, String email) {
        System.out.println(username + " " + pwd);
            if(username.length() > 2 && pwd.length()>5 && email.length()>4 && username.matches("[a-zA-Z0-9_-]{3,15}") && pwd.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}") && email.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)@[a-z0-9-]+(.[a-z0-9-]*)(.[a-z]{2,4})$") ){
                try {
                    List<User> users = User.fromDBWithCredentials(username, pwd);
                    if (users.isEmpty()) {
                        User newUser = User.create(email, username, pwd);
                        userList.put(newUser.getUsername(), newUser);
                        newUser.getCountriesFromDB();
                        newUser.getQRFromDB();
                        createSession(username);
                    } else {
                        return unauthorized("User bereits vorhanden");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return unauthorized("SQLException!");
                }
                return ok();
            } else{
                return unauthorized("Bitte halte Dich an das vorgegebene Format.");
            }


    }

    /**
     * Fügt Land in Map des aktuellen Spielers hinzu
     * @param flagurl
     * @return
     */
    public Result saveCountry(String flagurl) {
        User user = userList.get(session("connected"));
        String flag = cutFront(flagurl, "/", 3);
        try {
            Country country = mapapp.getCountry(flag);

            user.addCountry(country);
            System.out.println("Usermap China drin: " + user.getMap().contains(country));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok();
    }

    /**
     * Lädt alle gesammelten Länder eines Spielers
     * @return
     */
    public Result loadCountries() {
        User user = userList.get(session("connected"));
        String[] countryNames = new String[user.getMap().size()];
        for (int i = 0; i < user.getMap().size(); i++) {
            countryNames[i] = user.getMap().get(i).getName();
        }
        Gson gson = new Gson();
        String json = gson.toJson(countryNames);
        return ok(json);

    }

    /**
     * Schneidet URL zu
     * @param txt
     * @param teil
     * @param number
     * @return
     */
    public String cutFront(String txt, String teil, int number) {
        for (int i = 0; i < number; i++) {
            txt = txt.substring(txt.indexOf(teil) + 1, txt.length());
        }
        return txt;
    }

    /**
     * Lädt ensprechende Daten, die im Profil angezeigt werden
     * @return
     */
    public Result loadProfile() {
        User user = userList.get(session("connected"));
        ObjectNode res = Json.newObject();
        res.put("name", user.getUsername());
        res.put("mail", user.getEmail());
        res.put("titel", user.getMap().size());
        return ok(res);

    }

    /**
     * speichert QRCode- eingescannt in QRMap des Users(Würzburg)
     * @return
     */
    public Result saveQRCode(){
        User user = userList.get(session("connected"));
        try{
            user.addQRWürzburg();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Würzburg erfolgreich gespeichert");
        return ok();
}

    /**
     * lädt QRCodeMap des entsprechenden Users (spezifisch für Würzburg)
     * @return
     */
    public Result loadQRCode() {
    User user = userList.get(session("connected"));
    QRMap qrmap = user.getQrmap();
        ObjectNode res = Json.newObject();
        res.put("wuerzburg", qrmap.getWuerzburg());
    return ok(res);
}

}








