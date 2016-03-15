package Entity;

import java.util.ArrayList;

public class ShootingPattern {
	
	protected ArrayList<TimedBullet> listTimedBullet = new ArrayList<TimedBullet>();
	protected int hpFinish;
	protected long duration;
	
	public ShootingPattern(){
	}
	
	public ArrayList<TimedBullet> getListTimedBullet(){
		return listTimedBullet;
	}
	
}
