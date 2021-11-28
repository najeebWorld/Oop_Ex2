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

    HashMap<Integer, Vertex> Nodes = new HashMap<Integer, Vertex>();
    HashMap<Double,Edge> Edges = new HashMap<Double, Edge>();//must do it(x,y)


    public Graph(String jsonName) throws ParseException {
        String filename = jsonName;
        try {
            JSONObject jsonObject = parseJSONFile(filename);
            JSONArray vertex=jsonObject.getJSONArray("Nodes");
            JSONArray edges=jsonObject.getJSONArray("Edges");
            saveJson(vertex,edges);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
        //must use the json file on and add the details in.

    public void saveJson(  JSONArray nodes,JSONArray edges) {

        for (int i =0; i< nodes.length();i++){
            Integer key = nodes.getJSONObject(i).getInt("id");
            String a =nodes.getJSONObject(i).getString("pos");
            Geo p = new Geo(a);
            Vertex b = new Vertex(key,p,0,0,"");
           this.Nodes.put(key,b);

        }
        for (int i =0 ; i< edges.length();i++){
            Integer src = edges.getJSONObject(i).getInt("src");
            Double w = edges.getJSONObject(i).getDouble("w");
            Integer dest = edges.getJSONObject(i).getInt("dest");
            Edge a = new Edge(src,dest,w);
            double key1 = d(src,dest);
            this.Edges.put(key1,a);

        }
    }
    private double d(int src,int dest){
       double a = (double)src;
        double b = (double)dest;
        while(b>=1){
           b = b/10;
        }
        return a+b;
    }
    @Override
    public NodeData getNode(int key) {

        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {

        return this.Edges.get(d(src,dest));
    }

    @Override
    public void addNode(NodeData n) {
        Integer key = n.getKey();
        this.Nodes.put(key,(Vertex) n);


    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge a = new Edge(src,dest,w);
        double key1 = d(src,dest);
        this.Edges.put(key1,a);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        HashMap<Integer,NodeData> a = (HashMap<Integer, NodeData>)this.Nodes.clone() ;
        Iterator<NodeData> iter = a.values().iterator();
        return iter;
    }


    @Override
    public Iterator<EdgeData> edgeIter() {
        HashMap<Integer,EdgeData> a = (HashMap<Integer, EdgeData>)this.Edges.clone() ;
        Iterator<EdgeData> iter = a.values().iterator();
        return iter;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return this.Nodes.remove(key);
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
