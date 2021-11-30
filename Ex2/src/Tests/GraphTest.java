package Tests;

import api.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph gr = new Graph("C:\\Users\\Admin\\IdeaProjects\\Oop_Ex2\\Ex2\\data\\G1.json");

    GraphTest() throws ParseException {
    }






    @Test
    void getNode() {
        Geo p = new Geo(35.212111165456015,32.106235628571426,0.0);
        NodeData n = new Vertex(10,p);
        assertEquals(n.getKey(),gr.getNode(10).getKey());
        assertEquals(n.getLocation().x(),gr.getNode(10).getLocation().x());
    }

    @Test
    void getEdge() {
        //assertEquals(11,gr.getEdge(11,11).getSrc());
        assertEquals(1.4962204797190428,gr.getEdge(10,11).getWeight());
        assertEquals(null,gr.getEdge(16,14));
    }

    @Test
    void addNode() {
        Geo p = new Geo(35.212111165456015,32.106235628571426,0.0);
        NodeData n = new Vertex(18,p);
        gr.addNode(n);
        assertEquals(n.getLocation().x(),gr.getNode(18).getLocation().x());
    }

    @Test
    void connect() {
        gr.connect(16,14,8);
        assertEquals(16,gr.getEdge(16,14).getSrc());
    }

    @Test
    void nodeIter() {
        Iterator<NodeData> it1= gr.nodeIter();
        NodeData n1= it1.next();
        assertEquals(0, n1.getKey());
        assertEquals(35.19589389346247,n1.getLocation().x());
        NodeData n2= it1.next();
        assertEquals(1,n2.getKey());
        assertEquals(35.20319591121872,n2.getLocation().x());
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> it1= gr.edgeIter();
        EdgeData n1= it1.next();
        assertEquals(0, n1.getSrc());
        assertEquals(16,n1.getDest());
    }

    @Test
    void testEdgeIter() {
        Iterator<EdgeData> it1= gr.edgeIter(0);
        EdgeData n1= it1.next();
        assertEquals(0, n1.getSrc());
        assertEquals(16,n1.getDest());
    }

    @Test
    void removeNode() {
        assertEquals(5, gr.removeNode(5).getKey());
        assertEquals(35.20797194027441,gr.removeNode(6).getLocation().x());
    }

    @Test
    void removeEdge() {
        assertEquals(5,gr.removeEdge(4,5).getDest());
        assertEquals(null ,gr.removeEdge(4,5));
    }

    @Test
    void nodeSize() {
        assertEquals(17,gr.nodeSize());
        gr.removeNode(16);
        assertEquals(16,gr.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(36,gr.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(0,gr.getMC());
    }


}

