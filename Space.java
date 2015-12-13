
public class Space {
	public int xloc;
	public int yloc;
	public Terrain terr;
	public boolean occ;
	public Unit unit;
	
	public Space(int x, int y) {
		xloc = x;
		yloc = y;
	}
	public Space(int x, int y, Terrain ter){
		xloc = x;
		yloc = y;
		terr = ter;
		occ = false;
		unit = null;
	}
	public String toString(){
		return xloc + "," + yloc;
	}
	public int getDistance(Space space){
		return Math.abs(space.xloc-xloc) + Math.abs(space.yloc-yloc);
	}
	/* THIS ALLOWS FOR DIAGONAL MOVES ---- GOOD IDEA?
	public int getDistance(Space space){
		int xdist = Math.abs(space.xloc - xloc);
		int ydist = Math.abs(space.yloc - yloc);
		if(xdist > ydist)
			return xdist;
		return ydist;
	}
	*/
}
