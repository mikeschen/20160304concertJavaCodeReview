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
		  model.put("bands", Band.all());
		  model.put("venues", Venue.all());
		  model.put("template", "templates/index.vtl");
		  return new ModelAndView(model, layout);
		}, new VelocityTemplateEngine());

		//VIEW INDIVIDUAL BAND
    get("/band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", band);
      model.put("assignedVenues", band.getVenues());
      model.put("venues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

	  //CREATE BAND OBJECT
	  post("/band/new", (request, response) -> {
	    HashMap<String, Object> model = new HashMap<String, Object>();
	    String name = request.queryParams("bandName");
	    Band newBand = new Band(name);
	    newBand.save();
	    response.redirect("/");
	    return null;
	  });

	  //CREATE VENUE OBJECT
    post("/venue/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("venueName");
      Venue newVenue = new Venue(name);
      newVenue.save();
      response.redirect("/");
      return null;
    });

    //ASSIGN VENUE TO BAND
    post("/band/:id/assign-venue", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      int venueId = Integer.valueOf(request.queryParams("venueId"));
      band.addVenue(venueId);
      String url = String.format("/band/%d", band.getId());
      response.redirect(url);
      return null;
    });
	}
}
