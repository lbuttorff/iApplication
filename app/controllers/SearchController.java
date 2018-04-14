package controllers;

import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static play.libs.Json.toJson;

public class SearchController extends Controller {

    private final FormFactory formFactory;
    private final ArrayList<String> campuses = new ArrayList<>(Arrays.asList(
            "Abington","Altoona","Beaver","Behrend","Berks","Brandywine","DuBois","Fayette","Greater Allegheny",
            "Harrisburg","Hazleton","Lehigh Valley","Mont Alto","New Kensington","Schuylkill","Shenango",
            "University Park","Wilkes-Barre","Worthington Scranton","York"
    ));
    //TODO: This is being left in to describe order of contents. We should update this at some point
    //Department name constants
/*
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
*/
    //Service name constants
/*
    private final String UNDERGRAD_APP = "Undergraduate Application Help";
    private final String GRAD_APP = "Graduate Application Help";
    private final String ESSAY_EDIT = "Essay Editing";
    private final String INTERVIEW_PREP = "Interview Prep";
    private final String DORM_APT_HELP = "Help Finding Dorms & Apartments";
    private final String COLLEGE_VISIT = "College Visits";
*/
    //Experience level constants
/*
    private final String UNDERGRAD = "Undergraduate Student";
    private final String MASTERS = "Masters Student";
    private final String PHD = "Ph.D. Candidate";
    private final String FACULTY = "PSU Faculty Member";
*/
    @Inject
    public SearchController(final FormFactory formFactory){
        this.formFactory = formFactory;
    }

    @RequireCSRFCheck
    public Result search(){
        String term = formFactory.form().bindFromRequest().get("campus"); //TODO: update so that it uses the selected campus, not the default value
        //TODO: Serialize and return List of users
        return ok(search.render(term, User.getCurrentUser()));
    }

    public Result searchResults(){
        String term = formFactory.form().bindFromRequest().get("campus");
        List<User> users;
        if(term == null){
            System.out.println(formFactory.form().bindFromRequest().rawData().toString());
            users = User.find.all();
        }else{
            System.out.println(term);
            users = User.find.query().where().eq("campus", campuses.indexOf(term)).and().eq("type", 1).findList();
        }
        return ok(toJson(users));
    }

    /**
     * Gets most recent data from database and filters according to criteria from filter bar
     * @return
     */
    //TODO: add protection for the default term being selected in the dropdown
    @RequireCSRFCheck
    public Result filter(){
        //Get list of mentors from selected campus
        String term = formFactory.form().bindFromRequest().get("campus"); //TODO: update so that it uses the selected campus, not the default value
        List<User> users = User.find.query().where().eq("campus", campuses.indexOf(term)).and().eq("type", 1).findList();

        //Get Json data and determine filtering criteria
        Map<String, String[]> json = request().body().asFormUrlEncoded();
        if(json == null){
            return badRequest();
        }else{
            int[] selectDepart = new int[15];
            int[] selectServ = new int[6];
            int[] selectExp = new int[4];
            //Generate List of departments
            if(json.get("agSciences")[0].equals("true")) {
                selectDepart[0] = 1;
            }
            if(json.get("artsAndArch")[0].equals("true")) {
                selectDepart[1] = 1;
            }
            if(json.get("business")[0].equals("true")) {
                selectDepart[2] = 1;
            }
            if(json.get("communications")[0].equals("true")) {
                selectDepart[3] = 1;
            }
            if(json.get("earthAndMineralSciences")[0].equals("true")) {
                selectDepart[4] = 1;
            }
            if(json.get("education")[0].equals("true")) {
                selectDepart[5] = 1;
            }
            if(json.get("engineering")[0].equals("true")) {
                selectDepart[6] = 1;
            }
            if(json.get("hhd")[0].equals("true")) {
                selectDepart[7] = 1;
            }
            if(json.get("ist")[0].equals("true")) {
                selectDepart[8] = 1;
            }
            if(json.get("dickinsonLaw")[0].equals("true")) {
                selectDepart[9] = 1;
            }
            if(json.get("psuLaw")[0].equals("true")) {
                selectDepart[10] = 1;
            }
            if(json.get("liberalArts")[0].equals("true")) {
                selectDepart[11] = 1;
            }
            if(json.get("medicine")[0].equals("true")) {
                selectDepart[12] = 1;
            }
            if(json.get("nursing")[0].equals("true")) {
                selectDepart[13] = 1;
            }
            if(json.get("science")[0].equals("true")) {
                selectDepart[14] = 1;
            }

            //Services
            if(json.get("undergradApp")[0].equals("true")) {
                selectServ[0] = 1;
            }
            if(json.get("gradApp")[0].equals("true")) {
                selectServ[1] = 1;
            }
            if(json.get("essayEdit")[0].equals("true")) {
                selectServ[2] = 1;
            }
            if(json.get("interviewPrep")[0].equals("true")) {
                selectServ[3] = 1;
            }
            if(json.get("dormAptHelp")[0].equals("true")) {
                selectServ[4] = 1;
            }
            if(json.get("collegeVisit")[0].equals("true")) {
                selectServ[5] = 1;
            }

            //Generate array of experience levels
            if(json.get("undergrad")[0].equals("true")) {
                selectExp[0] = 1;
            }
            if(json.get("masters")[0].equals("true")) {
                selectExp[1] = 1;
            }
            if(json.get("phd")[0].equals("true")) {
                selectExp[2] = 1;
            }
            if(json.get("faculty")[0].equals("true")) {
                selectExp[3] = 1;
            }

            //Parse results from campus query, compare to arrays, discard if something doesn't match
            for(User user : users ) {
                if (selectDepart[user.getDepartment()] == 0) {
                    users.remove(user);
                } else if (selectExp[user.getStanding()] == 0) {
                    users.remove(user);
                } else {
                    //Create temp array to do double level filtering
                    int[] temp = new int[6];
                    for (int s : user.getServices()) {
                        //If the service exists, we add it to the temp array
                        if (selectServ[s] == 1) {
                            temp[s] = 1;
                        }
                    }
                    //Loop through the temp array to see if there are any 1's
                    for (int i = 0; i < temp.length; i++) {
                        int t = temp[i];
                        if (t == 1) {
                            //Break the loop if the user has any of the services selected
                            break;
                        } else if (i == temp.length - 1) {
                            //If no services match the selection and it is the last time through, we remove the user from the list.
                            users.remove(user);
                        }
                    }
                }
            }
        }
        return ok(toJson(users));
    }
}
