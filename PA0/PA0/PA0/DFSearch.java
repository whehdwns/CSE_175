//DFS: start from root node, before it explore next branch, it explore current branch
//DFS: LIFO

//DFS is really similar to BFS except DFS is LIFO and BFS is FIFO

import java.util.HashSet;
import java.util.*;


public class DFSearch {
	//initialize the froniter with initial/sharing location
	public Frontier frontier = new Frontier();
	//initialize the explored set
	public Set<String> explore = new HashSet<String>();
	//initialize the variable for BFSearch
	public Map graph;
	public String initialLoc, destinationLoc;
	public int limit, nodeExpansionCount;
	//dfs.nodeExpansionCount;
	
	//DFSearch(graph, initialLoc, destinationLoc, limit) from Pzero.java
	public DFSearch(Map graph, String initialLoc, String destinationLoc, int limit) {
		this.graph = graph;
		this.initialLoc = initialLoc;
		this.destinationLoc = destinationLoc;
		//limit is 1000, but it should starts from 0-999. 
		this.limit = limit-1;
	}
	//dfs.search(false) from Pzero.java
	//boolean variable -> true or false
	public Node search(boolean checkvertex) {
		//Location starting location = map find location (start node)
		Node parent = new Node(graph.findLocation(initialLoc));
		//if fronitier is empty, fail
		if(!frontier.isEmpty()) {
			//chose leaf node and remove from fronier -> fronitier.removeTop()
			frontier.removeTop();
		}
		frontier.addToTop(parent);
		explore.add(parent.loc.name);
		//Count starts from zero
		nodeExpansionCount =0;
		
		//if leaf node is destiatnion, return parent node
		if(initialLoc == destinationLoc) {
			return parent;
		}
		//LIFO -> addtoTop and removebotoom
		//Expand Node, add child nodes to frontier
		//only if not in frontier or explored set -repeated state checking
		//the depth shouldn't go beyond the limit
		while(!frontier.isEmpty() && parent.depth < limit) {
			//chose leaf node and remove from frontier 
			parent = frontier.removeTop();
			//if leaf node is destiatnion, return parent node
			if(parent.isDestination(destinationLoc)) {
				return parent;
			}
			//explore and expand
			parent.expand();
			nodeExpansionCount++;
			if(!checkvertex) {
				//use for (Road r : loc.roads) method in Node.java
				for(Node i: parent.children) {
						frontier.addToTop(i); //--------------Difference between BFS
				}
			}
			else {
				for(Node i: parent.children) {
					//if child state is not explored or frontier
					if(!explore.contains(i.loc.name)) {
						frontier.addToTop(i); //---------------Difference between BFS
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

