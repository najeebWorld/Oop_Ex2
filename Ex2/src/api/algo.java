package api;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import java.util.*;

public class algo implements DirectedWeightedGraphAlgorithms {
    Graph myGraph;

    public algo(String jsonName) {
        try {
            myGraph=new Graph(jsonName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.myGraph= (Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.myGraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Graph g =new Graph();
        Iterator<NodeData> a = myGraph.nodeIter();
        while(a.hasNext()){
            g.addNode(a.next());
        }
        Iterator<EdgeData> b= myGraph.edgeIter();
        while(b.hasNext()){
            g.connect(b.next().getSrc(),b.next().getDest(),b.next().getWeight());
        }


        return g;
    }

    @Override
    public boolean isConnected() {
        Queue<NodeData> Short = new ArrayDeque<>();
        Iterator<NodeData> a = myGraph.nodeIter();
        Vertex nodi=null;
        if(a.hasNext()) {
            nodi = (Vertex) a.next();
        }
        tagchild(nodi);
        Iterator<NodeData> b = myGraph.nodeIter();
        while(b.hasNext()){
            if(b.next().getTag()!= 1)
                return  false;
        }
      Graph newG =   flipGraph(this.myGraph);
        Iterator<NodeData> c = myGraph.nodeIter();
        Vertex nody=null;
        if(c.hasNext()) {
            nody = (Vertex) c.next();
        }
        tagchild(nody);
        Iterator<NodeData> d = myGraph.nodeIter();
        while(d.hasNext()){
            if(d.next().getTag()!= 1)
                return  false;
        }
        return true;
    }
    public void tagchild(Vertex v){
        if (v.nodes.size()!=0 || v.getTag()==1){
            v.tag=1;
            int key = v.getKey();
            Iterator < EdgeData> a = myGraph.edgeIter(key);
            while(a.hasNext()){
                Vertex v1= (Vertex) a.next();
                tagchild(v1);
            }
        }
    }

    public Graph flipGraph( Graph myGraph){
        Graph newGraph=null;
        Iterator<NodeData> a = myGraph.nodeIter();
        while(a.hasNext()){
            newGraph.addNode(a.next());
        }
        Iterator<EdgeData> b= myGraph.edgeIter();
        while(b.hasNext()){
            newGraph.connect(b.next().getDest(),b.next().getSrc(),b.next().getWeight());
        }
        return newGraph;
    }

    @Override
    public double shortestPathDist(int src, int dest) {

        Queue<NodeData> Short = new ArrayDeque<>();
        Short.add(myGraph.getNode(src));
        HashMap<Integer,Double> nod = new HashMap<Integer,Double> ();
        Iterator<NodeData> e = myGraph.nodeIter();
        for(int i =0 ; i< nod.size();i++ )
            nod.put(e.next().getKey(), Double.MAX_VALUE);
        nod.put(src,0.0);

        while(!Short.isEmpty()){
            NodeData v =  Short.poll();
            Iterator <EdgeData> a = myGraph.edgeIter(src);
            while (a.hasNext()){
                EdgeData EDGE = a.next();
                if(nod.get(EDGE.getDest()) > nod.get(v.getKey())+ EDGE.getWeight()) {
                    nod.put(EDGE.getDest(), nod.get(v.getKey()) + EDGE.getWeight());
                    Short.add(myGraph.getNode(EDGE.getDest()));
                }
            }

        }
        return nod.get(dest);
    }//must return the smallest way by weight

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
