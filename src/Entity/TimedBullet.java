package Entity;

public class TimedBullet{
	
	public boolean isFocusOnPlayer, isAlreadyShot;
	public String name;
	public double x, y, moveSpeed;
	public long shotTimer;
	
	public TimedBullet(String name, double moveSpeed, long shotTimer){
		this.isFocusOnPlayer = true;
		this.name = name;
		this.moveSpeed = moveSpeed;
		this.shotTimer = shotTimer;
		isAlreadyShot = false;
	}
	
	public TimedBullet(double x, double y, String name, double moveSpeed, long shotTimer){
		this.isFocusOnPlayer = false;
		this.x = x;
		this.y = y;
		this.name = name;
		this.moveSpeed = moveSpeed;
		this.shotTimer = shotTimer;
		isAlreadyShot = false;
	}
}
