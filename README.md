
# Ex2_OOP
__________________________________
## Najeeb Abdulah & Yehudit Brickner 
### our project has 9 classes:
<b> 
<br>•	Geo- implements Geolocation
<br>•	Vertex- implements NodeData
<br>•	Edge- implements EdgeData 
<br>•	Graph- implements DirectedwheightedGrah
<br>•	Algo- implements DirectedwheightedGrah
<br>•	FrameGUI
<br>•	Panel_GUI
<br>•	Ex2
<br>•	main
</b> 
<br>
<br>
<b> Geo</b> 
<br>In this class there are three variables x, y, z. 
<br>these variables are doubles and are used to represent a 3D location,
<br>in this project we are using 2D points so the z variable wont be used much.
<br>this class has getters for x, y, z and a function to find the distance between 2 points.
<br>
<br>
<b>Vertex </b> 
<br>In this class we have 6 variables: ID, pos, tag, weight, info, and nodes.
<br>ID in an int, pos is a Geo, tag is int, weight is a double, info is a string, and nodes is a HashMap<Integer, Integer>.
<br>We have 3 constructers, 1 that gets everything, 1 that gets ID and pos, and a copy constructor.
<br>In this project we didn’t use the info and weights of the vertex
<br>we have getters and  setters for almost everything.
<br>
<br>
<b>  Edge</b> 
<br>In this class we have 5 variables: src, dest, tag, weight, info.
<br>src and dest are Vertexes tag is int, weight is a double, and info is a string.
<br>We have 2 constructers, 1 that gets everything, and a copy constructor.
<br>In this project we didn’t use the info of the edge.
<br>we have getters and setters for almost everything.
<br>
<br>
<b>  Graph </b> 
<br>In this class we have 4 variables: mc, Nodes, Edge, edg.
<br>mc is a int, Nodes is a HashMap<Integer, Vertex>, Edge is a HashMap<Integer, HashMap<Integer, EdgeData>>, and edg is a HashMap<String, EdgeData>.
<br>We have 3 constructers, 1 that gets a graph from a json file, 1 that gets everything, and a copy constructor.
<br>in this class we have the following functions:
<br>getNode: returns the vertex of the key given.
<br>getEdge: returns the edge that has the src and dest given.
<br>addNode: adds the NodeData that we were given to the graph (Vertex).
<br>connect: creates a new edge with the data given.
<br>nodeIter, edgeIter, edgeIter (node_id) :creating an iterator that will give a run time exception if we changed the graph while the iterator was running.
<br>there is an iterator to go through the Vertexes in the graph, the edges in the graph, and to go through the edges that are coming from a certain vertex.
<br>removeNode: removes the vertex from the graph and all edges that start or end at this vertex.
<br>removeEdge: removes the edge from the graph.
<br>nodeSize: return the amount of vertexes in this graph.
<br>edgeSize: return the amount of edges in this graph.
<br>getMC: return the mc;
<br>
<br>
<b>  Algo</b> 
<br>this class has1 variables myGraph which is a Graph
<br>We have 3 constructers 1 that gets a graph from a json file, 1 that just creates the class with out a graph, and a copy constructer.
<br>in this class we have the following functions:
<br>init: makes a copy of the given graph – myGraph.
<br>getGraph: return the graph
<br>copy: creates a deep copy of the graph
<br>isConnected: checks if you can get from every vertex to every other vertex
<br>tagchild: marks that we have seen this vertex
<br>flipGraph: edges of the graph
<br>shortestPathDist: return the shortest weight of the shortest distance between the vertexes.
<br>shortestPath: return a list of Vertexes that is the path of the shortest distance between the vertexes.
<br>center: returns the center of the graph if there is 1. This is the vertex that distance of the farthest vertex from is the least out of all the vertexes in the graph.
<br>tsp: this function returns a path that has all the vertexes in a list, the path should be as minimal as possible. In this function you can use nodes that aren’t in the list and can repeat nodes.
<br>save: saves the graph to a json file
<br>load: loads a json file of a graph
<br>Floyd_Warshall: makes a HashMap of the shortest distance between all pairs of Vertexes.
<br>Floyd_Warshall_list: makes a HashMap of lists of vertexes that are the shortest path between each pair of Vertexes.
we will elaborate more about most of these function later
<br>
<br>
<b> FrameGUI</b> 
<br>this class make the frame of the gui.
<br>creates the menu bar, and the action listener.
<br>when you open the menu bar and press on the function you want to run, the action bar will ask for input if needed till it gets correct input, and than runs the function and a window will pop up with answer. 
<br>
<br>
<b>  Panel_GUI</b> 
<br>this class makes the panel of the gui.
<br>in the panel we will be showing the graph.
<br>the panel has the functions to take the x,y coordinates from the vertexes and the edges and change them so that they fit nicely in the panel.
<br>we also have a function to make arrows as edges.
<br>we found this function on stack over flow: https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
<br>
<br>
<b>  Ex2</b> 
<br>this class has 3 functions:
<br>getGraph: creates a new object algo, load a json file to it, return the graph from algo
<br>getGraphAlgo: creates the object algo with a graph we get from a json file, return algo.
<br>runGUI: create an object of frameGUI that shows the GUI.
<br>
<br>
<b> main </b>
<br>this class gets a String for the json file and runs the function in Ex2 that creates the GUI.
<br>
<br>
__________________________________________________________________________________________________________________________________

