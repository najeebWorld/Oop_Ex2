package api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph graph123 = generate();
    algo graph = new algo(graph123);


    @Test
    void init() {
        algo graphs1 = new algo();
        graphs1.init(graph123);
        Iterator<EdgeData> original = graph123.edgeIter();
        Iterator<EdgeData> initgraph123 = graphs1.getGraph().edgeIter();
        while (original.hasNext()){
            assertEquals(original.next().getWeight(),initgraph123.next().getWeight());
        }
    }

    @Test
    void getGraph() {
        Iterator<EdgeData> original = graph123.edgeIter();
        Iterator<EdgeData> initgraph123 = graph.getGraph().edgeIter();
        while (original.hasNext()){
            assertEquals(original.next().getWeight(),initgraph123.next().getWeight());
        }
    }

    @Test
    void copy() {
        Graph copy = (Graph) graph.copy();
        assertEquals(copy.edgeSize(),graph123.edgeSize());
        assertEquals(copy.nodeSize(), graph123.nodeSize());
        Iterator<NodeData> iterator = copy.nodeIter();
        Iterator<NodeData> iterator1 = graph123.nodeIter();
        while (iterator.hasNext()){
           assertEquals(iterator.next().getLocation(), iterator1.next().getLocation());
        }
        Iterator<EdgeData> iterator2 = copy.edgeIter();
        Iterator<EdgeData> iterator3 = graph123.edgeIter();
        while (iterator.hasNext()){
            assertEquals(iterator2.next().getSrc(),iterator3.next().getSrc());
        }
    }

    @Test
    void isConnected() {
        assertEquals(true, graph.isConnected());
         graph.getGraph().removeEdge(4,0);
        assertEquals(false, graph.isConnected());
        graph.getGraph().connect(6,0,0.954);
        boolean b=graph.isConnected();
       assertEquals(true, b);
//       algo A4=new algo("data\\1000Nodes.json");
//        assertEquals(true,A4.isConnected());
//        algo A5=new algo("data\\10000Nodes.json");
//        assertEquals(true,A5.isConnected());
//        //algo A6=new algo("data\\100000.json");
//        //assertEquals(true,A6.isConnected());
    }

    @Test
    void shortestPathDist() {
        assertEquals(graph.shortestPathDist(5,8), 5.9, 0.0001);
        assertEquals(graph.shortestPathDist(4,5),1.5, 0.0001);
        assertEquals(graph.shortestPathDist(5,7),4.7, 0.0001);
        graph.getGraph().connect(5,1,2.2);
        assertEquals(graph.shortestPathDist(7,1),2.5, 0.0001);
    }

    @Test
    void shortestPath() {

        HashMap<List<Integer>, List<Integer>> m= graph.Floyd_Warshall_list(graph123);
        List<NodeData> l= new ArrayList<NodeData>();
        l.add(this.graph123.Nodes.get(5));
        l.add(this.graph123.Nodes.get(9));
        l.add(this.graph123.Nodes.get(8));

        List<Integer> i=new ArrayList<Integer>();
        i.add(5);
        i.add(8);
        List<Integer> ans1=m.get(i);
        System.out.println(ans1);
        List<NodeData> ans=new ArrayList<NodeData>();
        for (int j=0;j<ans1.size();j++){
            ans.add(this.graph123.Nodes.get(ans1.get(j)));
        }
        assertEquals(l,ans);


        l.clear();
        l.add(this.graph123.Nodes.get(3));
        l.add(this.graph123.Nodes.get(2));
        l.add(this.graph123.Nodes.get(4));
        l.add(this.graph123.Nodes.get(6));
        i.clear();
        i.add(3);
        i.add(6);
        ans1.clear();
        ans1=m.get(i);
        System.out.println(ans1);
        ans.clear();
        for (int j=0;j<ans1.size();j++){
            ans.add(this.graph123.Nodes.get(ans1.get(j)));
        }
        assertEquals(l,ans);


    }

    @Test
    void center() {

        assertEquals(graph.center().getKey(),5);
        algo A1=new algo("data\\G1.json");
        assertEquals(8,A1.center().getKey());
        algo A2=new algo("data\\G2.json");
        assertEquals(0,A2.center().getKey());
        algo A3=new algo("data\\G3.json");
        assertEquals(40,A3.center().getKey());
//        algo A4=new algo("data\\1000Nodes.json");
//        assertEquals(0,A4.center().getKey());
//        algo A5=new algo("data\\10000Nodes.json");
//        assertEquals(0,A5.center().getKey());
//        algo A6=new algo("data\\100000.json");
//        assertEquals(0,A6.center().getKey());

    }

    @Test
    void tsp() {
        HashMap<List<Integer>, Double> m= graph.Floyd_Warshall(graph123);
        List<NodeData> group1 = new ArrayList<NodeData>();
        group1.add(graph123.Nodes.get(0));
        group1.add(graph123.Nodes.get(1));
        group1.add(graph123.Nodes.get(9));
        group1.add(graph123.Nodes.get(2));
        group1.add(graph123.Nodes.get(8));
        group1.add(graph123.Nodes.get(3));
        group1.add(graph123.Nodes.get(7));
        group1.add(graph123.Nodes.get(6));
        group1.add(graph123.Nodes.get(4));
        group1.add(graph123.Nodes.get(5));

        double biggest1 = 92.0;
        List <NodeData> ans1= graph.tsp(group1);
        double real1=0;
        for (int i=1;i<ans1.size();i++){
            List<Integer> l1=new ArrayList<Integer>();
            l1.add(ans1.get(i-1).getKey());
            l1.add(ans1.get(i).getKey());
            real1=real1+m.get(l1);
        }
        boolean b1=real1<=biggest1;
        assertTrue(b1);

        List<NodeData> group2 = new ArrayList<NodeData>();

        group2.add(graph123.Nodes.get(1));
        group2.add(graph123.Nodes.get(2));
        group2.add(graph123.Nodes.get(8));
        group2.add(graph123.Nodes.get(3));
        group2.add(graph123.Nodes.get(7));
        group2.add(graph123.Nodes.get(4));

        double biggest2 = 50.0;
        List <NodeData> ans2= graph.tsp(group2);
        double real2=0;
        for (int i=1;i<ans2.size();i++){
            List<Integer> l1=new ArrayList<Integer>();
            l1.add(ans1.get(i-1).getKey());
            l1.add(ans1.get(i).getKey());
            real2=real2+m.get(l1);
        }
        boolean b2=real2<=biggest2;
        assertTrue(b2);


    }


    @Test
    void save() {
        graph.save("data\\G1TEST.json");
        algo graph2 = new algo();
        graph2.load("data\\G1TEST.json");
        assertEquals(graph.getGraph().nodeSize(),graph2.getGraph().nodeSize());
    }

    @Test
    void load() {
        graph.save("data\\G1TEST.json");
        algo graph2 = new algo();
        graph2.load("data\\G1TEST.json");
        assertEquals(graph.getGraph().nodeSize(),graph2.getGraph().nodeSize());
    }

    public Graph generate(){
        Graph graph123 = new Graph();
        Vertex v0 = new Vertex(0, new Geo(2,10,0));
        Vertex v1 = new Vertex(1, new Geo(4,11,0));
        Vertex v2 = new Vertex(2, new Geo(6,12,0));
        Vertex v3 = new Vertex(3, new Geo(8,13,0));
        Vertex v4 = new Vertex(4, new Geo(3,7,0));
        Vertex v5 = new Vertex(5, new Geo(7,8,0));
        Vertex v6 = new Vertex(6, new Geo(2,4,0));
        Vertex v7 = new Vertex(7, new Geo(4,5,0));
        Vertex v8 = new Vertex(8, new Geo(6,6,0));
        Vertex v9 = new Vertex(9, new Geo(8,7,0));

        graph123.addNode(v1);
        graph123.addNode(v0);
        graph123.addNode(v2);
        graph123.addNode(v3);
        graph123.addNode(v4);
        graph123.addNode(v5);
        graph123.addNode(v6);
        graph123.addNode(v7);
        graph123.addNode(v8);
        graph123.addNode(v9);
        graph123.connect(0,1,1.5);
        graph123.connect(1,2,2.1);
        graph123.connect(3,2,8.2);
        graph123.connect(4,0,5.2);
        graph123.connect(4,1,4.9);
        graph123.connect(2,4,5.3);
        graph123.connect(4,5,6.3);
        graph123.connect(5,4,3.5);
        graph123.connect(5,2,1.1);
        graph123.connect(5,3,8.4);
        graph123.connect(4,6,6.3);
        graph123.connect(4,7,1.2);
        graph123.connect(7,5,0.3);
        graph123.connect(5,8,7.3);
        graph123.connect(5,9,3.5);
        graph123.connect(9,8,2.4);
        graph123.connect(8,7,0.2);
        graph123.connect(6,7,4.5);

        return graph123;




    }
}