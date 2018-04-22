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
import views.html.userprofile;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;

public class UserController extends Controller {

    private final FormFactory formFactory;

    //Enumeration of campuses for easier access later
    private final ArrayList<String> campuses = new ArrayList<>(Arrays.asList(
            "Abington","Altoona","Beaver","Behrend","Berks","Brandywine","DuBois","Fayette","Greater Allegheny",
            "Harrisburg","Hazleton","Lehigh Valley","Mont Alto","New Kensington","Schuylkill","Shenango",
            "University Park","Wilkes-Barre","Worthington Scranton","York"
    ));

    //Enumeration of academic status
    private final ArrayList<String> statuses = new ArrayList<>(Arrays.asList(
            "Undergraduate Student","Masters Student","Ph.D. Candidate","Faculty Member"
    ));

    //Enumeration of departments
    private final ArrayList<String> departments = new ArrayList<>(Arrays.asList(
            "Agricultural Sciences","Arts and Architecture","Smeal College of Business","College of Communications",
            "Earth and Mineral Sciences","Education","Engineering","Health and Human Development",
            "Information Sciences and Technology","Dickinson Law","Penn State Law","The Liberal Arts",
            "College of Medicine","College of Nursing","Eberly College of Science"
    ));

    @Inject
    public UserController(final FormFactory formFactory){
        this.formFactory = formFactory;
    }

    @AddCSRFToken
    public Result getLogin(){
        return ok(login.render(null));
    }

    @AddCSRFToken
    public Result getSignUp(){
        return ok(signup.render(null));
    }

    public Result login(){
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String email = requestData.get("email");
        String pass = requestData.get("password");
        User u = User.authenticate(email, pass);
        if(u == null){
            return badRequest(login.render("Error: Invalid email and/or password entered"));
        }else {
            session().put("token", u.createToken());
            return ok(main.render(u));
        }
    }

    public Result logout(){
        User user = User.getCurrentUser();
        if(user != null) {
            user.setAuthToken("old");
            user.save();
        }
        session().clear();
        return ok(main.render(null));
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
            return badRequest(signup.render("Error: your passwords did not match"));
        }
        //Set default type to applicant
        int type = 0;
        int campus = -1;
        int status = -1;
        int department = -1;
        ArrayList<Integer> services = new ArrayList<>();
        //Update type if the user selected mentor
        if(requestData.get("roleOption").equals("mentorOption")) {
            type = 1;
            //Get campus
            campus = campuses.indexOf(requestData.get("campusOption"));
            //Get services
            if(requestData.get("undergradAppHelp") != null){
                services.add(1);
            }else{
                services.add(0);
            }
            if(requestData.get("gradAppHelp") != null){
                services.add(1);
            }else{
                services.add(0);
            }
            if(requestData.get("essayHelp") != null){
                services.add(1);
            }else{
                services.add(0);
            }
            if(requestData.get("interviewHelp") != null){
                services.add(1);
            }else{
                services.add(0);
            }
            if(requestData.get("dormAptHelp") != null){
                services.add(1);
            }else{
                services.add(0);
            }
            if(requestData.get("collegeVisit") != null){
                services.add(1);
            }else{
                services.add(0);
            }
            // /Get academic status
            status = statuses.indexOf(requestData.get("statusOption"));
            //Get department
            department = departments.indexOf(requestData.get("departmentOption"));
        }
        int age = Integer.parseInt(requestData.get("age"));
        //Check if user already exists based on email
        if(User.findByEmail(email) != null){
            return badRequest(signup.render("Error: email already belongs to an account."));
        }else{
            //Create new user
            User u = new User(firstName,lastName,email,pass,type,age);;
            //Add additional fields if necessary
            if (type == 1){
                u.setCampus(campus);
                u.setDepartment(department);
                u.setServices(services);;
                u.setStanding(status);
            }
            //Create session token to track current user | also saves the new user
            session().put("token", u.createToken());
            return ok(main.render(u));
        }
    }

    public Result editProfile() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        User u = User.getCurrentUser();
        u.setFirstName(requestData.get("firstName"));
        u.setLastName(requestData.get("lastName"));
        u.setEmail(requestData.get("email"));
        u.setBio(requestData.get("bio"));
        ArrayList<Integer> services = new ArrayList<>();
        //Update type if the user selected mentor
        if(u.getType() == 1) {
            //Get campus
            u.setCampus(campuses.indexOf(requestData.get("campusOption")));
            //Get services
            if(requestData.get("undergradAppHelp") != null){
                services.set(0, 1);
            }else{
                services.set(0, 0);
            }
            if(requestData.get("gradAppHelp") != null){
                services.set(1, 1);
            }else{
                services.set(1, 0);
            }
            if(requestData.get("essayHelp") != null){
                services.set(2, 1);
            }else{
                services.set(2, 0);
            }
            if(requestData.get("interviewHelp") != null){
                services.set(3, 1);
            }else{
                services.set(3, 0);
            }
            if(requestData.get("dormAptHelp") != null){
                services.set(4, 1);
            }else{
                services.set(4, 0);
            }
            if(requestData.get("collegeVisit") != null){
                services.set(5, 1);
            }else{
                services.set(5, 0);
            }
            u.setServices(services)
            // /Get academic status
            u.setStanding(statuses.indexOf(requestData.get("statusOption")));
            //Get department
            u.setDepartment(departments.indexOf(requestData.get("departmentOption")));
        }
        return ok(userprofile.render(u));
    }

    @AddCSRFToken
    public Result getUserProfile() { return ok(userprofile.render(User.getCurrentUser())); }

}
