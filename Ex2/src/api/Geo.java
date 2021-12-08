package api;

public class Geo implements GeoLocation {
    private double x;
    private double y;
    private double z;
//this class is the location of each node .
    public Geo (double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;

    }
    public Geo (Geo v){
        this.x=v.x();
        this.y=v.y();
        this.z=v.z();
    }

    //this constructor get string that have the location and make from it double of x/y/z.

    public Geo (String s){
        int a = 0;
        int b=0;
        String x = "";
        String y ="";
        String z = "";
        int k=0;
        for (int i =0 ; i<2;i++){
            if(i==1)k++;
            while(s.charAt(k)!= ','){
                k++;
            }
            if(a==0){
                a=k;
            }else{
                b=k;
            }
        }
        x=s.substring(0,a);
        y=s.substring(a+1,b);
        z=s.substring(b+1,s.length());
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.z = Double.parseDouble(z);
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
    // function that return the distance between my node to given node.
    @Override
    public double distance(GeoLocation g) {
        double  one  = Math.pow((g.x()-this.x),2);
        double two =Math.pow((g.y()-this.y),2);
        double three = Math.pow((g.z()-this.z),2);
        double final1 = one+two+three;
        final1 = Math.sqrt(final1);
        return final1;
    }
}
