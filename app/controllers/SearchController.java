package controllers;

import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

import java.util.List;

public class SearchController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public SearchController(final FormFactory formFactory){
        this.formFactory = formFactory;
    }

    @RequireCSRFCheck
    public Result search(){
        String term = formFactory.form().bindFromRequest().get("campus");
        List<User> users = User.find.query().where().eq("campus", term).findList();
        return ok(search.render(term));
    }

    /**
     *
     * @return
     */
    @RequireCSRFCheck
    public Result filter(){
        String term = formFactory.form().bindFromRequest().get("campus");
        return ok();
    }
}
