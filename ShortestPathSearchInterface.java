import java.util.List;

public interface ShortestPathSearchInterface {

    /**
     * Gets the route as a list of airports
     *
     * @return A list airports along the route.
     */
    List<String> getRoute();

    /**
     * Gets the list of miles to travel for each segment of the route.
     *
     * @return A list of the miles for each segment of the route.
     */
    List<Integer> getSegmentMiles();

    /**
     * Gets the total miles for the entire route.
     *
     * @return The total miles
     */
    int getTotalMiles();
}
