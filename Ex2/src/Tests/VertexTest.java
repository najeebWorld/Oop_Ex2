package Tests;

import api.Edge;
import api.Geo;
import api.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {
    Geo a1 = new Geo (19,20,22);
    Geo a2 = new Geo(1,2,3);
    Geo a3 = new Geo(5,6,7);
    Geo a4 = new Geo(8,9,10);
    Vertex b1= new Vertex(2,a4);
    Vertex b2 = new Vertex(3,a3);
    Vertex b3 = new Vertex(4,a2);
    Vertex b4 = new Vertex(5,a1);

    @Test
    void getKey() {
        assertEquals(2,b1.getKey());
        assertEquals(3,b2.getKey());
        assertEquals(4,b3.getKey());
        assertEquals(5,b4.getKey());

    }

    @Test
    void getLocation() {
        assertEquals(8, b1.getLocation().x());
        assertEquals(9, b1.getLocation().y());
        assertEquals(6, b2.getLocation().y());
        assertEquals(3,b3.getLocation().z());
    }

    @Test
    void setLocation() {

        Geo P6 = new Geo(3,4,5);
        b2.setLocation(P6);
        assertEquals(3, b2.getLocation().x());
        assertEquals(4,b2.getLocation().y());
    }

    @Test
    void getWeight() {
        assertEquals(0,b1.getWeight());
        assertEquals(0,b2.getWeight());
        assertEquals(0,b3.getWeight());
        assertEquals(0,b4.getWeight());
    }

    @Test
    void setWeight() {
        b1.setWeight(4);
        b2.setWeight(5);
        b3.setWeight(6);
        b4.setWeight(7);
        assertEquals(4,b1.getWeight());
        assertEquals(5,b2.getWeight());
        assertEquals(6,b3.getWeight());
        assertEquals(7,b4.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals(null, b1.getInfo());
        assertEquals(null,b2.getInfo());
        assertEquals(null,b3.getInfo());
        assertEquals(null,b4.getInfo());
    }

    @Test
    void setInfo() {
        b1.setInfo("3");
        b2.setInfo("4");
        b3.setInfo("5");
        b4.setInfo("6");
        assertEquals("3",b1.getInfo());
        assertEquals("4",b2.getInfo());
        assertEquals("5",b3.getInfo());
        assertEquals("6",b4.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0,b1.getTag());
        assertEquals(0,b2.getTag());
        assertEquals(0,b3.getTag());
        assertEquals(0,b4.getTag());
    }

    @Test
    void setTag() {
        b1.setTag(3);
        b2.setTag(4);
        b3.setTag(5);
        b4.setTag(6);
        assertEquals(3,b1.getTag());
        assertEquals(4,b2.getTag());
        assertEquals(5,b3.getTag());
        assertEquals(6,b4.getTag());
    }



    @Test
    void removeFromList() {
        Edge e1 = new Edge(1,2,0);
        Edge e2 = new Edge(2,1,0);
        Edge e3 = new Edge(3,4,5);
        b1.addToMeList(e2);
        b4.addToMeList(e3);
        b2.addToMeList(e1);
        b1.removeFromList(2);
        b2.removeFromList(1);
        b4.removeFromList(3);
        assertEquals(null,b1.getnodes().get(2));
        assertEquals(null,b2.getnodes().get(1));
        assertEquals(null,b4.getnodes().get(3));
    }

    @Test
    void getnodes() {
    }
}