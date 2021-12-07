package api;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import java.io.PrintWriter;
import java.util.*;

public class algo implements DirectedWeightedGraphAlgorithms {
    Graph myGraph;//the graph that i work on.
   // int index;
    double [] arr  ;//save the sortest wheight.

    public algo(String jsonName) {
        try {
            myGraph = new Graph(jsonName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public algo(Graph gr1) {
        this.myGraph = gr1;

        this.arr = new double[this.myGraph.nodeSize()];//the size of array is nodesize
        Iterator<NodeData> al = gr1.nodeIter();//making iterator of NodeData on gr1(gragh)


    }

    public algo() {
        this.myGraph = new Graph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
     //   this.arr =new double[g.nodeSize()];
        Iterator<NodeData> al = g.nodeIter();//making iterator for g.
        while (al.hasNext()) {//while loop on my iterator.
            NodeData curr = al.next();
            this.myGraph.addNode(curr);//adding to my graph node by node
        }
        Iterator<EdgeData> ag = g.edgeIter();
        while (ag.hasNext()){
            EdgeData curr = ag.next();
            this.myGraph.connect(curr.getSrc(),curr.getDest(),curr.getWeight());
            //adding to myGraph edge by edge.
        }

    }


    @Override
    public DirectedWeightedGraph getGraph() {
        return this.myGraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Graph g = new Graph();
        Iterator<NodeData> a = myGraph.nodeIter();
        while (a.hasNext()) {
            g.addNode(a.next());
        }
        Iterator<EdgeData> b = myGraph.edgeIter();
        while (b.hasNext()) {
            g.connect(b.next().getSrc(), b.next().getDest(), b.next().getWeight());
        }


        return g;
    }

    @Override
    public boolean isConnected() {
        for (int i = 0; i < this.myGraph.nodeSize() ; i++) {
            HashMap<Integer,Double> al = shortpath(i);
            //Iterator <EdgeData> iter =
//must add on iter;
            for (int j = 0; j < this.myGraph.nodeSize(); j++) {
                if (i == j) {
                    continue;
                }
                if(al.get(j)!= null){
                    if (al.get(j) == -1) {
                       return false;
                }}
            }

        }
        return true;
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        HashMap<Integer, Double> nod = shortpath(src);

        return nod.get(dest);
//        Queue<NodeData> Short = new ArrayDeque<>();
//        Short.add(myGraph.getNode(src));
//        HashMap<Integer,Double> nod = new HashMap<Integer,Double> ();
//        Iterator<NodeData> e = myGraph.nodeIter();
//        for(int i =0 ; i< nod.size();i++ )
//            nod.put(e.next().getKey(), Double.MAX_VALUE);
//        nod.put(src,0.0);
//
//        while(!Short.isEmpty()){
//            NodeData v =  Short.poll();
//            Iterator <EdgeData> a = this.myGraph.edgeIter(v.getKey());
//            while (a.hasNext()){
//                EdgeData EDGE = a.next();
//                if(nod.get(EDGE.getDest()) > nod.get(v.getKey())+ EDGE.getWeight()) {
//                    nod.put(EDGE.getDest(), nod.get(v.getKey()) + EDGE.getWeight());
//                    Short.add(myGraph.getNode(EDGE.getDest()));
//                }
//            }
//
//        }
//        for(Integer i : nod.keySet()){
//            if(nod.get(i)== Double.MAX_VALUE)
//                nod.put(i, (double) -1);
//        }
//        return nod.get(dest);
//
//    }
//
    }


    public HashMap<Integer, Double> shortpath(int src) {

        Queue<NodeData> Short = new ArrayDeque<>();
        Short.add(myGraph.getNode(src));
        HashMap<Integer, Double> nod = new HashMap<Integer, Double>();
        Iterator<NodeData> e = myGraph.nodeIter();
        for (int i = 0; i < nod.size(); i++) {
            if (e.next().getKey() != src)
                nod.put(e.next().getKey(), Double.MAX_VALUE);
        }
        nod.put(src, 0.0);

        while (!Short.isEmpty()) {
            NodeData v = Short.poll();
            Iterator<EdgeData> a = myGraph.edgeIter(src);
            while (a.hasNext()) {
                EdgeData EDGE = a.next();
                if(nod.get(EDGE.getDest())!= null ){
                    if (nod.get(EDGE.getDest()) > nod.get(v.getKey()) + EDGE.getWeight()) {
                        nod.put(EDGE.getDest(), nod.get(v.getKey()) + EDGE.getWeight());
                        Short.add(myGraph.getNode(EDGE.getDest()));
                }}
            }

        }
        return nod;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer, Double> weight = shortpath(src);
        List<NodeData> nod = new ArrayList<>();
        NodeData n = myGraph.getNode(src);
        nod.add(n);
//        Geo X = new Geo(1, 1, 2);
//        n = new Vertex(-1, X);
//        System.out.println(n);
        while (n.getKey() != src) {

            double min = Double.MAX_VALUE;
            int what_in = Integer.MAX_VALUE;
            Iterator<Integer> edge_to_dest = myGraph.in.get(dest).values().iterator();
            while (edge_to_dest.hasNext()) {
                int node = edge_to_dest.next();
                if (node == src) {
                    what_in = src;
                    break;
                }
                if(weight.get(node) != null){
                  if (min > weight.get(node)) {
                    min = weight.get(node);
                    what_in = node;
                }}

            }
            dest = what_in;
            n = myGraph.getNode(what_in);
            nod.add(n);
        }
        Collections.reverse(nod);
        return nod;
    }


    @Override
    public NodeData center() {
        if (isConnected() == false) {
            return null;
        }
        double min = Double.MAX_VALUE;
        NodeData nod = null;
        for (int i = 0; i < this.myGraph.nodeSize() ; i++) {
            HashMap<Integer,Double> al = shortpath(i);

            double maxValue = 0;
            for (int j = 0; j < this.myGraph.nodeSize(); j++) {
                if(al.get(j)!= null){
                  if (al.get(j) > maxValue) {
                    maxValue = al.get(j);

                }
            }}
            if (maxValue < min) {
                min = maxValue;
                nod = this.myGraph.getNode(i);
            }
        }
        return nod;


    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities.isEmpty()) {
            return null;
        }
        List<NodeData> na = new ArrayList<>();
        int index = 0;
        NodeData nop = cities.get(index);
        na.add(nop);
        cities.remove(nop);
        while (cities.size() > 0) {
            double minIndex = Double.MAX_VALUE;
            HashMap<Integer, Double> shortOne = shortpath(nop.getKey());
            int i = 0;
            while (i < cities.size() && shortOne.get(cities.get(i)) != null) {
                if (shortOne.get(cities.get(i).getKey()) < minIndex) {
                    minIndex = shortOne.get(cities.get(i).getKey());
                    index = i;
                }
                i++;
            }
            nop = cities.get(index);
            na.add(nop);
            cities.remove(nop);
        }
        return na;
    }
//        if(cities.isEmpty())
//             return null;
//        List<NodeData> nod = cities;
//
//        Iterator<NodeData> na = nod.listIterator();
//        while(na.hasNext()){
//            NodeData b = na.next();
//            HashMap <Integer,Double> a = shortpath(b.getKey());
//
//            for(Integer i : )


    @Override
    public boolean save(String file) {
        try {
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            Map m;
            Iterator<EdgeData> iterator = this.myGraph.edgeIter();
            while (iterator.hasNext()) {
                m = new LinkedHashMap(3);
                EdgeData e = iterator.next();
                m.put("src", e.getSrc());
                m.put("w", e.getWeight());
                m.put("dest", e.getDest());
                ja.add(m);
            }
            jo.put("Edges", ja);
            ja = new JSONArray();
            Iterator<NodeData> iterator1 = this.myGraph.nodeIter();
            while (iterator1.hasNext()) {
                m = new LinkedHashMap(2);
                NodeData n = iterator1.next();
                m.put("pos", n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z());
                m.put("id", n.getKey());
                ja.add(m);
            }
            jo.put("Nodes", ja);
            PrintWriter pw = new PrintWriter(file);
            pw.write(jo.toString());
            pw.flush();
            pw.close();
            return true;
        } catch (Exception e) {
            return false;
    }}

    @Override
    public boolean load(String file) {

        try {
            DirectedWeightedGraph newGraph = new Graph(file);
            myGraph = (Graph) newGraph;

        } catch (Exception e) {
            return false;
        }
        return true;
    }



//    public HashMap<List<Integer>, Double> Floyd_Warshall (){
//
//        HashMap<List<Integer>, Double> mat_rep=new HashMap<List<Integer>, Double>();
//        Vertex n1=null;
//        Vertex n2=null;
//        Vertex n3=null;
//        //filling in the initial values in the hashmap
//        Iterator<NodeData> it1 = myGraph.nodeIter();
//        while(it1.hasNext()){
//            n1=(Vertex)it1.next();
//            Iterator<NodeData> it2 = myGraph.nodeIter();
//            while(it2.hasNext()){
//                n2=(Vertex)it2.next();
//                List<Integer> l1=new ArrayList<Integer>();
//                l1.add(n1.getKey());
//                l1.add(n2.getKey());
//                if(this.myGraph.Edges.get(l1)!=null){
//                    mat_rep.put(l1,this.myGraph.Edges.get(l1).);
//                }
//                else{
//                    if(l1.get(0)==l1.get(1)){
//                        mat_rep.put(l1,0.0);
//                    }
//                    else {
//                        mat_rep.put(l1, Double.MAX_VALUE);
//                    }
//                }
//            }
//        }
//
//        double w1=0;
//        double w2=0;
//        double w3=0;
//
//        Iterator<NodeData> iter1 = myGraph.nodeIter();  //k
//        while (iter1.hasNext()) {
//            n1 = (Node) iter1.next();
//            Iterator<NodeData> iter2 = myGraph.nodeIter();  //i
//            while (iter2.hasNext()) {
//                n2 = (Node) iter2.next();
//                if (n1.getKey() != n2.getKey()) { // dont want the same ones
//                    Iterator<NodeData> iter3 = myGraph.nodeIter();  //j
//                    while (iter3.hasNext()) {
//                        n3 = (Node) iter3.next();
//                        if (n3.getKey() != n1.getKey() && n3.getKey() != n2.getKey()) { // dont want the same ones
//                            List<Integer> l1 = new ArrayList<Integer>(); // l1= ij
//                            l1.add(n2.getKey());
//                            l1.add(n3.getKey());
//
//                            List<Integer> l2 = new ArrayList<Integer>(); // l2=ik
//                            l2.add(n2.getKey());
//                            l2.add(n1.getKey());
//
//                            List<Integer> l3 = new ArrayList<Integer>(); // l3 = kj
//                            l3.add(n1.getKey());
//                            l3.add(n3.getKey());
//
//                            w1 = mat_rep.get(l1);
//
//                            w2 = mat_rep.get(l2);
//
//                            w3 = mat_rep.get(l3);
//
//                            if (w1 >= w2 + w3) {
//                                mat_rep.put(l1, w2 + w3);
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return mat_rep;
//    }
}