package Entity;

public class ShootingPattern_0 extends ShootingPattern{
	
	public ShootingPattern_0(){
		super();

		listTimedBullet.add(new TimedBullet("grosse boule", 0.0065, 0));
		listTimedBullet.add(new TimedBullet("grosse boule", 0.0065, 1000));
		listTimedBullet.add(new TimedBullet("grosse boule", 0.0065, 2000));
		listTimedBullet.add(new TimedBullet("grosse boule", 0.0065, 3000));
		listTimedBullet.add(new TimedBullet("grosse boule", 0.0065, 4000));
		
		hpFinish = 0;
		duration = 6000;
	}
}
