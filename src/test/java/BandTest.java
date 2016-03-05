import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void save_savesBandintoDatabase() {
  	Band testBand = new Band("Sting");
  	testBand.save();
  	Band savedBand = Band.all().get(0);
  	assertTrue(savedBand.equals(testBand));
  }

  @Test
  public void save_assignsIdToBand() {
    Band testBand = new Band("Sting");
    testBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(testBand.getId(), savedBand.getId());
  }

  @Test
  public void find_findsBandInDatabase_true() {
    Band testBand = new Band("Sting");
    testBand.save();
    Band savedBand = Band.find(testBand.getId());
    assertTrue(testBand.equals(savedBand));
  }

  @Test
	  public void updateName_updatesNamePropertyOfBandObject_true(){
		Band testBand = new Band("Sting");
		testBand.save();
		testBand.update("Queen");
		assertEquals(Band.all().get(0).getName(), "Queen");
  }

  @Test
	  public void deleteBand_deletesBand_true(){
		Band testBand = new Band("Sting");
		testBand.save();
		assertEquals(Band.all().size(), 1);
		testBand.delete(testBand.getId());
		assertEquals(Band.all().size(), 0);
  }

  @Test
    public void addToVenue_createsRecordInBandsVenuesTable_true(){
    Band testBand = new Band("Queen");
    testBand.save();
    Venue testVenue = new Venue("Crystal Ballroom");
    testVenue.save();
    testBand.addVenue(testVenue.getId());
    Venue savedVenue = testBand.getVenues().get(0);
    assertTrue(testVenue.equals(savedVenue));
  }
}
