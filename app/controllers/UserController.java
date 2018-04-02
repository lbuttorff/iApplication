package controllers;

import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.main;
import views.html.signup;

import javax.inject.Inject;

public class UserController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public UserController(final FormFactory formFactory){
        this.formFactory = formFactory;
    }

    @AddCSRFToken
    public Result getLogin(){
        return ok(login.render());
    }

    @AddCSRFToken
    public Result getSignUp(){
        return ok(signup.render());
    }

    public Result login(){
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String email = requestData.get("email");
        String pass = requestData.get("password");
        User u = User.authenticate(email, pass);
        if(u == null){
            return badRequest(login.render());
        }else {
            return ok(main.render());
        }
    }

    public Result signUp(){
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String firstName = requestData.get("firstName");
        String lastName = requestData.get("lastName");
        String email = requestData.get("email");
        String confEmail = requestData.get("confirmEmail");
        String pass = requestData.get("password");
        String confPass = requestData.get("confirmPassword");
        //Check passwords and emails for matching
        if(!pass.equals(confPass) || !email.equals(confEmail)){
            return badRequest(signup.render());
        }
        //Set default type to applicant
        int type = 0;
        //Update type if the user selected mentor
        if(requestData.get("roleOption").equals("mentorOption")) {
            type = 1;
        }
        int age = Integer.parseInt(requestData.get("age"));
        //Check if user already exists based on email
        if(User.findByEmail(email) != null){
            return badRequest(signup.render());
        }else{
            //Create new user and save to database
            User u = new User(firstName,lastName,email,pass,type,age);
            u.save();
        }
        return ok(main.render());
    }
}
