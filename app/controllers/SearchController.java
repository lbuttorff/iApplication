package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

public class SearchController extends Controller {

    public Result getSearch(){
        return ok(search.render());
    }
}
