package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.signup;

public class UserController extends Controller {

    public Result getLogin(){
        return ok(login.render());
    }

    public Result getSignUp(){
        return ok(signup.render());
    }

    public Result login(){
        //TODO: code the login functionality
        return ok();
    }

    public Result signUp(){
        //TODO: code the sign up functionality
        return ok();
    }
}
