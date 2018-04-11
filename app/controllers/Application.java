package controllers;

import models.User;
import play.filters.csrf.AddCSRFToken;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    @AddCSRFToken
    public Result index() {
        User u = User.getCurrentUser();
        return ok(main.render(u));
    }


}
