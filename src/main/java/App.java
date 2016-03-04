import java.util.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

		//VIEW INDEX
		get("/", (request, response) -> {
		  HashMap<String, Object> model = new HashMap<String, Object>();
		  model.put("template", "templates/index.vtl");
		  model.put("bands", Band.all());
		  return new ModelAndView(model, layout);
		}, new VelocityTemplateEngine());

	  //CREATE BOOK OBJECT
	  post("/band/new", (request, response) -> {
	    HashMap<String, Object> model = new HashMap<String, Object>();
	    String name = request.queryParams("bandName");
	    Band newBand = new Band(name);
	    newBand.save();
	    response.redirect("/");
	    return null;
	  });
	}
}
