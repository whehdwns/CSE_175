import java.util.*;
//Greedy Search uses h(node) -> Heuristic and GoodHeuristic
//For the frontier, use a priority queue that sorts by  h(n)  cost
public class GreedySearch {
	//initialize the frontier with initial/sharing location
	//For the frontier, use a priority queue that sorts by h(n) cost
	public SortedFrontier frontier = new SortedFrontier(SortBy.h);
	//initialize the explored set
	public Set<String> explore = new HashSet<String>();
	public StreetMap graph;
	public String initialLoc, destinationLoc;
	public int limit, nodeExpansionCount=0;
	//Greedy Search needs Heuristic and GoodHeuristic
	public Heuristic heuristic;
	
	
	// GreedySearch(graph, initialLoc, destinationLoc, limit);
	public GreedySearch(StreetMap graph, String initialLoc, String destinationLoc, int limit) {
		//GoodHeuristic(StreetMap map, Location destination) 
		//graph.findLocation(destinationLoc) is for find destination location
		this.heuristic = new GoodHeuristic(graph,graph.findLocation(destinationLoc));
		this.graph= graph;
		this.initialLoc= initialLoc;
		this.destinationLoc = destinationLoc;
		this.limit = limit - 1;
	}
	public Node search(boolean checkvertex) {
		while(!frontier.isEmpty()) {
			//chose leaf node and remove from frontier -> fronitier.removeTop()
			frontier.removeTop();
		}
		explore.clear();
		nodeExpansionCount =0;
		
		Node parent = new Node(graph.findLocation(initialLoc));
		//if the node contains a goal state
		if(initialLoc == destinationLoc) {
			//return the corresponding solution which is parents
			return parent;
		}
		//add the node to the explore set
		frontier.addSorted(parent);
		//expand the chosen node
		explore.add(parent.loc.name);
		
		while(!frontier.isEmpty() && parent.depth < limit) {
			//chose leaf node and remove from frontier 
			parent = frontier.removeTop();
			//if leaf node is destination, return parent node
			if(parent.isDestination(destinationLoc)) {
				return parent;
			}
			//explore and expand
			//Using Heuristic and GoodHeuristic to expand (to find minimum cost of the route)
			parent.expand(heuristic);
			nodeExpansionCount++;
			//if the checkvertex is false
			if(!checkvertex) {
				frontier.addSorted(parent.children);
			}
			//if the checkvertex is true
			else 
			{
				//for each child in the children of the chosen node
				for(Node i: parent.children) 
				{
					//if child is not in frontier or explore set
					if(!explore.contains(i.loc.name)) {
						frontier.addSorted(i);
						explore.add(i.loc.name);
					}
				}
			}
		}
		return null;
	}
	
}
