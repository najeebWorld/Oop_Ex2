package Gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;


public class panel_GUI extends JPanel {


Graph graph1;

    panel_GUI(Graph g) {
        this.graph1=g;
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();


        int screenHeight = screensize.height;
        int screenWidth = screensize.width;
        this.setPreferredSize(new Dimension(screenWidth / 2, screenHeight / 2));

    }

    public void paint(Graphics g) {

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


        if(graph1.nodeSize()<500){

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
        }

        g2D.setPaint(Color.BLUE);
        int r2=1;
        g2D.setStroke(new BasicStroke(2*r2));

        Iterator<EdgeData> iter2 = graph1.edgeIter();
        while(iter2.hasNext()) {
            Edge e = (Edge) iter2.next();
            g2D.setPaint(Color.BLUE);
            double xx1 = graph1.Nodes.get(e.getSrc()).getLocation().x();
            int x1 = change_cord_x(xx1, x_differ, screenWidth, arr[0]);
            double yy1 = graph1.Nodes.get(e.getSrc()).getLocation().y();
            int y1 = change_cord_y(yy1, y_differ, screenHeight, arr[2]);
            double xx2 = graph1.Nodes.get(e.getDest()).getLocation().x();
            int x2 = change_cord_x(xx2, x_differ, screenWidth, arr[0]);
            double yy2 = graph1.Nodes.get(e.getDest()).getLocation().y();
            int y2 = change_cord_y(yy2, y_differ, screenHeight, arr[2]);
            g2D.setPaint(Color.blue);
            drawArrowLine(g2D, x1, y1, x2, y2, 6, 6);
        }
            Iterator<NodeData> iter3 = graph1.nodeIter();
            while (iter3.hasNext()) {
                Vertex n = (Vertex) iter3.next();
                double xx = n.getLocation().x();
                int x = change_cord_x(xx, x_differ, screenWidth, arr[0]);
                double yy = n.getLocation().y();
                int y = change_cord_y(yy, y_differ, screenHeight, arr[2]);
                g2D.setFont(new Font("Ariel", Font.BOLD, 11));
                g2D.setPaint(Color.green);
                g2D.drawString("key: " + n.getKey(), x + 7, y + 7);
            }
        }
        else{
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
                g2D.setPaint(Color.green);
                g2D.drawString("key: "+ n.getKey() , x+7,y+7 );
            }

            g2D.setPaint(Color.BLUE);
            int r2=1;
            g2D.setStroke(new BasicStroke(2*r2));
            Iterator<EdgeData> iter2 = graph1.edgeIter();

            while(iter2.hasNext()) {
                Edge e = (Edge) iter2.next();
                g2D.setPaint(Color.BLUE);
                double xx1 = graph1.Nodes.get(e.getSrc()).getLocation().x();
                int x1 = change_cord_x(xx1, x_differ, screenWidth, arr[0]);
                double yy1 = graph1.Nodes.get(e.getSrc()).getLocation().y();
                int y1 = change_cord_y(yy1, y_differ, screenHeight, arr[2]);
                double xx2 = graph1.Nodes.get(e.getDest()).getLocation().x();
                int x2 = change_cord_x(xx2, x_differ, screenWidth, arr[0]);
                double yy2 = graph1.Nodes.get(e.getDest()).getLocation().y();
                int y2 = change_cord_y(yy2, y_differ, screenHeight, arr[2]);
                g2D.setPaint(Color.blue);
                drawArrowLine(g2D, x1, y1, x2, y2, 6, 6);
            }
        }
    }






    public int change_cord_x(double x,double x_differ,int screenWidth,double minx){
   int x_cor=(int)(((x-minx)/x_differ)*(screenWidth-100));
        x_cor=x_cor+35;
        return x_cor;
    }
    public int change_cord_y(double y,double y_differ,int screenHeight,double miny){
        int y_cor=(int)(((y-miny)/y_differ)*(screenHeight-100));
        y_cor=y_cor+35;
        return y_cor;
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


    private void drawArrowLine(Graphics g2D, int x1, int y1, int x2, int y2, int d, int h) {


        //  https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java


        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        g2D.drawLine(x1, y1, x2, y2);
        g2D.fillPolygon(xpoints, ypoints, 3);
    }


}


