import java.util.*;
import org.sql2o.*;
import org.apache.commons.lang.WordUtils;

public class Band {
  private int id;
  private String name;

  public Band(String name) {
    this.id = id;
    this.name = WordUtils.capitalize(name);
  }

  //Geters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) && this.getId() == newBand.getId();
    }
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands ORDER BY name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  public void addVenue(int venueId){
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id);";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
        .addParameter("venue_id", venueId)
        .addParameter("band_id", this.id)
        .executeUpdate();
    }
  }

  //getVenue method using JOINS
  public List<Venue> getVenues(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT venues.id, venues.name FROM venues JOIN bands_venues ON (venues.id = bands_venues.venue_id) JOIN bands ON (bands_venues.band_id = bands.id) WHERE bands.id = :band_id;";
      List<Venue> venues = con.createQuery(sql).addParameter("band_id", this.getId()).executeAndFetch(Venue.class);
      return venues;
    }
  }

  //CREATE
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(name) VALUES (:name)";
      this.id = (int)con.createQuery(sql, true)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }

  //READ
  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id=:id";
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  //UPDATE
  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET name = :name WHERE id=:id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  //DELETE
  public static void delete(int id) {
    String sql = "DELETE FROM bands WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
