//BFS: start from root node, explore close neighbor first, and explore far neighbor later
//BSF: FIFO
//Resource: Textbook Chapter 3 : Solving Problem by Searching page 82

import java.util.HashSet;
import java.util.*;


public class BFSearch {
	//initialize the frontier with initial/sharing location
	public Frontier frontier = new Frontier();
	//initialize the explored set
	public Set<String> explore = new HashSet<String>();
	//initialize the variable for BFSearch
	public Map graph;
	public String initialLoc, destinationLoc;
	public int limit, nodeExpansionCount;
	//bfs.nodeExpansionCount;
	
	//BFSearch(graph, initialLoc, destinationLoc, limit) from Pzero.java
	public BFSearch(Map graph, String initialLoc, String destinationLoc, int limit) {
		this.graph = graph;
		this.initialLoc = initialLoc;
		this.destinationLoc = destinationLoc;
		//limit is 1000, but it should starts from 0-999. 
		this.limit = limit-1;
	}
	//bfs.search(false) from Pzero.java
	//boolean variable -> true or false
	public Node search(boolean checkvertex) {
		//Location starting location = map find location (start node)
		Node parent = new Node(graph.findLocation(initialLoc));
		//if frontier is empty, fail
		if(!frontier.isEmpty()) {
			//chose leaf node and remove from frontier -> fronitier.removeTop()
			frontier.removeTop();
		}
		frontier.addToBottom(parent);
		explore.add(parent.loc.name);
		//Count starts from zero
		nodeExpansionCount =0;
		
		//if leaf node is destination, return parent node
		if(initialLoc == destinationLoc) {
			return parent;
		}
		//FIFO -> removeTOp and addBottom
		//Expand Node, add child nodes to frontier
		//only if not in frontier or explored set -repeated state checking
		//the depth shouldn't go beyond the limit
		while(!frontier.isEmpty() && parent.depth < limit) {
			//chose leaf node and remove from frontier 
			parent = frontier.removeTop();
			//if leaf node is destination, return parent node
			if(parent.isDestination(destinationLoc)) {
				return parent;
			}
			//explore and expand
			parent.expand();
			nodeExpansionCount++;
			//if the checkvertex is false
			if(!checkvertex) {
				//use for (Road r : loc.roads) method in Node.java
				for(Node i: parent.children) {
						frontier.addToBottom(i);
				}
			}
			//if the checkvertex is true
			else {
				for(Node i: parent.children) {
					//if child state is not explored or frontier
					if(!explore.contains(i.loc.name)) {
						frontier.addToBottom(i);
						//insert
						explore.add(i.loc.name);
					}
				}
			}
		}
		//if the node is in destination location, return null
		return null;
	}
	
	
}


