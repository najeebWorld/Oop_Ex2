package api;

public class Geo implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public Geo (double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;

    }


    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double  one  = Math.pow((g.x() -this.x),2);
        double two =Math.pow((g.y() -this.y),2);
        double three = Math.pow((g.z() -this.z),2);
        double final1 = one+two+three;
        final1 = Math.sqrt(final1);



        return final1;
    }
}
