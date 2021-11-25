package api;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;


public class Graph implements DirectedWeightedGraph{
//    NodeData node;
//    NodeData node2;
//    EdgeData edge;

    HashMap<Integer, Node> Nodes = new HashMap<Integer, Node>();
    HashMap<Integer,Edge> Edges = new HashMap<Integer, Edge>();//must do it(x,y)

    public Graph(String jsonName) throws ParseException {
        String filename = jsonName;
        try {
            JSONObject jsonObject = parseJSONFile(filename);
            JSONArray vertex=jsonObject.getJSONArray("Nodes");
            JSONArray edges=jsonObject.getJSONArray("Edges");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }




        //must use the json file on and add the details in.


    @Override
    public NodeData getNode(int key) {

        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(NodeData n) {


    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
