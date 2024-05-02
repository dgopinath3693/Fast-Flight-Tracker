import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Backend implements IndividualBackendInterface {

    private GraphADT<String, Integer> graph; // stores the current graph of airport
    private double totalDistance = 0;

    /*
     * Constructor that takes initializes the graph
     */
    public Backend(GraphADT<String, Integer> graph) {
        this.graph = graph;
    }

    @Override
    public void readFile(String filePath) throws FileNotFoundException, IOException{
        // read file and break up nodes + miles to travel between airports
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.contains("--")) {
                    // Split the line based on specific patterns
                    String[] tokens = line.split("\\s*--\\s*|\\s*\\[miles=|\\];");

                    // Extract information
                    String startAirportNode = tokens[0].replaceAll("\"", "").trim();
                    String endAirportNode = tokens[1].replaceAll("\"", "").trim();
                    double weight = Double.parseDouble(tokens[2]);

                    // add non-duplicates and edges
                    if (!graph.containsNode(startAirportNode)) {
                        graph.insertNode(startAirportNode);
                    }

                    if (!graph.containsNode(endAirportNode)) {
                        graph.insertNode(endAirportNode);
                    }

                    graph.insertEdge(startAirportNode, endAirportNode, (int) weight);
                    graph.insertEdge(endAirportNode, startAirportNode, (int) weight);
                    totalDistance += weight;
                }
            }
        } catch (Exception e) {
            throw e;
            
        }
    }

    /*
     * Obtains and formats the shortest path for frontend.
     * 
     * @param startPoint, the first airport of the path
     * 
     * @param endPoint, the last airport of the path
     */

    public ShortestPathSearchInterface getShortestRoute(String startAirport, String destAirport){
        if (!graph.containsNode(startAirport) || !graph.containsNode(destAirport)) {
            throw new IllegalArgumentException("Could not find the desired Start airport or Destination airport.");
        }
        
        List<String> airports = graph.shortestPathData(startAirport, destAirport);
        
        List<Integer> segmentDistance = new LinkedList<>();
        
        for(int i = 0; i < airports.size() - 1; i++) {
            String originAirport = airports.get(i);
            String finalAirport = airports.get(i + 1);
            
            Integer flightDistance = graph.getEdge(originAirport, finalAirport);
            
            segmentDistance.add(flightDistance);
             
        }
        
        
        int totalCost =(int) graph.shortestPathCost(startAirport,destAirport);
        
        return new ShortestPathSearch (airports, segmentDistance, totalCost);
    }
    

    @Override
    public String getStats() {
        int numAirports = graph.getNodeCount();
        int numFlights = graph.getEdgeCount();
        return "Airports: " + numAirports + ", Flights: " + numFlights + ", Total Miles: " + totalDistance;
    }

}