## elaborating the functions from algo
<b>init:</b> 
<br>makes a copy of the given graph into are graph-myGraph
<br>run time O(|v|+|e|) v=vertexes, e=edges .
<br>getGraph:</b> 
<br>returns the graph
<br>the run time is O(1)
<br><br><b>copy: </b>
<br>this creates a deep copy of the graph
<br>run time O(|v|+|e|) v=vertexes, e=edges .
<br><br><b>isConnected:</b>
<br>we will check if you can get from every Vertex to Every Vertex.
<br>if the graph has no nodes by default it is connected.
<br>if the graph has fewer edges than vertexes it can not be connected.
<br>if neither of the if give us an answer.
<br>we will start by marking each vertex as not seen.
<br>taking the first Vertex in the Hashmap of Vertexes.
<br>and running the function tagchild.
<br>after we will iterate through the Vertexes and make sure that all the Vertexes have been seen.
<br>if we find a vertex that hasn't been seen the graph isn't connected.
<br>if all the vertexes have been seen we will flip the graph.
<br>and repeat on the flipped graph.
<br>the running time is O(|v|^2) v=vertex.
<br>because tagchild running time is O(|v|^2)
<br>the max run time for flip graph is also O(|v|^2) =O (|v|+|e|)
<br>when every Vertex has edges to every vertex v*(v-1)+v=v^2 
<br><br><b>tagchild:</b>
<br>we will check if the vertx has been seen.
<br>if it hasn't been seen we will mark that it has been seen.
<br>we will iterate through the Vertexes in the graph.
<br>if the vertex we get to while iterating hasn't been seen and isn't vertex v.
<br>we will see if there is an edge between v and this vertex.
<br>if there is we will run the function again with the new vertex.
<br>the run time of this function is O(|v|^2) v=vertex. 
<br><br><b>flipGraph:</b>
<br>we will start by creating a new graph.
<br>we will copy the vertexes from the given graph to our new graph.
<br>we will copy the edges from the given graph to our new graph.
<br>BUT we will switch the src and dest.
<br>run time O(|v|+|e|) v=vertexes, e=edges .
<br><br><b>shortestPathDist:</b>
<br>create  a hashmap from the Floyd_Warshall function
<br>create a list l1 with the src and dest keys
<br>create the list l2 by getting the value of l1 from the hashmap
<br>return l2
<br>the run time of the function is O(|v|^3) because of the Floyd_Warshall function 
<br><br><b>shortestPath:</b>
<br>create  a hashmap from the Floyd_Warshall_list function
<br>create a list l1 with the src and dest keys
<br>create a new list  l2 from access the hashmap with the l1 and get its value
<br>create a new list  l3 of Vertexes from the l2 we got from the hashmap
<br>return l3
<br>the run time of the function is O(|v|^3) because of the Floyd_Warshall_list function 
<br><br><b>center:</b>
<br>if the graph isn't connected the graph doesn't have a center.
<br>create a hashmap from the Floyd_Warshall function.
<br>create a new hashmap that contains the farthest vertex from each vertex when looking at the shortest path between vertexes.
<br>we will create a loop in a loop to find the farthest vertex from each vertex
<br>and add it to the hashmap.
<br>we will loop through the hashmap to find the vertex with the smallest value
<br>and return that vertex.
<br>the run time of the function is O(|v|^3) because of the Floyd_Warshall function.
<br><br><b>tsp: </b>
<br>if cities is empty return null.
<br>if cities has 1 city return cities.
<br>hashmap1= Floyd_Warshall.
<br>hashmap2= Floyd_Warshall_list.
<br>create 2 arraylists of the cities city1,city2.
<br>create a loop on a loop to find the shortest path in the cities list.
<br>create list ans and add the Vertexes in that path to it.
<br>go through ans and remove from city 2 the vertexes in ans.
<br>while city2 >0
<br>go through city2 and find the smallest path that we can add on to the left or right.
<br>add the Vertexes in that path to ans.
<br>go through ans and remove from city 2 the vertexes in ans.
<br>we have added all the vertexes from cities to the ans.
<br>now we need to connect the last Vertex to the first vertex.
<br>return ans.
<br>the run time of the function is O(|v|^3) v=vertex or O(|c|!) c=vertexes in cities which ever is bigger.
<br><br><b>save:</b>
<br>we created a map
<br>than a jsonObject and jsonArray
<br>we will iterate through the edges and create a linkedList in every iterartion.
<br>we will add the edges parts to the linkedList and dd the linked list to the json array
<br>when we finish iterating through the edges we will add the jsonArray to the Json object.
<br>and do the same thing for the Vertexes.than we will open a file writer and write the json object into a file
<br>we will flush and clos the file
<br>if we didnt have ant errors we will return true else we will false
<br>the run time is O(|v|+|e|) v=vertex e=edge. 
<br><br><b>load: </b>
<br>this function loads a graph from a json file
<br>run time O(|v|+|e|) v=vertexes, e=edges .
<br><br><b>Floyd_Warshall: </b>
<br>this function finds the shortest path between all 2 vertexes
<br>here is a explanation to how the code works
<br>https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
<br>the run time of the function is O(|v|^3) v=vertex
<br><br><b>Floyd_Warshall_list:</b>
<br>this function finds the shortest path between all 2 vertexes with a list of vertexes keys in the path
<br>here is a explanation to how the Floyd_Warshall works
<br>https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
<br>in this function as we updated the main hashmap we update the other hashmap with the path.
<br>the run time of the function is O(|v|^3).
<br>
<br>
_______________________________________________________________________
### running time of the functions
<br>

|   	          |isConnected|tsp		| center   | shortestPathDist |	shortestpath|
|---------------|-----------|--------|---------|------------------|-----------------|
| 1000 Nodes    |		        |         |	        |	                  |  	                 |
| 10000 Nodes	  |		    |      |      |	      |	      |
| 100000 Nodes	|		    |      |      |	      |	      |



