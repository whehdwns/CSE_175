//A* search Pseudocode from the lecture slides is same as Pseudocode from the Uniform Cost search from lecture slides
//only difference is A* is using Heuristic function and GoodHeuristic function
//also A* is f = g +h
import java.util.*;

public class AStarSearch {
	//initialize the frontier with initial/sharing location
	public SortedFrontier frontier = new SortedFrontier(SortBy.f);
	//initialize the explored set
	public Set<String> explore = new HashSet<String>();
	public StreetMap graph;
	public String initialLoc, destinationLoc;
	public int limit, nodeExpansionCount=0;
	public Heuristic heuristic;
	//AStarSearch(graph, initialLoc, destinationLoc, limit);
	public AStarSearch(StreetMap graph, String initialLoc, String destinationLoc, int limit) {
		this.heuristic = new GoodHeuristic(graph,graph.findLocation(destinationLoc));
		this.graph= graph;
		this.initialLoc= initialLoc;
		this.destinationLoc = destinationLoc;
		this.limit = limit - 1;
	}
	public Node search(boolean checkvertex) {
		//if frontier is empty then return failure
		while(!frontier.isEmpty()) {
			//chose leaf node and remove from frontier -> fronitier.removeTop()
			frontier.removeTop();
		}
		explore.clear();
		nodeExpansionCount =0;
		Node parent = new Node(graph.findLocation(initialLoc));
		
		if(initialLoc == destinationLoc) {
			return parent;
		}
		frontier.addSorted(parent);
		explore.add(parent.loc.name);
		//for each action in problem.ACTIONS(node.STATE)
		while(!frontier.isEmpty() && parent.depth < limit) 
		{
			//chose leaf node and remove from frontier 
			parent = frontier.removeTop();
			//if leaf node is destination, return parent node
			if(parent.isDestination(destinationLoc)) 
			{
				return parent;
			}
			//explore and expand
			parent.expand(heuristic);
			nodeExpansionCount++;
			//if the checkvertex is false
			if(!checkvertex) {
				frontier.addSorted(parent.children);
			}
			//if the checkvertex is true
			else 
			{
				for(Node i: parent.children) 
				{
					//if child state is not explored or frontier
					if(!explore.contains(i.loc.name)) 
					{
						//frontier ¡çINSERT(child,frontier) 
						frontier.addSorted(i);
						explore.add(i.loc.name);
					//if child state is explored or frontier 
					}else 
					{
						if(frontier.contains(i.loc.name)) 
						{ 
							//find higher PATH-COST
							Node higher = frontier.find(i);
							if(i.partialPathCost <higher.partialPathCost) 
							{
								//replace that frontier node with child
								frontier.remove(higher);
								frontier.addSorted(i);
							}		
						}
					}
				}
			}
		}
		return null;
	}
	
}
