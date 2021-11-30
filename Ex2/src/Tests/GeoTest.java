package Tests;

import api.Geo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoTest {

    Geo a1 = new Geo(6,6,6);
    Geo a2 = new Geo(8,9,9);
    Geo a3 = new Geo(1,1,1);
    Geo a4 = new Geo(12,13,24);
    Geo a5 = new Geo(3,5,6);


    @Test
    void x() {
        assertEquals(6,a1.x());
        assertEquals(8,a2.x());
        assertEquals(1,a3.x());
        assertEquals(12,a4.x());
        assertEquals(3,a5.x());
    }

    @Test
    void y() {
        assertEquals(6,a1.y());
        assertEquals(9,a2.y());
        assertEquals(1,a3.y());
        assertEquals(13,a4.y());
        assertEquals(5,a5.y());
    }

    @Test
    void z() {
        assertEquals(6,a1.z());
        assertEquals(9,a2.z());
        assertEquals(1,a3.z());
        assertEquals(24,a4.z());
        assertEquals(6,a5.z());
    }

    @Test
    void distance() {
        assertEquals(Math.sqrt(22),a1.distance(a2));
        assertEquals(Math.sqrt(177),a2.distance(a3));
        assertEquals(Math.sqrt(45),a5.distance(a3));
    }
}