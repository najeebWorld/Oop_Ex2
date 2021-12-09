package api;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.PrintWriter;
import java.util.*;


public class algo implements DirectedWeightedGraphAlgorithms {
    public Graph myGraph; //the graph that we are working on.
    double[] arr;//save the smallest weight.


    //this algo constructor gets a json file and makes a new graph from it.
    public algo(String jsonName) {
        try {
            myGraph = new Graph(jsonName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // this algo constructor copies a graph
    public algo(Graph gr1) {
        this.myGraph = gr1;

        this.arr = new double[this.myGraph.nodeSize()];//the size of array is nodesize
        Iterator<NodeData> al = gr1.nodeIter();//making iterator of NodeData on gr1(gragh)


    }


    //default constructor
    public algo() {
        this.myGraph = new Graph();
    }



    @Override
    public void init(DirectedWeightedGraph g) {

        Iterator<NodeData> al = g.nodeIter();//making iterator for g.
        while (al.hasNext()) {
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
    //return my graph
    public DirectedWeightedGraph getGraph() {
        return this.myGraph;
    }

    @Override
    // this creates a deep copy of the graph
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


    /**
     *      we will see if you can get from every Vertex to Every Vertex
     *      if the graph has no nodes by default it is connected
     *      if the graph has fewer edges than vertexes it can not be connected
     *      if neither of the if give us an answer
     *      we will start by marking each vertex as not seen
     *      taking the first Vertex in the Hashmap of Vertexes
     *      and running the function tagchild
     *      after we will iterate through the Vertexes and make sure that all the Vertexes have been seen
     *      if we find a vertex that hasn't been seen the graph isn't connected
     *      if all the vertexes have been seen we will flip the graph
     *      and repeat on the flipped graph
     *
     *      the running time is O(|v|^2) v=vertex
     *      because tagchild running time is O(|v|^2)
     *      the max run time for flip graph is also O(|v|^2) =O (|v|+|e|)
     *      when every Vertex has edges to every vertex v*(v-1)+v=v^2
     * @return
     */

    @Override
    public boolean isConnected() {
        if(this.myGraph.nodeSize()==0){
            return true;
        }
        if(this.myGraph.edgeSize()<this.myGraph.nodeSize()){
            return false;
        }

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



    /**
     *      we will check if the vertx has been seen
     *      if it hasn't been seen we will mark that it has been seen
     *      we will iterate through the Vertexes in the graph
     *      if the vertex we get to while iterating hasn't been seen and isn't vertex v
     *      we will see if there is an edge between v and this vertex
     *      if there is we will run the function again with the new vertex
     *      the run time of this function is O(|v|^2) v=vertex
     *
     * @param v Vertex
     * @param g Graph
     */

    public void tagchild(Vertex v, Graph g){
      //  int key=v.getKey();
        if(v.getTag()!=1) {
           v.setTag(1);
           Iterator<NodeData> it = g.nodeIter();
           while(it.hasNext()) {
               Vertex n = (Vertex) it.next();
               if (n.getTag() != 1 && n!=v) {
                   if(g.edg.get(v.getKey()+","+n.getKey())!=null){
                           tagchild(n,g);
                   }
               }
           }
       }
    }



    /**
     *      we will start by creating a new graph
     *      we will copy the vertexes from the given graph to our new graph
     *      we will copy the edges from the given graph to our new graph
     *      BUT we will switch the src and dest
     *      run time O(|v|+|e|) v=vertexes, e=edges
     * @param myGraph
     * @return
     */

    public Graph flipGraph( Graph myGraph){
        Graph newGraph=new Graph ();

        Iterator<NodeData> a = myGraph.nodeIter();
        while(a.hasNext()){
         Vertex   n1=(Vertex)a.next();
            n1.nodes.clear();
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



    /**
     *  create  a hashmap from the Floyd_Warshall function
     *  create a list l1 with the src and dest keys
     *  create the list l2 by getting the value of l1 from the hashmap
     *  return l2
     *  the run time of the function is O(|v|^3) because of the Floyd_Warshall function
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */

    @Override
    public double shortestPathDist(int src, int dest) {

        HashMap<List<Integer>, Double> not_mat = Floyd_Warshall(this.myGraph);
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(src);
        l1.add(dest);
        double ans = not_mat.get(l1);
        return ans;
    }



    /**
     *      create  a hashmap from the Floyd_Warshall_list function
     *      create a list l1 with the src and dest keys
     *      create a new list  l2 from access the hashmap with the l1 and get its value
     *      create a new list  l3 of Vertexes from the l2 we got from the hashmap
     *      return l3
     *      the run time of the function is O(|v|^3) because of the Floyd_Warshall_list function
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */

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



    /**
     *      if the graph isn't connected the graph doesn't have a center
     *      create a hashmap from the Floyd_Warshall function
     *      create a new hashmap that contains the farthest vertex from each vertex when looking at the shortest path between vertexes
     *      we will create a loop in a loop to find the farthest vertex from each vertex
     *      and add it to the hashmap
     *      we will loop through the hashmap to find the vertex with the smallest value
     *      and return that vertex
     *      the run time of the function is O(|v|^3) because of the Floyd_Warshall function
     *
     * @return
     */

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



    }


    /**
     * if cities is empty return null
     * if cities has 1 city return cities
     * hashmap1= Floyd_Warshall
     * hashmap2= Floyd_Warshall_list
     * create 2 arraylists of the cities city1,city2
     * crete a loop on a loop to find the shortest path in the cities list
     * create list ans and add the Vertexes in that path to it
     * go through ans and remove from city 2 the vertexes in ans
     * while city2 >0
     * go through city2 and find the smallest path that we can add on to the left or right
     * add the Vertexes in that path to ans
     * go through ans and remove from city 2 the vertexes in ans
     * we have added all the vertexes from cities to the ans
     * now we need to connect the last Vertex to the first vertex
     * return ans
     * the run time of the function is O(|v|^3) v=vertex or O(|c|!) c=vertexes in cities which ever is bigger
     *
     * @param cities
     * @return
     */


    @Override
    public List<NodeData> tsp(List<NodeData> cities) {

        if (cities.isEmpty()) {
            return null;
        }
        if(cities.size()==1){
            return cities;
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
                            l4 = l3;
                            s = true;
                    }
                }
            }
            for (int i = 0; i < city2.size(); i++) {
                if (city2.get(i).getKey() != key_end) {
                    List<Integer> l3 = new ArrayList<Integer>();
                    l3.add(key_end);
                    l3.add(city2.get(i).getKey());
                    if (mat_d.get(l3) < small2) {
                            l4 = l3;
                            e = true;
                            s = false;
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
    }








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



    /**
     *      this function finds the shortest path between all 2 vertexes
     *      here is a explanation to how the code works
     *      https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
     *      the run time of the function is O(|v|^3)
     * @param g
     * @return
     */

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
                if(this.myGraph.edg.get(n1.getKey()+","+n2.getKey())!=null){
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



    /**
     *      this function finds the shortest path between all 2 vertexes with a list of vertexes keys in the path
     *      here is a explanation to how the code works
     *      https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
     *      the run time of the function is O(|v|^3)
     * @param g
     * @return
     */

    public HashMap<List<Integer>, List<Integer>>Floyd_Warshall_list (Graph g){

        HashMap<List<Integer>, Double> mat_rep=new HashMap<List<Integer>, Double>();
        HashMap<List<Integer>, List<Integer>> mat=new HashMap<List<Integer>, List<Integer>>();
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
                        List<Integer> l2=new ArrayList<Integer>();
                        l2.add(n1.getKey());
                        mat.put(l1,l2);
                    }
                    else {
                        mat_rep.put(l1, Double.MAX_VALUE);
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
                                // creating the new list with the path
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