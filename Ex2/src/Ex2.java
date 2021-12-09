import Gui.frameGui;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
//import Tests.algo;

import api.Graph;
import api.algo;
import org.json.simple.parser.ParseException;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        // ****** Add your code here ******
        DirectedWeightedGraphAlgorithms a= new algo();
        a.load(json_file);
        ans=a.getGraph();
          // ********************************
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;
        // ****** Add your code here ******
        ans=new algo(json_file);
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******

        frameGui myGui=new frameGui(alg);




        // ********************************
    }

    public static void main(String[] args) throws ParseException {
         //Graph graph=new Graph("Ex2/data/G2.json");
        //System.out.println("Ex2/data/G2.json".toString());
        runGUI("myyyjson.json");



//        algo g = new algo("C:\\Users\\Admin\\IdeaProjects\\Oop_Ex2\\Ex2\\data\\G1.json");
//        //g.load("C:\\Users\\User\\Downloads\\OOP_2021-main (1)\\OOP_2021-main\\Assignments\\Ex2\\data\\1000Nodes.json");
//        double t1 = System.currentTimeMillis();
//        g.isConnected();
//        double t2 = System.currentTimeMillis();
//        System.out.println(t2-t1);
//        g.center();
//        double t3 = System.currentTimeMillis();
//        System.out.println(t3-t1);
//
//        algo g2 = new algo("C:\\Users\\Admin\\IdeaProjects\\Oop_Ex2\\Ex2\\data\\G1.json");
//        //g2.load("C:\\Users\\User\\Downloads\\OOP_2021-main (1)\\OOP_2021-main\\Assignments\\Ex2\\data\\10000Nodes.json");
//        double t4 = System.currentTimeMillis();
//        g2.isConnected();
//        double t5 = System.currentTimeMillis();
//        System.out.println(t5-t4);
//        g2.center();
//        double t6 = System.currentTimeMillis();
//        System.out.println(t6-t4);
//

    }
}