
public class Terrain {
	int movecost;
	public Terrain(int x){
		movecost = x;
	}
	public int getMoveCost(){
		return movecost;
	}
	public String toString(){
		if(movecost == 1)
			return "P";
		if(movecost == 2)
			return "H";
		if(movecost == 3)
			return "M";
		return "W";
	}
}
