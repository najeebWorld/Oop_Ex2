package api;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import java.util.*;

public class algo implements DirectedWeightedGraphAlgorithms {
    Graph myGraph;
    int index;
    double [] arr  ;

    public algo(String jsonName) {
        try {
            myGraph = new Graph(jsonName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public algo(Graph gr1) {
        this.myGraph = gr1;
        this.index = 0;
        this.arr = new double[this.myGraph.nodeSize()];
        Iterator<NodeData> al = gr1.nodeIter();
//        this.ShortestPath = new HashMap<Integer, Double[]>();
//        while (al.hasNext()) {
//            this.ShortestPath.put(al.next().getKey(), new Double[gr1.nodeSize()]);
//        }

    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.arr =new double[g.nodeSize()];
        Iterator<NodeData> al = g.nodeIter();
        while (al.hasNext()) {
            NodeData curr = al.next();
            this.myGraph.addNode(curr);
        }
        Iterator<EdgeData> ag = g.edgeIter();
        while (ag.hasNext()){
            EdgeData curr = ag.next();
            this.myGraph.connect(curr.getSrc(),curr.getDest(),curr.getWeight());
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

            for (int j = 0; j < this.myGraph.nodeSize(); j++) {
                if (i == j) {
                    continue;
                }
                if (al.get(j) == -1) {
                    return false;
                }
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
                if (nod.get(EDGE.getDest()) > nod.get(v.getKey()) + EDGE.getWeight()) {
                    nod.put(EDGE.getDest(), nod.get(v.getKey()) + EDGE.getWeight());
                    Short.add(myGraph.getNode(EDGE.getDest()));
                }
            }

        }
        return nod;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer, Double> weight = shortpath(src);
        //  Iterator<Integer> edge_to_dest = graph.toMe().get(dest).values().iterator();
        List<NodeData> return_node = new ArrayList<>();
        NodeData n = myGraph.getNode(src);
        return_node.add(n);
        Geo X = new Geo(1, 1, 2);
        n = new Vertex(-1, X);
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
                if (min > weight.get(node)) {
                    min = weight.get(node);
                    what_in = node;
                }

            }
            dest = what_in;
            n = myGraph.getNode(what_in);
            return_node.add(n);
        }
        Collections.reverse(return_node);
        return return_node;
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
                if (al.get(j) > maxValue) {
                    maxValue = al.get(j);

                }
            }
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
            while (i < cities.size()) {
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
        return false;
    }

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
}