import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class is the backend placeholder for the frontend that implements the
 * backend interface
 */
public class FRBackendPlaceholder implements IndividualBackendInterface {

    // GraphADT graph;

    /**
     * constructor for backend placeholder
     *
     */
    public FRBackendPlaceholder() {
        // this.graph = graph;
    }

    /**
     * reads the file and if the file is found, prints that out. Otherwise catches
     * file not found exception and prints that out.
     *
     * @param filePath data file name
     */
    @Override
    public void readFile(String filePath) throws FileNotFoundException {
        if (filePath.equals("flights.dot")) {
            System.out.println("file was found!");
        } else {
            System.out.println("file was not found!");
        }

    }

    /**
     * gets the dataset statistics of airports, flights, and total miles in graph
     *
     */
    @Override
    public String getStats() {
        return "Number of airports(nodes): 7\nNumber of flights(edges): 12\nNumber of total miles in graph(sum of edge weights): 452";

    }

    /**
     * takes a starting and destination airport and prints the shortest route
     * between them, segment distances between them, and total miles
     *
     * @param startAirport String - the starting airport to calculate the route of
     * @param destAirport  String - the final airport to calcualte the route of
     */
    @Override
    public ShortestPathSearchInterface getShortestRoute(String startAirport, String destAirport)
            throws IllegalArgumentException {
        return new FrShortestPathPlaceholder();
    }

}
