import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Venue firstVenue = new Venue("Crystal Ballroom");
    Venue secondVenue = new Venue("Crystal Ballroom");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void find_findVenueInDatabase_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void getBands_getsBandsVenueHasHosted(){
  Band testBand = new Band("Queen");
  testBand.save();
  Venue testVenue = new Venue("Crystal Ballroom");
  testVenue.save();
  testBand.addVenue(testVenue.getId());
  assertEquals(testBand, testVenue.getBands().get(0));
  }
}