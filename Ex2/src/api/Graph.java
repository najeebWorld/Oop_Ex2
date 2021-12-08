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


public class Graph implements DirectedWeightedGraph {


    int mc;
    HashMap<Integer, Vertex> Nodes;
    HashMap<Integer, HashMap<Integer, EdgeData>> Edges;
    public HashMap<String, Edge> edg;// all edges that came for each node


    public Graph(String jsonName) throws ParseException {
        Nodes = new HashMap<>();
        Edges = new HashMap<>();
        edg= new HashMap<>();

        String filename = jsonName;
        try {
            JSONObject jsonObject = parseJSONFile(filename);
            JSONArray vertex = jsonObject.getJSONArray("Nodes");
            JSONArray edges = jsonObject.getJSONArray("Edges");
            saveJson(vertex, edges);

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mc = 0;


    }

    public Graph(Graph g) {
        HashMap<Integer, Vertex> Nodes = g.Nodes;
        HashMap<Integer, HashMap<Integer, EdgeData>> Edges = g.Edges;


    }

    public Graph() {
        this.Nodes = new HashMap<Integer, Vertex>();
        this.Edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.edg = new HashMap<>();
    }

    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
    //must use the json file on and add the details in.

    public void saveJson(JSONArray nodes, JSONArray edges) {

        for (int i = 0; i < nodes.length(); i++) {
            Integer key = nodes.getJSONObject(i).getInt("id");

            String a = nodes.getJSONObject(i).getString("pos");
            Geo p = new Geo(a);
            Vertex b = new Vertex(key, p, 0, 0, "");
            addNode(b);

        }
        for (int i = 0; i < edges.length(); i++) {
            Integer src = edges.getJSONObject(i).getInt("src");
            Double w = edges.getJSONObject(i).getDouble("w");
            Integer dest = edges.getJSONObject(i).getInt("dest");
            connect(src, dest, w);

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
        this.Nodes.put(key, (Vertex) n);
        this.Edges.put(n.getKey(), new HashMap<>());
        mc++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge a = new Edge(src, dest, w);
        this.edg.put(src+","+dest,a);
        this.Edges.get(src).put(dest, a);
        this.Nodes.get(src).nodes.put(src,dest);
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
//        HashMap<Integer, NodeData> a = (HashMap<Integer, NodeData>) this.Nodes.clone();
       // Iterator<NodeData> iter = this.Nodes.values().iterator();
        return new Iterator<NodeData>(){
            Iterator<Vertex> iter=Nodes.values().iterator();
            private int currentMc=mc;
            NodeData last=null;

            @Override
            public boolean hasNext() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                return iter.hasNext();
            }

            @Override
            public NodeData next() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                last=iter.next();
                return last;
            }

            @Override
            public void remove() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                if(last!=null){
                    removeNode(last.getKey());
                }
                Iterator.super.remove();
            }
        };
    }


    @Override
    public Iterator<EdgeData> edgeIter() {

        return new Iterator<EdgeData>() {
            Iterator<Edge>iter=edg.values().iterator();
            private int currentMc=mc;
            EdgeData last=null;

            @Override
            public boolean hasNext() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                last = iter.next();
                return last;
            }

            public void remove(){
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                if(last!=null){
                    removeEdge(last.getSrc(), last.getDest());
                    currentMc=mc;
                }
            }
        };

       /*
        ArrayList<EdgeData> nod = new ArrayList<>();
        for (Integer i : this.Nodes.keySet()) {
            Iterator<EdgeData> iter = edgeIter(i);
            while (iter.hasNext()) {
                nod.add(iter.next());
            }
        }

        return nod.iterator();
        */
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> iter = Edges.get(node_id).values().iterator();
            private int currentMc = mc;
            EdgeData last = null;

            @Override
            public boolean hasNext() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                return iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                last = iter.next();
                return last;
            }

            @Override
            public void remove() {
                if (currentMc != mc) {
                    throw new RuntimeException("you updated the graph while the iterater was running");
                }
                if (last !=null){
                    removeEdge(last.getSrc(), last.getDest());
                    currentMc = mc;
                }
            }
        };
    }

    @Override
    public NodeData removeNode(int key) {
        ArrayList<String> keys = new ArrayList<String>();
        for (String k: edg.keySet())
        {
            if(key==Integer.parseInt((k.split(","))[0])||key==Integer.parseInt((k.split(","))[1])) {
                keys.add(k);
            }
        }
        keys.forEach(k->this.edg.remove(k));
        Iterator<NodeData> it=nodeIter();
        while (it.hasNext()){
            Vertex v=(Vertex)it.next();
            Iterator<EdgeData> iter=edgeIter(v.getKey());
            while(iter.hasNext()){
                Edge e=(Edge)iter.next();
                if(e.getSrc()==key || e.getDest()==key){
                    v.nodes.remove(e);
                }
            }

        }
        Vertex ans=this.Nodes.remove(key);


//        for (Integer i : this.Edges.get(key).keySet()) {
//            in.get(i).remove(key);
//        }
//
//
//        for (Integer i : this.in.get(key).keySet()) {
//            Edges.get(i).remove(key);
//        }
//        NodeData n = Nodes.get(key);
//        Edges.remove(key);
//        Nodes.remove(key);
        mc++;
        return ans;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edg1 = this.Edges.get(src).get(dest);

        this.Edges.get(src).remove(dest);
        //this.edg.get(src+","+dest);
        this.edg.remove(src+","+dest);
        this.Nodes.get(src).getnodes().remove(src,dest);
        mc++;
        return edg1;

    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        int count = 0;
        for (Integer i : this.Edges.keySet()) {
            count += this.Edges.get(i).size();
        }

        return count;
    }

    @Override
    public int getMC() {
        return this.mc;
    }
}