package Gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;


public class panel_GUI extends JPanel {

   // algo a1;
Graph graph1;

    panel_GUI(Graph g) {
        this.graph1=g;
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBackground(Color.BLACK);
        int screenHeight = screensize.height;
        int screenWidth = screensize.width;
        this.setPreferredSize(new Dimension(screenWidth / 2, screenHeight / 2));


        // add try and catch for json
        //this.a1 = new algo();
    }

    public void paint(Graphics g) {
      //  this.setBackground(new Color(0,0,0));
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screensize.height/2;
        int screenWidth =screensize.width/2;
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLACK);
        g2D.fillRect(0,0,screenWidth,screenHeight);

        double [] arr=find_mins_maxs(this.graph1);
        double x_differ=arr[1]-arr[0];
        double y_differ=arr[3]-arr[2];
        int r1=8;

        Iterator<NodeData> iter1 = graph1.nodeIter();
        while (iter1.hasNext()) {
            g2D.setPaint(Color.RED);
            Vertex n = (Vertex) iter1.next();
            double xx=n.getLocation().x();
            int x=change_cord_x(xx,x_differ,screenWidth,arr[0]);
            double yy=n.getLocation().y();
            int y=change_cord_y(yy,y_differ,screenHeight,arr[2]);
            g2D.fillOval(x-r1, y-r1, r1*2, r1*2);
            g2D.setFont(new Font("Ariel", Font.BOLD, 10));
            g2D.setPaint(Color.PINK);
            g2D.drawString("key: "+ n.getKey() , x+7,y+7 );
        }

        g2D.setPaint(Color.BLUE);
        int r2=1;
        g2D.setStroke(new BasicStroke(2*r2));
        Iterator<EdgeData> iter2 = graph1.edgeIter();
        while(iter2.hasNext()){
            Edge e=(Edge)iter2.next();
            g2D.setPaint(Color.BLUE);


            double xx1=graph1.Nodes.get(e.getSrc()).getLocation().x();
            int x1=change_cord_x(xx1,x_differ,screenWidth,arr[0]);
            double yy1=graph1.Nodes.get(e.getSrc()).getLocation().y();
            int y1=change_cord_y(yy1,y_differ,screenHeight,arr[2]);
            double xx2=graph1.Nodes.get(e.getDest()).getLocation().x();
            int x2=change_cord_x(xx2,x_differ,screenWidth,arr[0]);
            double yy2=graph1.Nodes.get(e.getDest()).getLocation().y();
            int y2=change_cord_y(yy2,y_differ,screenHeight,arr[2]);
            g2D.drawLine(x1-r2,y1-r2,x2-r2,y2-r2);
            g2D.setPaint( Color.yellow );
            int xnew=(x1+x2)/2;
            int ynew=(y1+y2)/2;
           // g2D.drawString("weight: "+e.getWeight(),xnew,ynew);

            int [] dots=find_tri(x1,x2,y1,y2);
            int[] xs=new int[3];
            int[] ys=new int[3];
            for(int i=0;i<dots.length;i++){
                if(i<=2){
                    xs[i]=dots[i];
                }
                else{
                    ys[i-3]=dots[i];
                }
            }
            Polygon tri= new Polygon(xs,ys,3);
            g2D.fillPolygon(tri);


        }
        g2D.setPaint(Color.BLACK);
        int[] xs={0,10,5};
        int[] ys={0,0,6};
        g2D.fillPolygon(xs,ys,3);


    }


    public int change_cord_x(double x,double x_differ,int screenWidth,double minx){
   int x_cor=(int)(((x-minx)/x_differ)*(screenWidth-100));
        x_cor=x_cor+50;
        return x_cor;
    }
    public int change_cord_y(double y,double y_differ,int screenHeight,double miny){
        int y_cor=(int)(((y-miny)/y_differ)*(screenHeight-100));
        y_cor=y_cor+50;
        return y_cor;
    }

public void color_dot(int n){


}


    public double[] find_mins_maxs(Graph g) {
        double minx = Double.MAX_VALUE;
        double maxx = Double.MIN_VALUE;
        double miny = Double.MAX_VALUE;
        double maxy = Double.MIN_VALUE;
        double x;
        double y;
        Iterator<NodeData> iter1 = graph1.nodeIter();
        while (iter1.hasNext()){
            Vertex n=(Vertex)iter1.next();
            x=n.getLocation().x();
            y=n.getLocation().y();
            if(x<minx){
                minx=x;
            }
            if(x>maxx){
                maxx=x;
            }
            if(y<miny){
                miny=y;
            }
            if(y>maxy){
                maxy=y;
            }
        }
        double []arr={minx,maxx,miny,maxy};
        return arr;
    }
   public int[] find_tri(int x1,int x2, int y1, int y2){
        double slope=(y2-y1)/(x2-x1);
        if(slope>0 ) {
            int[] dots = { x2+4,  x2-4,  x2 + 6,y2-4, +y2+4,  y2+4};
            return dots;
        }
        if(slope<0){
            int[] dots = { x2-4, x2+4, x2+6, y2+4, +y2-4,  y2-6};
            return dots;
        }
        else{
            int[] dots = { x2, x2, x2+6, y2+4, +y2-4,  y2};
            return dots;
        }
   }

}


