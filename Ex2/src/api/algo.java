package api;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class algo implements DirectedWeightedGraphAlgorithms {
    Graph myGraph;//the graph that i work on.
    // int index;
    double[] arr;//save the sortest wheight.

    public algo(String jsonName) {
        try {
            myGraph = new Graph(jsonName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public algo(Graph gr1) {
        this.myGraph = gr1;

        this.arr = new double[this.myGraph.nodeSize()];//the size of array is nodesize
        Iterator<NodeData> al = gr1.nodeIter();//making iterator of NodeData on gr1(gragh)


    }

    public algo() {
        this.myGraph = new Graph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        //   this.arr =new double[g.nodeSize()];
        Iterator<NodeData> al = g.nodeIter();//making iterator for g.
        while (al.hasNext()) {//while loop on my iterator.
            NodeData curr = al.next();
            this.myGraph.addNode(curr);//adding to my graph node by node
        }
        Iterator<EdgeData> ag = g.edgeIter();
        while (ag.hasNext()) {
            EdgeData curr = ag.next();
            this.myGraph.connect(curr.getSrc(), curr.getDest(), curr.getWeight());
            //adding to myGraph edge by edge.
        }

    }


    @Override
    public DirectedWeightedGraph getGraph() {
        return this.myGraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Graph g = new Graph();
        Iterator<NodeData> a = myGraph.nodeIter();
        while (a.hasNext()) {
            g.addNode(a.next());
        }
        Iterator<EdgeData> b = myGraph.edgeIter();
        while (b.hasNext()) {
            EdgeData e=b.next();
            g.connect(e.getSrc(), e.getDest(), e.getWeight());
        }
        return g;
    }
/*
    @Override
    public boolean isConnected() {
        for (int i = 0; i < this.myGraph.nodeSize() ; i++) {
            HashMap<Integer,Double> al = shortpath(i);
            //Iterator <EdgeData> iter =
                //must add on iter;
            for (int j = 0; j < this.myGraph.nodeSize(); j++) {
                if (i == j) {
                    continue;
                }
                if(al.get(j)!= null){
                    if (al.get(j) == -1) {
                       return false;
                }}
            }

        }
        return true;
    }
*/

    @Override
    public boolean isConnected() {
        /*
        HashMap<List<Integer>, Double> map1 = Floyd_Warshall(this.myGraph);
        Iterator<NodeData> a1 = myGraph.nodeIter();
        Vertex v1=null;
        while (a1.hasNext()){
         v1=(Vertex) a1.next();
        }
        Iterator<NodeData> a2 = myGraph.nodeIter();
        while (a2.hasNext()){
            Vertex v2=(Vertex) a2.next();
            if(v1!=v2){
                List<Integer> l1 = new ArrayList<Integer>();
                l1.add(v1.getKey());
                l1.add(v2.getKey());
                if(map1.get(l1)==Double.MAX_VALUE){
                    return false;
                }
            }
        }
        Graph newG = flipGraph(this.myGraph);
        HashMap<List<Integer>, Double> map2 = Floyd_Warshall(newG);
        Iterator<NodeData> a3 = myGraph.nodeIter();
        Vertex v3=null;
        while (a3.hasNext()){
            v3=(Vertex) a3.next();
        }
        Iterator<NodeData> a4 = myGraph.nodeIter();
        while (a4.hasNext()){
            Vertex v4=(Vertex) a4.next();
            if(v1!=v4){
                List<Integer> l1 = new ArrayList<Integer>();
                l1.add(v3.getKey());
                l1.add(v4.getKey());
                if(map2.get(l1)==Double.MAX_VALUE){
                    return false;
                }
            }
        }
        return true;


        */

      //  Queue<NodeData> Short = new ArrayDeque<>();
        Iterator<NodeData> a1 = myGraph.nodeIter();
        while(a1.hasNext()){
            a1.next().setTag(0);
        }
        Iterator<NodeData> a = myGraph.nodeIter();
        Vertex nodi=null;
        if(a.hasNext()) {
            nodi = (Vertex) a.next();
        }
        tagchild(nodi,myGraph);
        Iterator<NodeData> b = myGraph.nodeIter();
        while(b.hasNext()){
            if(b.next().getTag()!= 1)
                return false;
        }
        Graph newG =   flipGraph(myGraph);

        /*
        Iterator<NodeData> c = myGraph.nodeIter();
        Vertex nody=null;
        if(c.hasNext()) {
            nody = (Vertex) c.next();
        }
        tagchild(nody);
        Iterator<NodeData> d = myGraph.nodeIter();
        while(d.hasNext()){
            if(d.next().getTag()!= 1)
                return false;
        }
        */



       // Queue<NodeData> Short1 = new ArrayDeque<>();
        Iterator<NodeData> c = myGraph.nodeIter();
        while(c.hasNext()){
            c.next().setTag(0);
        }
        Iterator<NodeData> d = myGraph.nodeIter();
        Vertex nody=null;
        if(d.hasNext()) {
            nody = (Vertex)d.next();
        }
        tagchild(nody,newG);
        Iterator<NodeData> e = myGraph.nodeIter();
        while(e.hasNext()){
            if(e.next().getTag()!= 1)
                return false;
        }
        return true;



    }




    public void tagchild(Vertex v, Graph g){
        int key=v.getKey();
       if(v.getTag()!=1) {
           v.setTag(1);
           Iterator<NodeData> it = g.nodeIter();
           while(it.hasNext()) {
               Vertex n = (Vertex) it.next();
               if (n.getTag() != 1 && n!=v) {
                   if(g.edg.get(v.getKey()+","+n.getKey())!=null){
                  // if (this.myGraph.Nodes.get(key).nodes.size() != 0) {
                       //  v.setTag(1);
                       //int key = v.getKey();
                      // Iterator<EdgeData> a = myGraph.edgeIter(key);
                       //while (a.hasNext()) {
                         //  int keys = a.next().getDest();
                           //Vertex v1 = this.myGraph.Nodes.get(keys);
                           tagchild(n,g);
                      // }
                   }
               }
           }
       }
    }

    public Graph flipGraph( Graph myGraph){
        Graph newGraph=new Graph ();
      //  Vertex n1=null;
      //  Vertex n2=null;
      //  Edge e1=null;
        Iterator<NodeData> a = myGraph.nodeIter();
        while(a.hasNext()){
         Vertex   n1=(Vertex)a.next();
            n1.nodes.clear();
           // n1.Edges_in.clear();
            n1.setTag(0);
            newGraph.addNode(n1);
        }
        Iterator<EdgeData> b= myGraph.edgeIter();
        while(b.hasNext()){
          Edge  e1= (Edge) b.next();
          Vertex  n1=this.myGraph.Nodes.get(e1.src);
          Vertex  n2=this.myGraph.Nodes.get(e1.dis);
            newGraph.connect(n2.getKey(), n1.getKey(), e1.getWeight());
        }
        return newGraph;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        //HashMap<Integer, Double> nod = shortpath(src);

        //return nod.get(dest);
        HashMap<List<Integer>, Double> not_mat = Floyd_Warshall(this.myGraph);
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(src);
        l1.add(dest);
        double ans = not_mat.get(l1);
        return ans;
//        Queue<NodeData> Short = new ArrayDeque<>();
//        Short.add(myGraph.getNode(src));
//        HashMap<Integer,Double> nod = new HashMap<Integer,Double> ();
//        Iterator<NodeData> e = myGraph.nodeIter();
//        for(int i =0 ; i< nod.size();i++ )
//            nod.put(e.next().getKey(), Double.MAX_VALUE);
//        nod.put(src,0.0);
//
//        while(!Short.isEmpty()){
//            NodeData v =  Short.poll();
//            Iterator <EdgeData> a = this.myGraph.edgeIter(v.getKey());
//            while (a.hasNext()){
//                EdgeData EDGE = a.next();
//                if(nod.get(EDGE.getDest()) > nod.get(v.getKey())+ EDGE.getWeight()) {
//                    nod.put(EDGE.getDest(), nod.get(v.getKey()) + EDGE.getWeight());
//                    Short.add(myGraph.getNode(EDGE.getDest()));
//                }
//            }
//
//        }
//        for(Integer i : nod.keySet()){
//            if(nod.get(i)== Double.MAX_VALUE)
//                nod.put(i, (double) -1);
//        }
//        return nod.get(dest);
//
//    }
//
    }


    public HashMap<Integer, Double> shortpath(int src) {

        Queue<NodeData> Short = new ArrayDeque<>();
        Short.add(myGraph.getNode(src));
        HashMap<Integer, Double> nod = new HashMap<Integer, Double>();
        Iterator<NodeData> e = myGraph.nodeIter();
        for (int i = 0; i < nod.size(); i++) {
            if (e.next().getKey() != src)
                nod.put(e.next().getKey(), Double.MAX_VALUE);
        }
        nod.put(src, 0.0);

        while (!Short.isEmpty()) {
            NodeData v = Short.poll();
            Iterator<EdgeData> a = myGraph.edgeIter(src);
            while (a.hasNext()) {
                EdgeData EDGE = a.next();
                if(nod.get(EDGE.getDest())!= null ){
                    if (nod.get(EDGE.getDest()) > nod.get(v.getKey()) + EDGE.getWeight()) {
                        nod.put(EDGE.getDest(), nod.get(v.getKey()) + EDGE.getWeight());
                        Short.add(myGraph.getNode(EDGE.getDest()));
                }}
            }

        }
        return nod;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<List<Integer>,Double> s=Floyd_Warshall(this.myGraph);
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(src);
        l1.add(dest);
        if(s.get(l1)==0.0 || s.get(l1)==Double.MAX_VALUE){
            return null;
        }
        HashMap<List<Integer>, List<Integer>> hash = Floyd_Warshall_list(this.myGraph);

        List<Integer> ans1 = hash.get(l1);
        List<NodeData> ans=new ArrayList<NodeData>();
        for (int i=0; i<ans1.size();i++){
          ans.add( myGraph.Nodes.get(ans1.get(i)));
        }
        return ans;
    }
//        HashMap<Integer, Double> weight = shortpath(src);
//        List<NodeData> nod = new ArrayList<>();
//        NodeData n = myGraph.getNode(src);
//        nod.add(n);
////        Geo X = new Geo(1, 1, 2);
////        n = new Vertex(-1, X);
////        System.out.println(n);
//        while (n.getKey() != src) {
//
//            double min = Double.MAX_VALUE;
//            int what_in = Integer.MAX_VALUE;
//            Iterator<Integer> edge_to_dest = myGraph.in.get(dest).values().iterator();
//            while (edge_to_dest.hasNext()) {
//                int node = edge_to_dest.next();
//                if (node == src) {
//                    what_in = src;
//                    break;
//                }
//                if(weight.get(node) != null){
//                  if (min > weight.get(node)) {
//                    min = weight.get(node);
//                    what_in = node;
//                }}
//
//            }
//            dest = what_in;
//            n = myGraph.getNode(what_in);
//            nod.add(n);
//        }
//        Collections.reverse(nod);
//        return nod;



    @Override
    public NodeData center() {
        if (isConnected() == false) {
            return null;
        }
        HashMap<List<Integer>,Double> shortlen =Floyd_Warshall(this.myGraph);
        HashMap<Integer,Double> al=new HashMap<Integer,Double>();

        Iterator<NodeData> it1 = myGraph.nodeIter();
        while (it1.hasNext()) {
            double big=0.0;
            Vertex v1=(Vertex)it1.next();
            Iterator<NodeData> it2 = myGraph.nodeIter();
            while (it2.hasNext()) {
                Vertex v2 = (Vertex) it2.next();
                List<Integer> l1=new ArrayList<Integer>();
                l1.add(v1.getKey());
                l1.add(v2.getKey());
                if(shortlen.get(l1)>big){
                    big=shortlen.get(l1);
                }
            }
            al.put(v1.getKey(),big);
        }

        double small=Double.MAX_VALUE;
        int key=0;
        Iterator<NodeData> it3 = myGraph.nodeIter();
        while (it3.hasNext()){
            Vertex v3=(Vertex) it3.next();
            if(al.get(v3.getKey())<small){
                key=v3.getKey();
               small= al.get(key);
            }
        }
       Vertex ans= myGraph.Nodes.get(key);
       return ans;
//        double min = Double.MAX_VALUE;
//        NodeData nod = null;
//        for (int i = 0; i < this.myGraph.nodeSize() ; i++) {
//            HashMap<Integer,Double> al = shortpath(i);
//
//            double maxValue = 0;
//            for (int j = 0; j < this.myGraph.nodeSize(); j++) {
//                if(al.get(j)!= null){
//                  if (al.get(j) > maxValue) {
//                    maxValue = al.get(j);
//
//                }
//            }}
//            if (maxValue < min) {
//                min = maxValue;
//                nod = this.myGraph.getNode(i);
//            }
//        }
//        return nod;


    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {

        if (cities.isEmpty()) {
            return null;
        }

        HashMap<List<Integer>,Double> mat_d =Floyd_Warshall(this.myGraph);
        HashMap<List<Integer>,List<Integer>> mat_n =Floyd_Warshall_list(this.myGraph);
        List<NodeData> city1=new ArrayList<NodeData>();
        List<NodeData> city2=new ArrayList<NodeData>();
        for (int i=0;i<cities.size();i++) {
            city1.add(cities.get(i));
            city2.add(cities.get(i));
        }
        double small=Double.MAX_VALUE;
        List<Integer> l2=new ArrayList<Integer>();
        //find the smallest connector in this group;

        for (int i=0;i<city1.size();i++){
            for (int j=0;j<city1.size();j++){
                if(i!=j){
                    int v1=city1.get(i).getKey();
                    int v2=city1.get(j).getKey();
                    List<Integer> l1=new ArrayList<Integer>();
                    l1.add(v1);
                    l1.add(v2);
                    if(small>mat_d.get(l1) && mat_d.get(l1)!=0){
                        small=mat_d.get(l1);
                        l2=l1;
                    }
                }
            }
        }
        ArrayList<Integer> ans1=new ArrayList<Integer>();
        List<Integer> l1=new ArrayList<Integer>();
        l1=mat_n.get(l2);
        //adding the nodes in l2 path ans
        for (int i=0;i<l1.size();i++){
            ans1.add(l1.get(i));
        }

        //make city smaller-by getting rid of the nodes we just added
        for (int j=0;j<l1.size();j++) {
            int k1 = -1;
            for (int i = 0; i < city2.size(); i++) {
                if (city2.get(i).getKey() == l1.get(j)) {
                    k1 = i;
                }
            }
            if (k1 != -1) {
                city2.remove(k1);
            }
        }


        while (city2.size()>0) {
           // double small1 = Double.MAX_VALUE;

            //double small2=small;
            double small2=Double.MAX_VALUE;

            int key_start = ans1.get(0);
            int key_end = ans1.get(ans1.size() - 1);
            boolean s = false;
            boolean e = false;

            List<Integer> l4 = new ArrayList<Integer>();
            for (int i = 0; i < city2.size(); i++) {
                if (city2.get(i).getKey() != key_start) {
                    List<Integer> l3 = new ArrayList<Integer>();
                    l3.add(city2.get(i).getKey());
                    l3.add(key_start);
                    if (mat_d.get(l3) < small2) {
                       // if (mat_d.get(l3) < small1) {
                            l4 = l3;
                          //  small1 = mat_d.get(l3);
                            s = true;
                       // }
                    }
                }
            }
            for (int i = 0; i < city2.size(); i++) {
                if (city2.get(i).getKey() != key_end) {
                    List<Integer> l3 = new ArrayList<Integer>();
                    l3.add(key_end);
                    l3.add(city2.get(i).getKey());
                    if (mat_d.get(l3) < small2) {
                       // if (mat_d.get(l3) < small1) {
                            l4 = l3;
                         //   small1 = mat_d.get(l3);
                            e = true;
                            s = false;
                       // }
                    }
                }
            }

            l1 = mat_n.get(l4);
            //adding the nodes in l2 path ans
            if (s) {
                for (int i = l1.size() - 2; i > 0 - 1; i--) { // dont add last its already in the list
                    ans1.add(0, l1.get(i));
                }
            }
            if (e) {
                for (int i = 1; i < l1.size(); i++) { // dont add first its already in the list
                    ans1.add(l1.get(i));
                }
            }
            for (int j=0;j<l1.size();j++) {
                int k1 = -1;
                for (int i = 0; i < city2.size(); i++) {
                    if (city2.get(i).getKey() == l1.get(j)) {
                        k1 = i;
                    }
                }
                if (k1 != -1) {
                    city2.remove(k1);
                }
            }
           // small2 = small1;
        }

        ArrayList<Integer> l5=new ArrayList<Integer>();
        l5.add(ans1.get(ans1.size()-1));
        l5.add(ans1.get(0));
        List<Integer> l6=mat_n.get(l5);
        for (int i=1;i<l6.size();i++){
            ans1.add(l6.get(i));
        }
        List<NodeData> ans =new ArrayList<NodeData>();
        for (int i=0; i<ans1.size();i++){
            ans.add(myGraph.Nodes.get(ans1.get(i)));
        }

            return ans;




        /*
        List<NodeData> na = new ArrayList<>();
        int index = 0;
        NodeData nop = cities.get(index);
        na.add(nop);
        cities.remove(nop);
        while (cities.size() > 0) {
            double minIndex = Double.MAX_VALUE;
            HashMap<Integer, Double> shortOne = shortpath(nop.getKey());
            int i = 0;
            while (i < cities.size() && shortOne.get(cities.get(i)) != null) {
                if (shortOne.get(cities.get(i).getKey()) < minIndex) {
                    minIndex = shortOne.get(cities.get(i).getKey());
                    index = i;
                }
                i++;
            }
            nop = cities.get(index);
            na.add(nop);
            cities.remove(nop);
        }
        return na;
        */
        // return null;
    }






//        if(cities.isEmpty())
//             return null;
//        List<NodeData> nod = cities;
//
//        Iterator<NodeData> na = nod.listIterator();
//        while(na.hasNext()){
//            NodeData b = na.next();
//            HashMap <Integer,Double> a = shortpath(b.getKey());
//
//            for(Integer i : )


    @Override
    public boolean save(String file) {
        try {
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            Map m;
            Iterator<EdgeData> iterator = this.myGraph.edgeIter();
            while (iterator.hasNext()) {
                m = new LinkedHashMap(3);
                EdgeData e = iterator.next();
                m.put("src", e.getSrc());
                m.put("w", e.getWeight());
                m.put("dest", e.getDest());
                ja.add(m);
            }
            jo.put("Edges", ja);
            ja = new JSONArray();
            Iterator<NodeData> iterator1 = this.myGraph.nodeIter();
            while (iterator1.hasNext()) {
                m = new LinkedHashMap(2);
                NodeData n = iterator1.next();
                m.put("pos", n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z());
                m.put("id", n.getKey());
                ja.add(m);
            }
            jo.put("Nodes", ja);
            PrintWriter pw = new PrintWriter(file);
            pw.write(jo.toString());
            pw.flush();
            pw.close();
            return true;
        } catch (Exception e) {
            return false;
    }}

    @Override
    public boolean load(String file) {

        try {
            DirectedWeightedGraph newGraph = new Graph(file);
            myGraph = (Graph) newGraph;

        } catch (Exception e) {
            return false;
        }
        return true;
    }



    public HashMap<List<Integer>, Double> Floyd_Warshall (Graph g){

        HashMap<List<Integer>, Double> mat_rep=new HashMap<List<Integer>, Double>();
        Vertex n1=null;
        Vertex n2=null;
        Vertex n3=null;
        //filling in the initial values in the hashmap
        Iterator<NodeData> it1 = myGraph.nodeIter();
        while(it1.hasNext()){
            n1=(Vertex)it1.next();
            Iterator<NodeData> it2 = myGraph.nodeIter();
            while(it2.hasNext()){
                n2=(Vertex)it2.next();
                List<Integer> l1=new ArrayList<Integer>();
                l1.add(n1.getKey());
                l1.add(n2.getKey());
                //if(this.myGraph.Nodes.get(n1.getKey()).nes.get(n1.getKey())!=null){
                if(this.myGraph.edg.get(n1.getKey()+","+n2.getKey())!=null){
                   // mat_rep.put(l1,this.myGraph.Edges.get(n1.getKey()).get(n1.getKey()).getWeight());
                    mat_rep.put(l1,this.myGraph.edg.get(n1.getKey()+","+n2.getKey()).getWeight());
                }
                else{
                    if(l1.get(0)==l1.get(1)){
                        mat_rep.put(l1,0.0);
                    }
                    else {
                        mat_rep.put(l1, Double.MAX_VALUE);
                    }
                }
            }
        }

        double w1=0;
        double w2=0;
        double w3=0;

        Iterator<NodeData> iter1 = myGraph.nodeIter();  //k
        while (iter1.hasNext()) {
            n1 = (Vertex) iter1.next();
            Iterator<NodeData> iter2 = myGraph.nodeIter();  //i
            while (iter2.hasNext()) {
                n2 = (Vertex) iter2.next();
                if (n1.getKey() != n2.getKey()) { // dont want the same ones
                    Iterator<NodeData> iter3 = myGraph.nodeIter();  //j
                    while (iter3.hasNext()) {
                        n3 = (Vertex) iter3.next();
                        if (n3.getKey() != n1.getKey() && n3.getKey() != n2.getKey()) { // dont want the same ones
                            List<Integer> l1 = new ArrayList<Integer>(); // l1= ij
                            l1.add(n2.getKey());
                            l1.add(n3.getKey());

                            List<Integer> l2 = new ArrayList<Integer>(); // l2=ik
                            l2.add(n2.getKey());
                            l2.add(n1.getKey());

                            List<Integer> l3 = new ArrayList<Integer>(); // l3 = kj
                            l3.add(n1.getKey());
                            l3.add(n3.getKey());

                            w1 = mat_rep.get(l1);

                            w2 = mat_rep.get(l2);

                            w3 = mat_rep.get(l3);

                            if (w1 > w2 + w3) {
                                mat_rep.put(l1, w2 + w3);

                            }
                        }
                    }
                }
            }
        }
        return mat_rep;
    }



    public HashMap<List<Integer>, List<Integer>>Floyd_Warshall_list (Graph g){

        HashMap<List<Integer>, Double> mat_rep=new HashMap<List<Integer>, Double>();
        HashMap<List<Integer>, List<Integer>> mat=new HashMap<List<Integer>, List<Integer>>();
        //Vertex n1=null;
        // Vertex n2=null;
        // Vertex n3=null;
        //filling in the initial values in the hashmap
        Iterator<NodeData> it1 = g.nodeIter();
        while(it1.hasNext()){
           Vertex n1=(Vertex)it1.next();
            Iterator<NodeData> it2 = g.nodeIter();
            while(it2.hasNext()){
                Vertex n2=(Vertex)it2.next();
                List<Integer> l1=new ArrayList<Integer>();
                l1.add(n1.getKey());
                l1.add(n2.getKey());
                if(g.edg.get(n1.getKey()+","+n2.getKey())!=null){
                    mat_rep.put(l1,g.edg.get(n1.getKey()+","+n2.getKey()).getWeight());
                   // List<> l2=new ArrayList<NodeData>();
                    List<Integer> l2=new ArrayList<Integer>();
                    l2.add(n1.getKey());
                    l2.add(n2.getKey());
                    mat.put(l1,l2);
                }
                else{
                    if(l1.get(0)==l1.get(1)){
                        mat_rep.put(l1,0.0);
                       // List<NodeData> l2=new ArrayList<NodeData>();
                        List<Integer> l2=new ArrayList<Integer>();
                        l2.add(n1.getKey());
                        mat.put(l1,l2);
                    }
                    else {
                        mat_rep.put(l1, Double.MAX_VALUE);
                        //List<NodeData> l2=new ArrayList<NodeData>();
                        List<Integer> l2=new ArrayList<Integer>();
                        l2.add(n1.getKey());
                        l2.add(n2.getKey());
                        mat.put(l1,l2);
                    }
                }
            }
        }

        double w1=0;
        double w2=0;
        double w3=0;

        Iterator<NodeData> iter1 = g.nodeIter();  //k
        while (iter1.hasNext()) {
           Vertex n1 = (Vertex) iter1.next();
            Iterator<NodeData> iter2 = g.nodeIter();  //i
            while (iter2.hasNext()) {
                Vertex n2 = (Vertex) iter2.next();
                if (n1.getKey() != n2.getKey()) { // dont want the same ones
                    Iterator<NodeData> iter3 = g.nodeIter();  //j
                    while (iter3.hasNext()) {
                        Vertex n3 = (Vertex) iter3.next();
                        if (n3.getKey() != n1.getKey() && n3.getKey() != n2.getKey()) { // dont want the same ones
                            List<Integer> l1 = new ArrayList<Integer>(); // l1= ij
                            l1.add(n2.getKey());
                            l1.add(n3.getKey());

                            List<Integer> l2 = new ArrayList<Integer>(); // l2=ik
                            l2.add(n2.getKey());
                            l2.add(n1.getKey());

                            List<Integer> l3 = new ArrayList<Integer>(); // l3 = kj
                            l3.add(n1.getKey());
                            l3.add(n3.getKey());

                            w1 = mat_rep.get(l1);

                            w2 = mat_rep.get(l2);

                            w3 = mat_rep.get(l3);

                            if (w1 > w2 + w3) {
                                mat_rep.put(l1, w2 + w3);
                               // List<NodeData> Vs_new= new ArrayList<NodeData>();
                               // List<NodeData> Vs_old= new ArrayList<NodeData>();
                                List<Integer> Vs_new=new ArrayList<Integer>();
                                List<Integer> Vs_old1=new ArrayList<Integer>();
                                List<Integer> Vs_old2=new ArrayList<Integer>();
                                Vs_old1=mat.get(l2);
                                for (int i=0;i<Vs_old1.size();i++){ // don't want to include last so that we don't have a repeat;
                                    Vs_new.add(Vs_old1.get(i));
                                }
                               // Vs_old.clear();
                                boolean seen=false;
                                Vs_old2=mat.get(l3);
                                for (int i=0;i<Vs_old2.size();i++){
                                    seen=false;
                                    for (int j=0;j<Vs_new.size() && !seen;j++){
                                        if (Vs_new.get(j)==Vs_old2.get(i)){
                                            seen=true;
                                        }
                                    }
                                    if(!seen)
                                    Vs_new.add(Vs_old2.get(i));
                                }
                                // System.out.println(Vs_new);
                                mat.put(l1,Vs_new);

                            }
                        }
                    }
                }
            }
        }

        return mat;
    }




}