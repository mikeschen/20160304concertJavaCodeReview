import java.util.*;
import org.sql2o.*;

public class Venue {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Venue(String name) {
    this.name = name;
  }

  public static List<Venue> all() {
    String sql = "SELECT id, name FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  // public void addBand(Band newBand) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO bands_venues(band_id, venue_id) VALUES (:band_id, :venue_id)";
  //     con.createQuery(sql)
  //       .addParameter("band_id", newBand.getId())
  //       .addParameter("venue_id", id)
  //       .executeUpdate();
  //   }
  // }

  public List<Band> getBands(){
    try(Connection con = DB.sql2o.open()){
        String sql = "SELECT bands.id, bands.name FROM bands JOIN bands_venues ON (bands.id = bands_venues.band_id) JOIN venues ON (bands_venues.venue_id = venues.id) WHERE venues.id = :venue_id;";
        List<Band> bands = con.createQuery(sql).addParameter("venue_id", this.getId()).executeAndFetch(Band.class);
        return bands;
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where id=:id";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }
}