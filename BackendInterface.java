import java.io.FileNotFoundException;
import java.io.IOException;

public interface BackendInterface {

  /*
   * Constructs a BackendInterface with GraphADT instance.
   *
   * @param graph GraphADT instance
   */
  //BackendInterface(GraphADT graph);

  /**
   * Reads data from given file
   *
   * @param filePath The path of the file to read data from.
   * @throws FileNotFoundException if the file does not exist or is invalid (wrong csv header).
   */
  void readFile(String filePath) throws FileNotFoundException, IOException;

  /**
   * Finds the shortest route from a start to a destination airport.
   *
   * @param startAirport The starting airport.
   * @param destAirport  The destination airport.
   * @return A ShortestPathSearchInterface object containing the shortest route.
   * @throws IllegalArgumentException if the start or destination airport does not exist.
   */
  ShortestPathSearchInterface getShortestRoute(String startAirport, String destAirport)
      throws IllegalArgumentException;

  /**
   * Returns a string with statistics that include the number of airports, flights, and the total
   * miles.
   *
   * @return A String containing the dataset statistics.
   */
  String getStats();
}
