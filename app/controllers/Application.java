package controllers;

import models.User;
import play.filters.csrf.AddCSRFToken;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    private boolean loaded = false;

    @AddCSRFToken
    public Result index() {
        if(!loaded) {
            UserController.createDummyUsers();
            loaded= true;
        }
        User u = User.getCurrentUser();
        return ok(main.render(u));
    }


}
