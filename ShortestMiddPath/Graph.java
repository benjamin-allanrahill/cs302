package ShortestMiddPath;

/**
 * 
 * 
 * Author: Shelby Kimmel
 * Creates a adjacency list object to store information about the graph of roads, and contains the main functions used to 
 * run the Bellman Ford algorithm 
 * Co-Author: Benjamin Allan-Rahill
 * Time spent: 2 hours
 * 
 */

import java.io.File;
import java.util.Scanner;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;
import java.security.KeyStore.Entry;
import java.io.File;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Graph {

	// Object that contains an adjacency list of a road network, and a dictionary from elements of the list to indeces from 0 to |V|-1
	HashMap<Integer, ArrayList<Road>> adjList;
	HashMap<Integer,Integer> nodeDict;


	public Graph(String file) throws IOException{
		// We will store the information about the road graph in an adjacency list
		// We will use a HashMap to store the Adjacency List, since each vertex in the graph has a more or less random integer name.
		// Each element of the HashMap will be an ArrayList containing all roads (edges) connected to that vertex
		adjList = new HashMap<>();
		nodeDict = null;

		// Based on https://stackoverflow.com/questions/49599194/reading-csv-file-into-an-arrayliststudent-java
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		if ((line=br.readLine())==null){
			return;
		}
		while ((line = br.readLine())!=null) {
			String[] temp = line.split(",");
			//Assume all roads are two-way, and using ArcMiles as distance:
			this.addToList(new Road(Integer.parseInt(temp[60]),Integer.parseInt(temp[61]),temp[9],Double.parseDouble(temp[31])));
			this.addToList(new Road(Integer.parseInt(temp[61]),Integer.parseInt(temp[60]),temp[9],Double.parseDouble(temp[31])));
		}


		//For dynamic programming, we will have an array with indeces 0 to |V|-1, 
		// where |V| is the number of vertices. Thus we need to associate each element of adjList with a number between 0 and |V|-1
		// We will use a Dictionary (HashMap) to do this.
		nodeDict = new HashMap<>();
		int j = 0;
		for (Integer nodeName: adjList.keySet()){
			nodeDict.put(nodeName, j);
			j++;
		}
	}


	// get functions
	public HashMap<Integer, ArrayList<Road>> getAdjList(){
		return adjList;
	}
	public HashMap<Integer,Integer> getDict(){
		return nodeDict;
	}

	//Adds the Road (edge) to the appropriate list of the adjacency list, used by the constructor method
	//Based on https://stackoverflow.com/questions/12134687/how-to-add-element-into-arraylist-in-hashmap 
	public synchronized void addToList(Road road) {
		Integer node = road.getStart();
		ArrayList<Road> roadList = this.getAdjList().get(node);

		// if node is not already in adjacency list, we creat a list for it
		if (roadList == null) {
			roadList = new ArrayList<Road>();
			roadList.add(road);
			this.getAdjList().put(node, roadList);
		} else {
			// add if item is not already in list
			if (!roadList.contains(road))
				roadList.add(road);
		}

	}

	/**
	 * This method returns the array with lists of shortest distances 
	 * @param startNode the node to start the path from 
	 * @return Double[][] Shortest Distance Array
	 */

	public Double[][] ShortestDistance(Integer startNode) {
		// This method should create the array storing the objective function values of subproblems used in Bellman Ford.

		Double[][] dpArray = new Double[nodeDict.size()][nodeDict.size()];//You should probably do a different initialization here

		// initalize array with infinity and zero
		for (int i = 0; i < dpArray.length; i++) {
			dpArray[i][0] = Double.MAX_VALUE;
		}
		dpArray[this.nodeDict.get(startNode)][0] = 0.0;

		// fill up array w/ shortest paths
		for (int i = 1; i < this.nodeDict.size(); i++) {
			for (Integer node : this.nodeDict.keySet()) {
				int v = this.nodeDict.get(node);
				dpArray[v][i] = dpArray[v][i - 1];
				for (Road road : this.adjList.get(node)) {
					if ((dpArray[this.nodeDict.get(road.endNode)][i - 1] + road.miles) < dpArray[v][i]) {
						dpArray[v][i] = dpArray[this.nodeDict.get(road.endNode)][i - 1] + road.miles;
					}

				}
				;
			}
		}

		return dpArray;
	}
	
	/**
	 * Works backwards through array and calculates the path to be taken; prints the path.
	 * @param endNode the location where the path should end
	 * @param dpArray the array of the shortest distances 
	 */
    public void ShortestPath(Integer endNode, Double[][] dpArray){
		// This method should work backwards through the array you created in ShortestDistance and output the 
		// sequence of streets you should take to get from your starting point to your ending point.

		// initalize values
		int i = this.nodeDict.size() - 1;
		int v = endNode;
		ArrayList<String> streetList = new ArrayList<>();


		// work "backwards" through the array 
		while (i > 0) {
			System.out.println(dpArray[this.nodeDict.get(v)][i]);
			if (dpArray[this.nodeDict.get(v)][i] != dpArray[this.nodeDict.get(v)][i - i]) {
				for (Road road : this.adjList.get(v)) {
					if ((dpArray[this.nodeDict.get(road.endNode)][i - 1] + road.miles) == dpArray[this.nodeDict.get(v)][i]) {
						streetList.add(0, road.name);
						v = road.endNode;
						break;
					}
				}
				;
			}
			i--;
		}

		// print the path
		System.out.println(streetList);
	}			
				

}