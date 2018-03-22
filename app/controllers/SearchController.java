package controllers;

import com.google.inject.Inject;
import play.data.FormFactory;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

public class SearchController extends Controller {

    private final FormFactory formFactory;

    @Inject
    public SearchController(final FormFactory formFactory){
        this.formFactory = formFactory;
    }

    @RequireCSRFCheck
    public Result search(){
        String term = formFactory.form().bindFromRequest().get("term");
        return ok(search.render(term));
    }
}
