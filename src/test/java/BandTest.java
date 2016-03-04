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

}
