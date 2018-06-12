package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import modell.Country;
import modell.Fragen_Level1;
import modell.Fragen_Level2;
import play.libs.Json;
import play.mvc.*;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Sarah on 06.03.2017.
 */
public class QuestionsApplication extends Controller {

    private Fragen_Level1 quiz1;
    private Fragen_Level2 quiz2;
    private List<Fragen_Level1> threeQuestions;
    private List<Fragen_Level2> threeQuestionsLvl2;
    private MapApplication mapapp = new MapApplication();

    /**
     * LEVEL1: erhält url der Flagge, wählt eine zum Land gehörige Frage aus und leitet diese per ajax weiter
     */
    public Result loadQuizL1(String flagurl){
        String flag = cutFront(flagurl,"/",3);
        //Lädt Quiz nur dann neu, wenn auch ein neues Land umgedeckt wurde
        if(quiz1 == null) {
            try {
                Country country = mapapp.getCountry(flag);
                quiz1 = new Fragen_Level1(country.getIdCountry());
                threeQuestions = new LinkedList<>(quiz1.randomFrage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Gibt eine der 3 zufaelligen Fragen zurueck
        JsonNode question = Json.toJson(threeQuestions.get(0));
        threeQuestions.remove(0);
        //Wenn keine Fragen mehr uebrig sind wird das Quiz resettet
        if(threeQuestions.isEmpty()){
            quiz1 = null;
        }
        return ok(question);
    }

    /**
     * LEVEL 2: erhält url der Flagge, wählt eine zum Land gehörige Frage aus und leitet diese per ajax weiter
     */
    public Result loadQuizL2(String flagurl){
        String flag = cutFront(flagurl,"/",3);
        //Lädt Quiz nur dann neu, wenn auch ein neues Land umgedeckt wurde
        if(quiz2 == null) {
            try {
                Country country = mapapp.getCountry(flag);
                quiz2 = new Fragen_Level2(country.getIdCountry());
                threeQuestionsLvl2 = new LinkedList<>(quiz2.randomFrage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Gibt eine der 3 zufaelligen Fragen zurueck
        JsonNode question = Json.toJson(threeQuestionsLvl2.get(0));
        threeQuestionsLvl2.remove(0);
        //Wenn keine Fragen mehr uebrig sind wird das Quiz resettet
        if(threeQuestionsLvl2.isEmpty()){
            quiz2 = null;
        }
        return ok(question);
    }

    /**
     * schneidet von url den Teil letzten Teil ab, um Land in DB zu identifizieren
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
     * Setzt alle Instanzen des Quiz zurück, um bereits geladene Fragen durch z.B. Spielabbruch
     * zurueckzusetzen
     * @return confirmation
     */
    public Result resetQuiz(){
        quiz1 = null;
        quiz2 = null;
        threeQuestions = null;
        threeQuestionsLvl2 = null;
        return ok("resetQuiz");
    }

}

