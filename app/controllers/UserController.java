package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.signup;

public class UserController extends Controller {

    public Result getLogin(){
        return ok(login.render());
    }

    public Result getSignup(){
        return ok(signup.render());
    }
}
