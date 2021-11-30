package Tests;

import api.Edge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    Edge a1 = new Edge(2,3,4);
    Edge a2 = new Edge(2,2,0);
    Edge a3 = new Edge(4,4,5);

    @Test
    void getSrc() {
        assertEquals(2,a1.getSrc());
        assertEquals(2,a2.getSrc());
        assertEquals(4,a3.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(3,a1.getDest());
        assertEquals(2,a2.getDest());
        assertEquals(4,a3.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(4,a1.getWeight());
        assertEquals(0,a2.getWeight());
        assertEquals(5,a3.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals(null,a1.getInfo());
        assertEquals(null,a2.getInfo());
        assertEquals(null,a3.getInfo());

    }

    @Test
    void setInfo() {
        a1.setInfo("3");
        a2.setInfo("4");
        a3.setInfo("5");
        assertEquals("3",a1.getInfo());
        assertEquals("4",a2.getInfo());
        assertEquals("5",a3.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0,a1.getTag());
        assertEquals(0,a2.getTag());
        assertEquals(0,a3.getTag());
    }

    @Test
    void setTag() {
        a1.setTag(4);
        a2.setTag(5);
        a3.setTag(6);
        assertEquals(4,a1.getTag());
        assertEquals(5,a2.getTag());
        assertEquals(6,a3.getTag());
    }
}