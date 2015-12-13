
public class Bow extends Sword{
	public Bow(Team t, int i){
		super(t, i);
		damage = 2;
		range = 4;
		health = 9;
		speed = 5;
	}
	public String toString(){
		return "B";
	}
}
