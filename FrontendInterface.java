import java.io.FileNotFoundException;
import java.io.IOException;

public interface FrontendInterface {

  /*
   * Constructor that accepts a reference to the backend and a java.util.Scanner
   * instance to read user input.
   */
  // FrontendInterface(Scanner scanner, BackendInterface backend)

  /**
   * specify and load a data file
   *
   * @param filename data file name
   * @throws IOException if IO exception is found
   * @throws FIleNotFoundException if file not found
   */
  void loadDataFile(String filename) throws FileNotFoundException, IOException;

  /**
   * show statistics about the dataset that includes the number of airports
   * (nodes), the number of
   * edges (flights), and the total number of miles (sum of all edge weights) in
   * the graph,
   */
  void showStats();

  /**
   * asks the user for a start and destination airport, then lists the shortest
   * route between those
   * airports, including all airports on the way, the distance for each segment,
   * and the total
   * number of miles from start to destination airport
   */
  void findRoute(String start, String destination);

  /**
   * Loop the menu and wait for choice
   */
  void menuLoop();

  /**
   * Exit the app
   */
  void exit();
}
