
public class Sword extends Unit{
	public Sword(Team t, int i){
		super(t, i);
		damage = 2;
		range = 1;
		health = 9;
		speed = 5;
	}
	/*
	public Sword(Map map, int x, int y, Team t, int currhp) {
		super(t);
		damage = 2;
		range = 1;
	}
	*/
	public String toString(){
		return "S";
	}
}
