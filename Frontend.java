import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * this class is the frontend that's methods implement the frontend interface,
 * where the program's outputs are coded
 */
public class Frontend implements FrontendInterface {

    Scanner scnr;
    IndividualBackendInterface backend;


    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph); 
        Frontend frontend = new Frontend(scnr, backend);

        frontend.menuLoop();
    }

    /**
     * creates the constructor for Frontend that takes in a reference to the backend
     * and scanner
     *
     * @param scnr    is a type of Scanner that takes in user scnr
     * @param backend is a type of FRBackendPlaceholder represents a reference to
     *                the backend for integration
     */
    public Frontend(Scanner scnr, IndividualBackendInterface backend) {
        this.scnr = scnr;
        this.backend = backend;

    }

    boolean programRunning = true;

    /**
     * specify and load a data file
     *
     * @param filename data file name
     *                 returns if the file was found or not in a string
     */
    public void loadDataFile(String filename) throws FileNotFoundException, IOException{
        try {
            backend.readFile(filename);
            System.out.println("file was found!");
        } 
         catch (FileNotFoundException e) {
            System.out.println("file was not found!");
        }
        catch (IOException e) {
            System.out.println("file was not found!");
        }
       
        


    }

    /**
     * show statistics about the dataset that includes the number of airports
     * (nodes), the number of
     * edges (flights), and the total number of miles (sum of all edge weights) in
     * the graph
     * returns the dataset statistics in a string
     */
    public void showStats() {
        System.out.println(backend.getStats());
    }

    /**
     * asks the user for a start and destination airport, then lists the shortest
     * route between those
     * airports, including all airports on the way, the distance for each segment,
     * and the total
     * number of miles from start to destination airport
     * 
     * @param startAirport       String - starting inputted user airport of route
     * @param destinationAirport String - ending inputted user airport of route
     *                           returns the final route as a list in a string
     */
    public void findRoute(String startAirport, String destinationAirport) {
        ShortestPathSearchInterface shortestPath = backend.getShortestRoute(startAirport, destinationAirport);
        System.out.println("Shortest path: " + shortestPath.getRoute() + "\n" +
                "Segment distances between airports: " + shortestPath.getSegmentMiles() + "\n" +
                "Total miles: " + shortestPath.getTotalMiles());

    }

    /**
     * Loop the menu and wait for choice
     * returns messages to the console and calls each frontend method
     */
    public void menuLoop() {
        while (programRunning) {
            System.out.println(
                    "Welcome to Flight Router! Press a command to start:\n(1)Load Data File\n(2)Show Stats\n(3)Find Route\n(4)Exit");
            String i = scnr.next();
            if (i.equals("1")) {
                System.out.println("Enter file name: ");
                String filename = scnr.next();
                try {
                    this.loadDataFile(filename);
                } catch (FileNotFoundException e) {
                    // e.getMessage();
                }
                catch (IOException e) {
                    // e.getMessage();
                }

            } else if (i.equals("2")) {
                this.showStats();
            } else if (i.equals("3")) {
                System.out.println("Enter a starting and destination airport location: ");

                if (scnr.hasNext()) {
                    String startAirport = scnr.next();
                    String destinationAirport = scnr.next();
                    this.findRoute(startAirport, destinationAirport);
                } else {
                    System.out.println("No scnr found.");
                }

            } else if (i.contains("4")) {
                this.exit();
                System.out.println("Thanks for using Flight Router!");
                break;
            } else {
                System.out.println("Invalid scnr input");
            }
        }
    }

    /**
     * Exit the app by setting its running boolean value to false, stopping the
     * while loop
     */
    public void exit() {
        programRunning = false;
    }

}