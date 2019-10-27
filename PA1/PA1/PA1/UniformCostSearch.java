//Uniform cost search   g(node)
import java.util.*;

public class UniformCostSearch {
	//initialize the frontier with initial/sharing location
	public SortedFrontier frontier = new SortedFrontier(SortBy.g);
	//initialize the explored set
	public Set<String> explore = new HashSet<String>();
	public StreetMap graph;
	public String initialLoc, destinationLoc;
	public int limit, nodeExpansionCount=0;
	//UniformCostSearch(graph, initialLoc, destinationLoc, limit);
	public UniformCostSearch(StreetMap graph, String initialLoc, String destinationLoc, int limit) {
		this.graph= graph;
		this.initialLoc= initialLoc;
		this.destinationLoc = destinationLoc;
		this.limit = limit - 1;
	}
	public Node search(boolean checkvertex) {
		//if frontier is empty then return failure
		////Fixed error(null exception) by changing from if statement to while statement
		while(!frontier.isEmpty()) {
			//chose leaf node and remove from frontier -> fronitier.removeTop()
			frontier.removeTop();
		}
		explore.clear();
		nodeExpansionCount =0;
		Node parent = new Node(graph.findLocation(initialLoc));
		
		if(initialLoc == destinationLoc) {
			//solution
			return parent;
		}
		frontier.addSorted(parent);
		explore.add(parent.loc.name);
		//for each action in problem.ACTIONS(node.STATE)
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
				frontier.addSorted(parent.children);
			}
			//if the checkvertex is true
			else 
			{
				for(Node i: parent.children) 
				{
					//if child state is not explored or frontier
					if(!explore.contains(i.loc.name)) {
						//frontier ¡çINSERT(child,frontier) 
						frontier.addSorted(i);
						explore.add(i.loc.name);
					//if child state is explored or frontier 
					}else 
					{
						//Fixed error(null exception) by changing explore.contains -> frontier.contains
						if(frontier.contains(i.loc.name)) { 
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
