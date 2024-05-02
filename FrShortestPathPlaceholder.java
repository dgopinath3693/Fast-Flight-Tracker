import java.util.Arrays;
import java.util.List;

public class FrShortestPathPlaceholder implements ShortestPathSearchInterface {

  /**
   * Gets the route as a list of airports
   *
   * @return A list airports along the route.
   */
  public List<String> getRoute() {
    return Arrays.asList("ATL", "MIA", "SFO");
  }

  /**
   * Gets the list of miles to travel for each segment of the route.
   *
   * @return A list of the miles for each segment of the route.
   */
  public List<Integer> getSegmentMiles() {
    return Arrays.asList(100, 150, 200);
  }

  /**
   * Gets the total miles for the entire route.
   *
   * @return The total miles
   */
  public int getTotalMiles() {
    return 450;
  }


}


