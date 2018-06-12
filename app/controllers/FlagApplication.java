package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import modell.Flags;
import play.mvc.*;

import static play.mvc.Results.ok;


/**
 * Created by Felix on 07.03.2017.
 */
public class FlagApplication {

    private Flags flaggen;
    private String[] randomFlags;


    /**
     * Wählt für das Memory Flaggen aus und leitet sie per ajax weiter
     * @return
     */
    public Result loadFlags(){
        try {
            flaggen = new Flags();
            randomFlags = flaggen.pickRandomFlags();
        }catch(Exception e){
            e.printStackTrace();
        }

        ObjectNode result = Json.newObject();
            result.put("Flagge1", randomFlags[0]);
            result.put("Flagge2", randomFlags[1]);
            result.put("Flagge3", randomFlags[2]);
            result.put("Flagge4", randomFlags[3]);
            result.put("Flagge5", randomFlags[4]);
            result.put("Flagge6", randomFlags[5]);
        return ok(result);

    }

}
