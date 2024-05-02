// --== CS400 File Header Information ==--
// Name: Diya Gopinath
// Email: dgopinath2@wisc.edu
// Group and Team: E08
// Group TA: Aydan Bailey
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.Assertions;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * 
     * @param map the map that the graph uses to map a data object to the node
     *            object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {

        // checks if the start and end nodes exist
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            throw new NoSuchElementException("Start/end node doesnt exist");
        }

        // initializes priority queue
        PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>();

        // intitilizes map that holds all the visited nodes in the sequence
        PlaceholderMap<NodeType, SearchNode> visitedNode = new PlaceholderMap<>();

        // sets starting node as the currentNode node
        Node currentNodeNode = nodes.get(start);

        // turns currentNode Node into a SearchNode object to formulate the starting
        // Node with a cost/distance of 0,
        // no predecessor because its the first and adds it to the priority queue
        SearchNode startNode = new SearchNode(currentNodeNode, 0, null);
        queue.add(startNode);

        // calls shortestPathHelper method to traverse the graph and compute the
        // shortest path
        // outputted node of this call becomes the finalNode
        SearchNode finalNode = shortestPathHelper(end, queue, visitedNode);

        // check if the final node returned by shortestPathHelper is valid
        if (finalNode.node != nodes.get(end) || !nodes.containsKey(finalNode.node.data)) {
            throw new NoSuchElementException("start/end node doesnt exist");
        }

        // returning final node in the shortest path
        return finalNode;
    }

    /**
     * This is a helper method that computes the shortest path between the start and
     * end nodes.
     * It uses a priority queue and a placeholder map to keep track of visitedNode
     * nodes.
     * 
     * @param end         the end node that the shortest path is calculated to
     * @param queue       the priority queue that stores SearchNode objects
     * @param visitedNode the placeholder map that keeps track of all SearchNode
     *                    objects that have already been visited
     * @return a SearchNode object that represents the final node in the shortest
     *         path from start to end
     * @throws NoSuchElementException if the queue is empty or start or end node
     *                                does not exist
     */
    private SearchNode shortestPathHelper(NodeType end, PriorityQueue<SearchNode> queue,
            PlaceholderMap<NodeType, SearchNode> visitedNode) {

        // checks if queue is empty and throws exception, meaning path not found
        if (queue.isEmpty()) {
            throw new NoSuchElementException("Start or end node does not exist");
        }

        // sets first node in queue as currentNode node
        Node currentNodeNode = queue.peek().node;

        // if currentNode node is at its destination, that node is returned
        if (currentNodeNode == nodes.get(end)) {
            return queue.peek();
        }

        // loop through edges leading out of the currentNodeNode node and transforms
        // their costs to doubles to be used for adding up costs values
        for (Edge edge : currentNodeNode.edgesLeaving) {
            double cost = edge.data.doubleValue();

            // check if the edge's successor node has already been visited
            if (visitedNode.containsKey(edge.successor.data)) {
                // check if the currentNodeNode path cost is lower than the cost of the
                // previously visitedNode node (then it would not be visited)
                if (queue.peek().cost + cost < visitedNode.get(edge.successor.data).cost) {
                    // create a new SearchNode object for the successor node with the updated cost
                    // and parent and adds to queue
                    SearchNode searchNode = new SearchNode(edge.successor, queue.peek().cost + cost, queue.peek());
                    visitedNode.remove(edge.successor.data);
                    visitedNode.put(edge.successor.data, searchNode);
                    queue.add(searchNode);

                } else {
                    // skip if currentNodeNode path cost is higher than the previously visited node
                    // (meaning it has been visited)
                    continue;
                }
            } else {
                // if the successor node has not been visitedNode yet, create a new SearchNode
                // object and add it to the queue and visited placeholder map
                SearchNode searchNode = new SearchNode(edge.successor, queue.peek().cost + cost, queue.peek());
                queue.add(searchNode);
                visitedNode.put(edge.successor.data, searchNode);
            }
        }

        // removes node off queue after all neighbor edges have been gone through and
        // recursively calls the method to get the next
        queue.remove();
        shortestPathHelper(end, queue, visitedNode);

        // returns first node in queue
        return queue.peek();
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // new list to store the shortest path nodes
        List<NodeType> shortestPath = new ArrayList<NodeType>();

        // computing shortest path node
        SearchNode currentNode = computeShortestPath(start, end);

        // add end node to beginning of path list
        shortestPath.add(0, currentNode.node.data);

        // while currentNode node has a predecessor, add its predecessor to beginning of
        // path list and set currentNode node
        // to its predecessor
        while (currentNode.predecessor != null) {
            shortestPath.add(0, currentNode.predecessor.node.data);
            currentNode = currentNode.predecessor;
        }
        // return shortest path list
        return shortestPath;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // computing shortest path node
        SearchNode currentNode = computeShortestPath(start, end);

        return currentNode.cost;
    }

    // /**
    //  * This method tests that the computeShortestPath() method returns
    //  * a searchNode with the correct shortest path, from gary's lecture
    //  * 
    //  * The start node being tested is D, the end node being tested is I
    //  */
    // @Test
    // public void test1() {

    //     // PlaceholderMap<String, Node> placeholderMap = new PlaceholderMap<>();
    //     DijkstraGraph<String, Integer> djikstra = new DijkstraGraph<>(new PlaceholderMap<>());

    //     // adding all the nodes in graph
    //     djikstra.insertNode("A");
    //     djikstra.insertNode("B");
    //     djikstra.insertNode("D");
    //     djikstra.insertNode("E");
    //     djikstra.insertNode("F");
    //     djikstra.insertNode("G");
    //     djikstra.insertNode("H");
    //     djikstra.insertNode("I");
    //     djikstra.insertNode("L");
    //     djikstra.insertNode("M");

    //     // adding all edges between each node in the graph
    //     djikstra.insertEdge("A", "B", 1);
    //     djikstra.insertEdge("A", "M", 5);
    //     djikstra.insertEdge("A", "H", 8);
    //     djikstra.insertEdge("B", "M", 3);
    //     djikstra.insertEdge("B", "E", 10);
    //     djikstra.insertEdge("D", "A", 7);
    //     djikstra.insertEdge("F", "G", 9);
    //     djikstra.insertEdge("G", "L", 7);
    //     djikstra.insertEdge("H", "B", 6);
    //     djikstra.insertEdge("H", "I", 2);
    //     djikstra.insertEdge("I", "H", 2);
    //     djikstra.insertEdge("I", "D", 1);
    //     djikstra.insertEdge("I", "L", 5);
    //     djikstra.insertEdge("M", "E", 3);
    //     djikstra.insertEdge("M", "F", 4);

    //     // Testing that the end node is correct, as well as the predecessors of the
    //     // returned end node
    //     assertEquals("I", djikstra.computeShortestPath("D", "I").node.data, "The predecessor is not correct");
    //     assertEquals("H", djikstra.computeShortestPath("D", "I").predecessor.node.data,
    //             "The predecessor is not correct");
    //     assertEquals("A", djikstra.computeShortestPath("D", "I").predecessor.predecessor.node.data,
    //             "The predecessor is not correct");
    //     assertEquals("D", djikstra.computeShortestPath("D", "I").predecessor.predecessor.predecessor.node.data,
    //             "The predecessor is not correct");

    // }

    // /**
    //  * This method tests that the shortestPathData() method returns
    //  * the actual shortest path between two nodes in the djikstra graph.
    //  * 
    //  * The start node being tested is D, the end node being tested is I
    //  */
    // @Test
    // public void test2() {
    //     DijkstraGraph<String, Integer> djikstra = new DijkstraGraph<>(new PlaceholderMap<>());

    //     // adding all the nodes in graph
    //     djikstra.insertNode("A");
    //     djikstra.insertNode("B");
    //     djikstra.insertNode("D");
    //     djikstra.insertNode("E");
    //     djikstra.insertNode("F");
    //     djikstra.insertNode("G");
    //     djikstra.insertNode("H");
    //     djikstra.insertNode("I");
    //     djikstra.insertNode("L");
    //     djikstra.insertNode("M");

    //     // adding all edges between each node in the graph
    //     djikstra.insertEdge("A", "B", 1);
    //     djikstra.insertEdge("A", "M", 5);
    //     djikstra.insertEdge("A", "H", 8);
    //     djikstra.insertEdge("B", "M", 3);
    //     djikstra.insertEdge("B", "E", 10);
    //     djikstra.insertEdge("D", "A", 7);
    //     djikstra.insertEdge("F", "G", 9);
    //     djikstra.insertEdge("G", "L", 7);
    //     djikstra.insertEdge("H", "B", 6);
    //     djikstra.insertEdge("H", "I", 2);
    //     djikstra.insertEdge("I", "H", 2);
    //     djikstra.insertEdge("I", "D", 1);
    //     djikstra.insertEdge("I", "L", 5);
    //     djikstra.insertEdge("M", "E", 3);
    //     djikstra.insertEdge("M", "F", 4);

    //     List<String> shortestPath = new ArrayList<String>();
    //     shortestPath.add("D");
    //     shortestPath.add("A");
    //     shortestPath.add("H");
    //     shortestPath.add("I");

    //     assertEquals(shortestPath, djikstra.shortestPathData("D", "I"), "The shortest path was not as expected");

    // }

    // /**
    //  * This method tests that the shortestPathCost() method returns
    //  * the cost of the shortest path between two nodes in the djikstra graph.
    //  * 
    //  * The start node being tested is D, the end node being tested is I
    //  */
    // @Test
    // public void test3() {
    //     // Create an instance of DijkstraGraph using the PlaceholderMap
    //     DijkstraGraph<String, Integer> djikstra = new DijkstraGraph<>(new PlaceholderMap<>());

    //     // gary lecture example graph
    //     // adding all the nodes in graph
    //     djikstra.insertNode("A");
    //     djikstra.insertNode("B");
    //     djikstra.insertNode("D");
    //     djikstra.insertNode("E");
    //     djikstra.insertNode("F");
    //     djikstra.insertNode("G");
    //     djikstra.insertNode("H");
    //     djikstra.insertNode("I");
    //     djikstra.insertNode("L");
    //     djikstra.insertNode("M");

    //     // adding all edges between each node in the graph
    //     djikstra.insertEdge("A", "B", 1);
    //     djikstra.insertEdge("A", "M", 5);
    //     djikstra.insertEdge("A", "H", 8);
    //     djikstra.insertEdge("B", "M", 3);
    //     djikstra.insertEdge("B", "E", 10);
    //     djikstra.insertEdge("D", "A", 7);
    //     djikstra.insertEdge("F", "G", 9);
    //     djikstra.insertEdge("G", "L", 7);
    //     djikstra.insertEdge("H", "B", 6);
    //     djikstra.insertEdge("H", "I", 2);
    //     djikstra.insertEdge("I", "H", 2);
    //     djikstra.insertEdge("I", "D", 1);
    //     djikstra.insertEdge("I", "L", 5);
    //     djikstra.insertEdge("M", "E", 3);
    //     djikstra.insertEdge("M", "F", 4);
    //     assertEquals(17.0, djikstra.shortestPathCost("D", "I"), "The shortest path's cost was not as expected");
    // }

    // /**
    //  * This method tests that the shortestPathCost() method returns
    //  * the cost of the shortest path between two nodes in the djikstra graph.
    //  * 
    //  */
    // @Test
    // public void test4() {
    //     DijkstraGraph<String, Integer> djikstra = new DijkstraGraph<>(new PlaceholderMap<>());

    //     // gary lecture example graph
    //     // adding all the nodes in graph
    //     djikstra.insertNode("A");
    //     djikstra.insertNode("B");
    //     djikstra.insertNode("D");
    //     djikstra.insertNode("E");
    //     djikstra.insertNode("F");
    //     djikstra.insertNode("G");
    //     djikstra.insertNode("H");
    //     djikstra.insertNode("I");
    //     djikstra.insertNode("L");
    //     djikstra.insertNode("M");
    //     // adding new node that has no edges to anything
    //     djikstra.insertNode("N");

    //     // adding all edges between each node in the graph
    //     djikstra.insertEdge("A", "B", 1);
    //     djikstra.insertEdge("A", "M", 5);
    //     djikstra.insertEdge("A", "H", 8);
    //     djikstra.insertEdge("B", "M", 3);
    //     djikstra.insertEdge("B", "E", 10);
    //     djikstra.insertEdge("D", "A", 7);
    //     djikstra.insertEdge("F", "G", 9);
    //     djikstra.insertEdge("G", "L", 7);
    //     djikstra.insertEdge("H", "B", 6);
    //     djikstra.insertEdge("H", "I", 2);
    //     djikstra.insertEdge("I", "H", 2);
    //     djikstra.insertEdge("I", "D", 1);
    //     djikstra.insertEdge("I", "L", 5);
    //     djikstra.insertEdge("M", "E", 3);
    //     djikstra.insertEdge("M", "F", 4);

    //     // trying to find paths for edges that don't exist between N and any other node
    //     Exception e1 = assertThrows(NoSuchElementException.class, () -> {
    //         djikstra.shortestPathData("H", "N");
    //     });

    //     assertEquals("Start or end node does not exist", e1.getMessage());

    //     // trying to find paths for edges that don't exist between N and any other node
    //     Exception e2 = assertThrows(NoSuchElementException.class, () -> {
    //         djikstra.shortestPathData("H", "N");
    //     });

    //     assertEquals("Start or end node does not exist", e2.getMessage());

    //     // trying to find paths for edges that don't exist between N and any other node
    //     Exception e3 = assertThrows(NoSuchElementException.class, () -> {
    //         djikstra.shortestPathCost("H", "N");
    //     });

    //     assertEquals("Start or end node does not exist", e3.getMessage());

    // }
}
