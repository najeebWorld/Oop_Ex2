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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Graph  implements DirectedWeightedGraph{


    int mc;
    HashMap<Integer, Vertex> Nodes ;
    HashMap<Integer , HashMap< Integer,EdgeData> > Edges ;
   public  HashMap<Integer,HashMap<Integer,Integer>> in;// all edges that came for each node



    public Graph(String jsonName) throws ParseException {
        Nodes=new HashMap<>();
        Edges=new HashMap<>();
        in= new HashMap<>();

        String filename = jsonName;
        try {
            JSONObject jsonObject = parseJSONFile(filename);
            JSONArray vertex=jsonObject.getJSONArray("Nodes");
            JSONArray edges=jsonObject.getJSONArray("Edges");
            saveJson(vertex,edges);

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mc=0;


    }
    public Graph(Graph g){
        HashMap<Integer, Vertex> Nodes = g.Nodes;
        HashMap<Integer , HashMap< Integer,EdgeData> > Edges =g.Edges;


    }
    public Graph(){
        this.Nodes=new HashMap<Integer,Vertex>();
        this.Edges=new HashMap<Integer,HashMap< Integer,EdgeData>>();
        this.in=new HashMap<>();
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
            addNode(b);

        }
        for (int i =0 ; i< edges.length();i++){
            Integer src = edges.getJSONObject(i).getInt("src");
            Double w = edges.getJSONObject(i).getDouble("w");
            Integer dest = edges.getJSONObject(i).getInt("dest");
            connect(src,dest,w);

//            if(this.Edges.containsKey(src)){
//                this.Edges.get(src).put(dest,a);
//            }else{
//                this.Edges.put(src,new HashMap<>());
//                this.Edges.get(src).put(dest,a);
//            }



        }
    }

    @Override
    public NodeData getNode(int key) {

        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {

        return this.Edges.get(src).get(dest);
    }

    @Override
    public void addNode(NodeData n) {
        Integer key = n.getKey();
        this.Nodes.put(key,(Vertex) n);
        this.Edges.put(n.getKey(),new HashMap<>());

        this.in.put(n.getKey(),new HashMap<>());
        mc++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge a = new Edge(src,dest,w);
        this.in.get(dest).put(src,src);
        this.Edges.get(src).put(dest,a);
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        HashMap<Integer,NodeData> a = (HashMap<Integer, NodeData>)this.Nodes.clone() ;
        Iterator<NodeData> iter = a.values().iterator();
        return iter;
    }


    @Override
    public Iterator<EdgeData> edgeIter() {
        ArrayList<EdgeData> nod = new ArrayList<>();
        for(Integer i : this.Nodes.keySet()){
           Iterator <EdgeData> iter = edgeIter(i);
           while (iter.hasNext())
           {
               nod.add(iter.next());
           }
        }
        
        return nod.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {

        Iterator<EdgeData> iter = this.Edges.get(node_id).values().iterator();
        return iter;
    }

    @Override
    public NodeData removeNode(int key) {
        for (Integer i: this.Edges.get(key).keySet()){
            in.get(i).remove(key);
        }



        for (Integer i: this.in.get(key).keySet()) {
            Edges.get(i).remove(key);
        }
        NodeData n = Nodes.get(key);
        Edges.remove(key);
        Nodes.remove(key);
        mc++;
        return n;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edg = this.Edges.get(src).get(dest);

        this.Edges.get(src).remove(dest);
        this.in.get(dest).remove(src);
        mc++;
        return  edg;

    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        int count=0;
        for (Integer i:this.Edges.keySet()){
            count +=this.Edges.get(i).size();
        }

        return count;
    }

    @Override
    public int getMC() {
        return this.mc;
    }
}