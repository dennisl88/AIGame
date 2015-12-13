
public class Unit implements Comparable<Unit>{
	public int xloc;
	public int yloc;
	public int nextTick;
	public int speed;
	public int damage;
	public int range;
	public int health;
	public int id;
	public Team team;
	public Map imap;
	public PathNode[][] pathing;
	public Unit(Team t, int i){
		team = t;
		id = i;
		nextTick = 0; 
	}
	/*
	public Unit(Team t, int currhp){
		team = t;
		health = currhp;
	}*/
	public void setIMap(Map map){
		imap = map;
	}
	// MAY WANT TO CHANGE NEXT 2 METHODS TO SPACE? MAY CHANGE EVERYTHING ELSE TO COORDS
	public void move(int x, int y){
		imap.moveUnit(this, x, y);
		setLoc(x, y);
	}
	public void setLoc(int x, int y){
		xloc = x;
		yloc = y;
	}
	public void attack(Unit t){
		imap.attack(this, t);
	}
	public void defend(int x){
		health -= x;
		if(health <= 0){
			die();
		}
	}
	public void die(){
		imap.removeUnit(this);
		imap.units.remove(this.id);
		imap.next.remove(this);
		team.members.remove(this.id);
		xloc = -1;
		yloc = -1;
	}
	public void act(){
		if(team.command() == 0){
			runBasic();
		}
	}
	public int getDistance(Space space){
		return Math.abs(space.xloc-xloc) + Math.abs(space.yloc-yloc);
	}
	/* THIS ALLOWS FOR DIAGONAL MOVES
	public int getDistance(Space space){
		int xdist = Math.abs(space.xloc - xloc);
		int ydist = Math.abs(space.yloc - yloc);
		if(xdist > ydist)
			return xdist;
		return ydist;
	}
	 */
	public void runBasic(){
		Unit u = findNearestEnemy();
		if(isInRange(u)){
			attack(u);
			nextTick += speed;
		} else {
			Space s = findPathTo(imap.board[u.xloc][u.yloc]);
			if(s.xloc == xloc && s.yloc == yloc){
				nextTick = imap.next.peek().nextTick+1;
			} else {
				move(s.xloc,s.yloc);
				nextTick += imap.board[xloc][yloc].terr.movecost*speed;
				System.out.println(nextTick);
			}
		}
	}
	public boolean isInRange(Unit u){
		if(getDistance(imap.board[u.xloc][u.yloc]) <= range){
			return true;
		}
		return false;
	}
	public Unit findNearestEnemy(){
		Unit near = null;
		int min = Integer.MAX_VALUE;
		for(Unit u:imap.units.values()){
			int dist = getDistance(imap.board[u.xloc][u.yloc]);
			if(dist < min && u.team != team){
				near = u;
				min = dist;
			}
		}
		return near;
	}
	public Space findPathTo(Space s){
		pathing = new PathNode[imap.board.length][imap.board[0].length];
		pathing[xloc][yloc] = new PathNode(0, null);
		Space curr = imap.board[xloc][yloc];
		while(curr.getDistance(s) > 1){
			Space[] neighbors = imap.findNeighbors(curr);
			int blocked = 0;
			for(Space test: neighbors){
				if(test != null && !test.occ){
					if(pathing[test.xloc][test.yloc] != null){
						int moveCost = pathing[curr.xloc][curr.yloc].cost + test.terr.getMoveCost();
						if(pathing[test.xloc][test.yloc] != null && moveCost < pathing[test.xloc][test.yloc].cost){
							pathing[test.xloc][test.yloc] = new PathNode((pathing[curr.xloc][curr.yloc].cost + test.terr.getMoveCost()), curr);
						}
					} else {
						pathing[test.xloc][test.yloc] = new PathNode((pathing[curr.xloc][curr.yloc].cost + test.terr.getMoveCost()), curr);
					}
				} else {
					blocked++;
				}
			}
			if(blocked == 4){
				return imap.board[xloc][yloc];
			}
			pathing[curr.xloc][curr.yloc].visit();
			int min = Integer.MAX_VALUE;
			int minx = 0;
			int miny = 0;
			for(int i = 0; i < pathing.length; i++){
				for(int j = 0; j < pathing[0].length; j++){
					if(pathing[i][j] != null && !pathing[i][j].visited && pathing[i][j].cost < min){
						minx = i;
						miny = j;
						min = pathing[i][j].cost;
					}
				}
			}
			curr = imap.board[minx][miny];
			//printlayer();
		}
		while(!pathing[curr.xloc][curr.yloc].prev.equals(imap.board[xloc][yloc])){
			curr = pathing[curr.xloc][curr.yloc].prev;
		}
		return curr;
	}
	public void printlayer(){
		System.out.print("   ");
		for(int i = 0; i < pathing.length; i++){
			System.out.print(i);
			for(int j = ((Integer)i).toString().length(); j < 3; j++){
				System.out.print(" ");
			}
		}
		System.out.println();
		for(int i = 0; i < pathing.length; i++){
			System.out.print(i);
			for(int j = ((Integer)i).toString().length(); j < 2; j++){
				System.out.print(" ");
			}
			for(int j = 0; j < pathing[i].length; j++){
				if(pathing[i][j] != null){
					System.out.print(" " + pathing[i][j].cost + " ");
				} else {
					System.out.print(" X ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	@Override
	public int compareTo(Unit o) {
		if(nextTick == o.nextTick){
			return id - o.id;
		}
		return nextTick-o.nextTick;
	}
	public String toString(){
		return "";
	}
	public String printID(){
		return id + " " + nextTick;
	}
}
