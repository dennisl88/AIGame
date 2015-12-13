import java.util.HashMap;

public class Team {
	HashMap<Integer, Unit> members;
	int tnumber;
	public Team(int x){
		tnumber = x;
		members = new HashMap<Integer, Unit>();
	}
	public void addMember(Unit u){
		members.put(u.id, u);
	}
	public int command(){
		return 0;
	}
}
