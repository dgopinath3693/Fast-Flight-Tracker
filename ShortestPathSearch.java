import java.util.List;
import java.util.ArrayList;

public class ShortestPathSearch implements ShortestPathSearchInterface {

      List<String> path;
      List<Integer> segmentMiles = new ArrayList<Integer>();
      private int totalMiles;


       public ShortestPathSearch   (List<String> path, List<Integer> segmentMiles, int totalMiles) {
           this.path = path;
           this.segmentMiles= segmentMiles;
           this.totalMiles = totalMiles;
       }
       

      @Override
      public List<Integer> getSegmentMiles() {
        return this.segmentMiles;
      }


      @Override
      public int getTotalMiles() {
        return  totalMiles;
      }


      @Override
      public List<String> getRoute() {
        return this.path;
      }

}