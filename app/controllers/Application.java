package controllers;

import com.google.inject.Inject;
import play.api.db.Database;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by Sarah on 29.11.2016.
 */
public class Application extends Controller {

    public static Database db;

    @Inject
    public Application(Database db){
        this.db = db;
    }

    public Result login() {
        return ok(login.render());
    }

    public Result register() { return ok(register.render()); }

    public Result startseite() {
        return ok(startseite.render());
    }

    public Result welcome() {
        return ok(welcome.render());
    }

    public Result training() { return ok(training.render());}

    public Result map() {
        return ok(map.render());
    }

    public Result profile() { return ok(profile.render());}

    public Result logout() {session().clear(); return ok(logout.render());}

    public Result logbook() { return ok(logbook.render());}

    public Result level1() { return ok(level1.render());}

    public Result level2() { return ok(level2.render());}

    public Result spielmodus() {return ok(spielmodus.render());}

    public Result tutorial(){ return ok(tutorial.render());}

    public Result qrcode() { return ok(qrcodemap.render());}

    public Result qrcodescanner(){return ok(qrcodescanner.render());}

    public Result event_china() {return ok(event_china.render());
    }
}

