
public class PathNode {
	int cost;
	Space prev;
	boolean visited;
	public PathNode(int c, Space p){
		cost = c;
		prev = p;
		visited = false;
	}
	public void setCost(int c){
		cost = c;
	}
	public void setPrev(Space p){
		prev = p;
	}
	public void visit(){
		visited = true;
	}
}
