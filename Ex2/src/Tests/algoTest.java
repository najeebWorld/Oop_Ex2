//package Tests;
//import api.*;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import java.io.PrintWriter;
//import java.util.*;
//import java.util.Iterator;
//import java.util.List;
//
//public class algo implements DirectedWeightedGraphAlgorithms {
//    private algo graph;
//
//    public algo(DirectedWeightedGraph gr) {
//        this.graph = (algo)gr;
//    }
//    public algo() {
//        this.graph = new algo();
//    }
//
//    @Override
//    public void init(DirectedWeightedGraph g) {
//        this.graph = (algo)g;
//    }
//
//    @Override
//    public DirectedWeightedGraph getGraph() {
//        return this.graph;
//    }
//
//    @Override
//    public DirectedWeightedGraph copy() {
//        algo here = new algo();
//        Iterator<NodeData> it = this.graph.nodeIter();
//        while (it.hasNext()) {
//            NodeData node = it.next();
//            Vertex k = new Vertex(node.getKey(), (Geo) node.getLocation());
//            here.addNode(k);
//        }
//        Iterator<NodeData> it2 = this.graph.nodeIter();
//        while (it2.hasNext()) {
//            Iterator<EdgeData> it3 = this.graph.edgeIter(it2.next().getKey());
//            while (it3.hasNext()) {
//                EdgeData edge= it3.next();
//                here.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
//            }
//        }
//        return here;
//    }
//
//    @Override
//    public boolean isConnected() {
//        Iterator<NodeData> iterator = this.graph.nodeIter();
//        while (iterator.hasNext()){
//            HashMap<Integer, Double> routes = routes(iterator.next().getKey());
//            Iterator<Double> iterator1 = routes.values().iterator();
//            while (iterator1.hasNext()){
//                if (iterator1.next() == -1.0){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public double shortestPathDist(int src, int dest) {
//        HashMap<Integer, Double> routes = routes(src);
//        return routes.get(dest);
//    }
//
//    public HashMap<Integer, Double> routes(int src){
//        Queue<NodeData> q = new ArrayDeque<>();
//        q.add(graph.getNode(src));
//        // List<Double> weight= new LinkedList<>();
//        HashMap<Integer, Double> weight = new HashMap<Integer, Double>();
//        Iterator<NodeData> n = graph.nodeIter();
//        for (int i = 0; i < graph.nodeSize(); i++) {
//            NodeData n1 = n.next();
//            if (n1.getKey() != src) {
//                weight.put(n1.getKey(), Double.MAX_VALUE);
//            }
//        }
//        weight.put(src, 0.0);
//        while (!q.isEmpty()) {
//            NodeData discover = q.poll();
//            Iterator<EdgeData> edge_node = this.graph.edgeIter(discover.getKey());
//            while (edge_node.hasNext()) {
//                EdgeData edge = edge_node.next();
//                if (weight.get(edge.getDest()) > weight.get(discover.getKey()) + edge.getWeight()) {
//                    weight.put(edge.getDest(), weight.get(discover.getKey()) + edge.getWeight());
//                    q.add(graph.getNode(edge.getDest()));
//                }
//            }
//        }
//        for (Integer i:weight.keySet()){
//            if (weight.get(i) == Double.MAX_VALUE){
//                weight.put(i, -1.0);
//            }
//        }
//        return weight;
//    }
//
//    @Override
//    public List<NodeData> shortestPath(int src, int dest) {
//        HashMap<Integer, Double> routes = routes(src);
//        if (routes.get(dest)== -1){
//            return null;
//        }
//        List<NodeData> returnList = new ArrayList<>();
//        NodeData node = graph.getNode(dest);
//        returnList.add(node);
//        double leftToGo = routes.get(dest);
//        while (leftToGo>0){
//            for(Integer i:this.graph.toMe.get(node.getKey()).keySet()) {
//                if (routes.get(i) == -1) {
//                    continue;
//                }
//                double somthing = this.graph.getEdge(i, node.getKey()).getWeight();
//                if (leftToGo - (routes.get(i) + somthing)<0.0001 && leftToGo - (routes.get(i) + somthing)>=0) {
//                    leftToGo = leftToGo - somthing;
//                    node = graph.getNode(i);
//                    returnList.add(node);
//                }
//            }
//            if (leftToGo < 0.0001){
//                break;
//            }
//        }
//        Collections.reverse(returnList);
//        return returnList;
//    }
//
//    @Override
//    public NodeData center() {
//        NodeData ans = null;
//        double min = Double.MAX_VALUE;
//        Iterator<NodeData> iterator = this.graph.nodeIter();
//        while (iterator.hasNext()){
//            double max = 0;
//            NodeData srcNode = iterator.next();
//            HashMap<Integer, Double> routes = routes(srcNode.getKey());
//            for (Integer i:routes.keySet()){
//                if (routes.get(i) == -1.0){
//                    return null;
//                }
//                if (routes.get(i) > max){
//                    max = routes.get(i);
//                }
//            }
//            if (max < min){
//                min = max;
//                ans = srcNode;
//            }
//        }
//        return ans;
//    }
//
//    @Override
//    public List<NodeData> tsp(List<NodeData> cities) {
//        if (cities.size() == 0){
//            return null;
//        }
//        List<NodeData> ans = new ArrayList<>();
//        int idxToDelete = 0;
//        NodeData here = cities.get(0);
//        ans.add(here);
//        cities.remove(cities.get(idxToDelete));
//        while (cities.size()>0){
//            double min = Double.MAX_VALUE;
//            HashMap<Integer, Double> routes = routes(here.getKey());
//            for (int i = 0; i < cities.size(); i++) {
//                if (routes.get(cities.get(i).getKey()) < min){
//                    min = routes.get(cities.get(i).getKey());
//                    idxToDelete = i;
//                }
//            }
//            here = cities.get(idxToDelete);
//            ans.add(here);
//            cities.remove(cities.get(idxToDelete));
//        }
//        return ans;
//    }
//
//    @Override
//    public boolean save(String file) {
//        try {
//            JSONObject jo = new JSONObject();
//            JSONArray ja = new JSONArray();
//            Map m;
//            Iterator<EdgeData> iterator = this.graph.edgeIter();
//            while (iterator.hasNext()) {
//                m = new LinkedHashMap(3);
//                EdgeData e = iterator.next();
//                m.put("src", e.getSrc());
//                m.put("w", e.getWeight());
//                m.put("dest", e.getDest());
//                ja.add(m);
//            }
//            jo.put("Edges", ja);
//            ja = new JSONArray();
//            Iterator<NodeData> iterator1 = this.graph.nodeIter();
//            while (iterator1.hasNext()) {
//                m = new LinkedHashMap(2);
//                NodeData n = iterator1.next();
//                m.put("pos", n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z());
//                m.put("id", n.getKey());
//                ja.add(m);
//            }
//            jo.put("Nodes", ja);
//            PrintWriter pw = new PrintWriter(file);
//            pw.write(jo.toJSONString());
//            pw.flush();
//            pw.close();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean load(String file) {
//        try {
//            this.graph = new algo(file);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//
//}