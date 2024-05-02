import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IndividualBackendInterface {

    /**
     * Constructs a BackendInterface with GraphADT instance.
     *
     * @param graph GraphADT instance
     */
    //BackendInterface(GraphADT graph);

    /**
     * Reads data from given file
     *
     * @param filePath The path of the file to read data from.
     * @throws IOException
     * @throws FileNotFoundException
     */
    void readFile(String filePath) throws FileNotFoundException, IOException;

    /**
     * Finds the shortest route from a start to a destination airport.
     *
     * @param startAirport The starting airport.
     * @param destAirport The destination airport.
     * @return A List showing the shortest route between airports.
     */
    ShortestPathSearchInterface getShortestRoute(String startAirport, String destAirport);

    /**
     * Returns a string with statistics that include the number of airports,flights, and the total miles.
     *
     * @return A String containing the dataset statistics.
     */
    String getStats();
}