package api;

import java.awt.*;
import java.util.HashMap;

public class Vertex implements NodeData{
    int id;
    Geo pos;
    double weight;
    String info;
    int tag;
    HashMap<Integer,Integer> nodes;

    public Vertex(int id,Geo pos,int tag,double weight,String info){
        this.id = id;
        this.pos = pos;
        this.weight=weight;
        this.info = info;
        this.tag=tag;
        this.nodes = new HashMap<Integer,Integer>();


    }
    @Override
    public int getKey() {
        return this.getKey();
    }

    @Override
    public GeoLocation getLocation() {
        return this.pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
     this.pos=(Geo)p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
    this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public void addToMeList(Edge n){
        this.nodes.put(n.src,n.src);
    }

    public void removeFromList(int n){
        this.nodes.remove(n);
    }

    public HashMap<Integer,Integer> getnodes(){
        return this.nodes;
    }
}
