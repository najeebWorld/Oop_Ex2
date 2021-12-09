package Gui;

import api.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class frameGui extends JFrame implements ActionListener {
    algo a1;
    panel_GUI panel;
    JMenuBar mb;
    JMenu vertex,edge,graph;
    JMenuItem save,load,isConnected,tsp,center,shortestPathList,shortestPath,connect,removeEdge,edgeSize,addNode,removeNode,nodeSize,makeList;

    public frameGui(DirectedWeightedGraphAlgorithms a) {
   //public frameGui() {
        super();
        this.a1= (algo)a;
        Graph g=a1.myGraph;
        //a1.load();
        ImageIcon image= new ImageIcon("green fade.png");
        this.setIconImage(image.getImage());
        this.setResizable(false);
        panel = new panel_GUI(g);
        this.add(panel);
        this.pack();


        this.setTitle("Najeeb and Yehudit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        mb=new JMenuBar();

        vertex=new JMenu("vertex");
        edge=new JMenu("edge");
        graph=new JMenu("graph");

        addNode=new JMenuItem("add vertex");
        removeNode=new JMenuItem("remove vertex");
        nodeSize=new JMenuItem("number of vertexes");
        vertex.add(addNode);
        vertex.add(removeNode);
        vertex.add(nodeSize);


        connect=new JMenuItem("add edge");
        removeEdge=new JMenuItem("remove edge");
        edgeSize =new JMenuItem("number of edges");
        edge.add(connect);
        edge.add(removeEdge);
        edge.add(edgeSize);


        isConnected=new JMenuItem("isConnected");
        shortestPath=new JMenuItem("shortest Path");
        shortestPathList=new JMenuItem("shortest Path list");
        center=new JMenuItem("center");
        makeList=new JMenuItem("make list");
        tsp=new JMenuItem("tsp");
        save=new JMenuItem("save");
        load=new JMenuItem("load");
        graph.add(isConnected);
        graph.add(shortestPath);
        graph.add(shortestPathList);
        graph.add(center);
        graph.add(tsp);
        graph.add(save);
        graph.add(load);

        mb.add(vertex);
        mb.add(edge);
        mb.add(graph);



        nodeSize.addActionListener(this);
        addNode.addActionListener(this);
        removeNode.addActionListener(this);

        edgeSize.addActionListener(this);
        connect.addActionListener(this);
        removeEdge.addActionListener(this);


        isConnected.addActionListener(this);
        shortestPath.addActionListener(this);
        shortestPathList.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        center.addActionListener(this);
        tsp.addActionListener(this);


        this.add(mb);
        this.setJMenuBar(mb);

        this.setVisible(true);


    }





 @Override
 public void actionPerformed(ActionEvent e) {


     if (e.getSource() == isConnected) {
         boolean b = a1.isConnected();
         //System.out.println("is the graph connected? " + b);
         if (b) {
             JOptionPane.showMessageDialog(null, "the graph is connected");
         } else {
             JOptionPane.showMessageDialog(null, "the graph isn't connected");
         }
     }


     if (e.getSource() == shortestPath) {

         String src = JOptionPane.showInputDialog("please enter the key of the src vertex");
         int s = Integer.parseInt(src);
         if (this.a1.myGraph.Nodes.get(s) == null) {
             JOptionPane.showMessageDialog(null, "error: src vertex doesn't exist");
         } else {
             String dest = JOptionPane.showInputDialog("please enter the key of the dest vertex");
             int d = Integer.parseInt(dest);
             if (this.a1.myGraph.Nodes.get(d) == null) {

                 JOptionPane.showMessageDialog(null, "error: dest vertex doesn't exist");
             } else {
                 double d1 = a1.shortestPathDist(s, d);
                 JOptionPane.showMessageDialog(null, "the shortest path between vertex "+s+" to vertex "+d+" is: "+d1);

             }
         }
     }


     if (e.getSource() == shortestPathList) {

          String src = JOptionPane.showInputDialog("please enter the key of the src vertex");
          int s = Integer.parseInt(src);
          if (this.a1.myGraph.Nodes.get(s) == null) {
              JOptionPane.showMessageDialog(null, "error: src vertex doesn't exist");
          } else {
              String dest = JOptionPane.showInputDialog("please enter the key of the dest vertex");
              int d = Integer.parseInt(dest);
              if (this.a1.myGraph.Nodes.get(d) == null) {

                  JOptionPane.showMessageDialog(null, "error: dest vertex doesn't exist");
              } else {
                  List<NodeData> l=  a1.shortestPath(s, d);
                  String str="the shortest path from vertex "+s+" to vertex "+d+" is: "+s;
                  for (int i=1;i<l.size();i++){
                      str=str+"->"+l.get(i).getKey();
                  }
                  JOptionPane.showMessageDialog(null, str);
                  }
              }
       }


     if(e.getSource()==center){
            NodeData n=a1.center();
            if(n!=null) {
                 // System.out.println("the NodeData in the middle of the graph is the node with key "+ n.getKey());
                JOptionPane.showMessageDialog(null,"the vertex wwth the key " +n.getKey()+"is the center" );
            }
            else{
                //  System.out.println("this graph doesn't have a center ");
                JOptionPane.showMessageDialog(null,"this graph isn't connected, thus it doesn't have a center" );
            }
       }


       if(e.getSource()==tsp){
           List <NodeData>l=new ArrayList<NodeData>();
           boolean addmore=true;
           while(addmore) {
               String src = JOptionPane.showInputDialog("please enter the key of a vertex");
               int s1 = Integer.parseInt(src);
               if (this.a1.myGraph.Nodes.get(s1) == null) {
                   JOptionPane.showMessageDialog(null, "error: src vertex doesn't exist");
               } else {
                   l.add(this.a1.myGraph.Nodes.get(s1));
                   String dest = JOptionPane.showInputDialog("do ypu want to add more vertexes to the group? if yes return 0, else return 1 ");
                   int d = Integer.parseInt(dest);
                   if (d == 1) {
                       addmore = false;
                   }
               }
           }
           List<NodeData> l2=a1.tsp(l);
           String str="the path that contains the list of vertexes is: "+l2.get(0).getKey();
           for (int i=1;i<l2.size();i++){
               str=str+"->"+l2.get(i).getKey();
           }
           JOptionPane.showMessageDialog(null, str);

        }


        if(e.getSource()==save){
             // get file name
             // run algo with the file name
        }


        if(e.getSource()==load){
         // get file name
         // run algo with the file name
       }



       if(e.getSource()==addNode) {

           String src1 = JOptionPane.showInputDialog("please enter the x coordinates ");
           double x = Double.parseDouble(src1);
           String src2 = JOptionPane.showInputDialog("please enter the y coordinates ");
           double y = Double.parseDouble(src1);
           Geo g = new Geo(x, y, 0);
           String src3 = JOptionPane.showInputDialog("please enter the Vertex key ");
           int key = Integer.parseInt(src3);
           if (this.a1.myGraph.Nodes.get(key) != null) {
               String src4 = JOptionPane.showInputDialog("a Vertex with this key exist, if you want to move it return 0, if you wnt to pick a new key return 1 ");
               int q = Integer.parseInt(src3);
               if (q == 0) {
                   Vertex n = new Vertex(key, g);
                   a1.myGraph.addNode(n);
                   this.setVisible(false);
                   frameGui f=new frameGui(this.a1);
               }
                else if (q == 1) {
                   src3 = JOptionPane.showInputDialog("please enter the Vertex key ");
                   key = Integer.parseInt(src3);
                   if (this.a1.myGraph.Nodes.get(key) != null) {
                       JOptionPane.showMessageDialog(null, "error");
                   } else {
                       Vertex n = new Vertex(key, g);
                       a1.myGraph.addNode(n);
                       this.setVisible(false);
                       frameGui f = new frameGui(this.a1);
                   }
               }
           } else {
               Vertex n = new Vertex(key, g);
               a1.myGraph.addNode(n);
               this.setVisible(false);
               frameGui f=new frameGui(this.a1);

           }
       }


       if(e.getSource()==removeNode){
           String src = JOptionPane.showInputDialog("please enter the vertex key you want to remove");
           int s=Integer.parseInt(src);
           if(this.a1.myGraph.Nodes.get(s)==null) {
               JOptionPane.showMessageDialog(null,"error: vertex doesn't exist");
           }
           else {
               a1.myGraph.removeNode(s);
               this.setVisible(false);
               frameGui f=new frameGui(this.a1);
           }
      }


      if(e.getSource()==nodeSize){
       int s=a1.myGraph.nodeSize();
       //System.out.println("the number of Vertexes in the graph is " +s)

          JOptionPane.showMessageDialog(null,"the number of Vertexes in the graph is " +s);
      }


      if(e.getSource()==connect){

          String src = JOptionPane.showInputDialog("please enter the key of the src vertex");
          int s = Integer.parseInt(src);
          if (this.a1.myGraph.Nodes.get(s) == null) {
              JOptionPane.showMessageDialog(null, "error: src vertex doesn't exist");
          } else {
              String dest = JOptionPane.showInputDialog("please enter the key of the dest vertex");
              int d = Integer.parseInt(dest);
              if (this.a1.myGraph.Nodes.get(d) == null) {

                  JOptionPane.showMessageDialog(null, "error: dest vertex doesn't exist");
              } else {
                  String w1 = JOptionPane.showInputDialog("please enter the weight of the edge ");
                  double w=Double.parseDouble(w1);
                  a1.myGraph.connect(s,d,w);
                  this.setVisible(false);
                  frameGui f=new frameGui(this.a1);
              }
          }
       }


      if(e.getSource()==removeEdge) {

          String src = JOptionPane.showInputDialog("please enter the key of the src vertex");
          int s=Integer.parseInt(src);
          if(this.a1.myGraph.Nodes.get(s)==null) {

              JOptionPane.showMessageDialog(null,"error: src vertex doesn't exist");
          }
          else {
              String dest = JOptionPane.showInputDialog("please enter the key of the dest vertex");
              int d=Integer.parseInt(dest);
              if(this.a1.myGraph.Nodes.get(d)==null) {

                  JOptionPane.showMessageDialog(null,"error: dest vertex doesn't exist");
              }
              else{
                  a1.myGraph.removeEdge(s, d);
                  this.setVisible(false);
                  frameGui f=new frameGui(this.a1);
              }
          }
      }


      if(e.getSource()==edgeSize){
       int s=a1.myGraph.edgeSize();
       String out="the number of edges in the graph is " +s;
       JOptionPane.showMessageDialog(null,"the number of edges in the graph is " +s);
       //System.out.println("the number of edges in the graph is " +s);

     }
    }


    public static void main(String[] args) {

      //  frameGui myGui=new frameGui("G1.json");


    }

}
