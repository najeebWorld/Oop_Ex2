package api;

public class Edge implements EdgeData{
    int src,dis;
    double weight;
    String info;
    int tag;//its the color of vertex.
    // 0 - white , 1- gray , 2 - black.


    public Edge(int src, int dis, double weight) {
        this.src = src;
        this.dis = dis;
        this.weight = weight;
        this.tag = 0;
        this.info = null;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.getDest();
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info= s;

    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag =t;
    }
}
