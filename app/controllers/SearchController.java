package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchController extends Controller {

    private final FormFactory formFactory;
    //Department name constants
    private final String AG_SCI = "Agricultural Sciences";
    private final String ARTS_AND_ARCH = "Arts and Architecture";
    private final String BUSINESS = "Smeal College of Business";
    private final String COMMUNICATIONS = "College of Communications";
    private final String EARTH_AND_MINERAL_SCIENCES = "Earth and Mineral Sciences";
    private final String EDUCATION = "Education";
    private final String ENGINEERING = "Engineering";
    private final String HHD = "Health and Human Development";
    private final String IST = "Information Sciences and Technology";
    private final String DICKINSON_LAW = "Dickinson Law";
    private final String PSU_LAW = "Penn State Law";
    private final String LIBERAL_ARTS = "The Liberal Arts";
    private final String MEDICINE = "College of Medicine";
    private final String NURSING = "College of Nursing";
    private final String SCIENCE = "Eberly College of Science";
    private final String HONORS = "Schreyer Honors College";

    //Service name constants
    private final String UNDERGRAD_APP = "Undergraduate Application Help";
    private final String GRAD_APP = "Graduate Application Help";
    private final String ESSAY_EDIT = "Essay Editing";
    private final String INTERVIEW_PREP = "Interview Prep";
    private final String DORM_APT_HELP = "Help Finding Dorms & Apartments";
    private final String COLLEGE_VISIT = "College Visits";

    //Experience level constants
    private final String UNDERGRAD = "Undergraduate Student";
    private final String MASTERS = "Masters Student";
    private final String PHD = "Ph.D. Candidate";
    private final String FACULTY = "PSU Faculty Member";

    @Inject
    public SearchController(final FormFactory formFactory){
        this.formFactory = formFactory;
    }

    @RequireCSRFCheck
    public Result search(){
        String term = formFactory.form().bindFromRequest().get("campus"); //TODO: update so that it uses the selected campus, not the default value
        List<User> users = User.find.query().where().eq("campus", term).and().eq("type", 2).findList(); //TODO: Confirm int value for "mentor" type
        //TODO: Serialize and return List of users
        return ok(search.render(term));
    }

    /**
     * Gets most recent data from database and filters according to criteria from filter bar
     * @return
     */
    @RequireCSRFCheck
    public Result filter(){
        //Get list of mentors from selected campus
        String term = formFactory.form().bindFromRequest().get("campus"); //TODO: update so that it uses the selected campus, not the default value
        List<User> users = User.find.query().where().eq("campus", term).and().eq("type", 2).findList(); //TODO: Confirm int value for "mentor" type

        //Get Json data and determine filtering criteria
        Map<String, String[]> json = request().body().asFormUrlEncoded();
        if(json == null) { //Data not sent in type expected
            return badRequest();
        } else {
            ArrayList<String> departments = new ArrayList<>();
            ArrayList<String> services = new ArrayList<>();
            ArrayList<String> experienceLevels = new ArrayList<>();

            //Generate List of departments
            if(json.get("agSciences")[0].equals("true")) {
                departments.add(AG_SCI);
            }
            if(json.get("artsAndArch")[0].equals("true")) {
                departments.add(ARTS_AND_ARCH);
            }
            if(json.get("business")[0].equals("true")) {
                departments.add(BUSINESS);
            }
            if(json.get("communications")[0].equals("true")) {
                departments.add(COMMUNICATIONS);
            }
            if(json.get("earthAndMineralSciences")[0].equals("true")) {
                departments.add(EARTH_AND_MINERAL_SCIENCES);
            }
            if(json.get("education")[0].equals("true")) {
                departments.add(EDUCATION);
            }
            if(json.get("engineering")[0].equals("true")) {
                departments.add(ENGINEERING);
            }
            if(json.get("hhd")[0].equals("true")) {
                departments.add(HHD);
            }
            if(json.get("ist")[0].equals("true")) {
                departments.add(IST);
            }
            if(json.get("dickinsonLaw")[0].equals("true")) {
                departments.add(DICKINSON_LAW);
            }
            if(json.get("psuLaw")[0].equals("true")) {
                departments.add(PSU_LAW);
            }
            if(json.get("liberalArts")[0].equals("true")) {
                departments.add(LIBERAL_ARTS);
            }
            if(json.get("medicine")[0].equals("true")) {
                departments.add(MEDICINE);
            }
            if(json.get("nursing")[0].equals("true")) {
                departments.add(NURSING);
            }
            if(json.get("science")[0].equals("true")) {
                departments.add(SCIENCE);
            }
            if(json.get("honors")[0].equals("true")) {
                departments.add(HONORS);
            }

            //Generate array of services
            if(json.get("undergradApp")[0].equals("true")) {
                services.add(UNDERGRAD_APP);
            }
            if(json.get("gradApp")[0].equals("true")) {
                services.add(GRAD_APP);
            }
            if(json.get("essayEdit")[0].equals("true")) {
                services.add(ESSAY_EDIT);
            }
            if(json.get("interviewPrep")[0].equals("true")) {
                services.add(INTERVIEW_PREP);
            }
            if(json.get("dormAptHelp")[0].equals("true")) {
                services.add(DORM_APT_HELP);
            }
            if(json.get("collegeVisit")[0].equals("true")) {
                services.add(COLLEGE_VISIT);
            }

            //Generate array of experience levels
            if(json.get("undergrad")[0].equals("true")) {
                experienceLevels.add(UNDERGRAD);
            }
            if(json.get("masters")[0].equals("true")) {
                experienceLevels.add(MASTERS);
            }
            if(json.get("phd")[0].equals("true")) {
                experienceLevels.add(PHD);
            }
            if(json.get("faculty")[0].equals("true")) {
                experienceLevels.add(FACULTY);
            }

            //Parse results from campus query, compare to arrays, discard if something doesn't match
            for(User user : users ) {
                if(!(departments.contains(user.getDepartment()))) {
                    users.remove(user);
                }
                else if (!(services.contains(user.getServices()))) { //TODO: implement getServices() in user model
                    users.remove(user);
                }
                else if(!(experienceLevels.contains(user.getStanding()))) {
                    users.remove(user);
                }
            }

            //Turn List into Json
            return ok(Json.toJson(users));
        }
    }
}
