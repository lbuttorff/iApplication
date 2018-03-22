package controllers;

import play.filters.csrf.AddCSRFToken;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    @AddCSRFToken
    public Result index() {
        return ok(main.render());
    }


}
