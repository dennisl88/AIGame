import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

public class Map {
	Space[][] board;
	int length;
	int width;
	int turnCount;
	HashMap<Integer, Unit> units;
	ArrayList<Team> teams;
	PriorityQueue<Unit> next;
	public Map(int x){
		board = new Space[x][x];
		length = x;
		width = x;
		units = new HashMap<Integer, Unit>();
		teams = new ArrayList<Team>();
		next = new PriorityQueue<Unit>();
		generateTerr();
		//generateTeam(new Team(1));
		//generateTeam(new Team(8));
		printlayer();
		turnCount = 0;
		while(!win()){
			move();
		}
	}
	public void move(){
		Unit u = next.poll();
		u.act();
		next.add(u);
		printlayer();
	}
	// just a straight mountain range
	public void generateTerr(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				board[i][j] = new Space(i,j,new Terrain(1));
			}
			board[i][3] = new Space(i,3,new Terrain(3));
		}
	}
	public void generateMountains(){
		
	}
	public void generateRiver(){
		
	}
	public void generateForest(){
		Random random = new Random();
		int startx = random.nextInt(board.length);
		
	}
	// need better way to create IDs, spawn locations
	public void generateTeam(Team t){
		teams.add(t);
		if(t.tnumber == 1){
		placeUnit(0,0, new Sword(t,1+t.tnumber*10));
		placeUnit(1,0, new Sword(t,2+t.tnumber*10));
		placeUnit(2,0, new Sword(t,3+t.tnumber*10));
		placeUnit(0,1, new Sword(t,4+t.tnumber*10));
		placeUnit(1,1, new Sword(t,5+t.tnumber*10));
		placeUnit(2,1, new Sword(t,6+t.tnumber*10));
		placeUnit(0,2, new Sword(t,7+t.tnumber*10));
		placeUnit(1,2, new Sword(t,8+t.tnumber*10));
		placeUnit(2,2, new Sword(t,9+t.tnumber*10));
		}
		else{
			placeUnit(0,t.tnumber, new Bow(t,1+t.tnumber*10));
			placeUnit(1,t.tnumber, new Bow(t,2+t.tnumber*10));
			placeUnit(2,t.tnumber, new Bow(t,3+t.tnumber*10));
		}
	}
	// other win conditions?
	public boolean win(){
		int count = 0;
		for(Team t: teams){
			if(t.members.size() > 0){
				count++;
			}
		}
		if(count > 1){
			return false;
		}
		return true;
	}
	public void printlayer(){
		System.out.print(turnCount);
		for(int j = ((Integer)turnCount).toString().length(); j < 4; j++){
			System.out.print(" ");
		}
		for(int i = 0; i < board.length; i++){
			System.out.print(i);
			for(int j = ((Integer)i).toString().length(); j < 4; j++){
				System.out.print(" ");
			}
		}
		System.out.println();
		for(int i = 0; i < board.length; i++){
			System.out.print(i);
			for(int j = ((Integer)i).toString().length(); j < 4; j++){
				System.out.print(" ");
			}
			for(int j = 0; j < board[i].length; j++){
				if(!board[i][j].occ)
					System.out.print(board[i][j].terr + "   ");
				else {
					System.out.print(board[i][j].terr + "" + board[i][j].unit + "" + board[i][j].unit.health + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	public void attack(Unit u, Unit t){
		//DAMAGE CALC HERE?
		t.defend(5);
	}
	public void placeUnit(int x, int y, Unit type){
		Space use = board[x][y];
		if(!use.occ){
			use.unit = type;
			use.occ = true;
			type.setLoc(x, y);
			type.setIMap(this);
			type.team.addMember(type);
			next.add(type);
			units.put(type.id, type);
		} else {
			throw new RuntimeException("Space " + x + ", " + y + " is already occupied!");
		}
	}
	public void moveUnit(Unit u, int x, int y){
		Space use = board[x][y];
		if(!use.occ){
			removeUnit(u);
			use.unit = u;
			use.occ = true;
		} else {
			throw new RuntimeException("Space " + x + ", " + y + " is already occupied!");
		}
	}
	public Space[] findNeighbors(Space s){
		Space[] neighbors = new Space[4];
		if(s.yloc > 0){
			neighbors[0] = board[s.xloc][s.yloc - 1];
		}
		if(s.xloc < board.length - 1){
			neighbors[1] = board[s.xloc + 1][s.yloc];
		}
		if(s.yloc < board[0].length - 1){
			neighbors[2] = board[s.xloc][s.yloc + 1];
		}
		if(s.xloc > 0){
			neighbors[3] = board[s.xloc - 1][s.yloc];
		}
		return neighbors;
	}
	public void removeUnit(Unit u){
		board[u.xloc][u.yloc].unit = null;
		board[u.xloc][u.yloc].occ = false;
	}
	public Unit getUnit(int xloc, int yloc){
		return board[xloc][yloc].unit;
	}
	public int getTerr(int x, int y) {
		return board[x][y].terr.getMoveCost();
	}
	public boolean getOcc(int x, int y){
		return board[x][y].occ;
	}
}
