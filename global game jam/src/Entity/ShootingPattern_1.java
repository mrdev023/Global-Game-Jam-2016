package Entity;

public class ShootingPattern_1 extends ShootingPattern{
	
	public ShootingPattern_1(){
		super();

		listTimedBullet.add(new TimedBullet(0.0, 0.3, "tireau", 0.005, 0));
		listTimedBullet.add(new TimedBullet(-0.03, 0.3, "tireau", 0.005, 0));
		listTimedBullet.add(new TimedBullet(0.03, 0.3, "tireau", 0.005, 0));
		
		hpFinish = 180;
		duration = 800;
	}

}
