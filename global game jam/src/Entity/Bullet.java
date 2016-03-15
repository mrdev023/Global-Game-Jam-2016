package Entity;

import java.awt.Dimension;

public class Bullet extends Entity{
	
	private int damage;
	
	public Bullet(String name, double xStart, double yStart, double xTarget, double yTarget, double moveSpeed){
		super("res/textures/" + name + ".png");
		
		xStart -= (image.getWidth() / 2) / Jeu.Jeu.resolution.getWidth();
		yStart -= (image.getHeight() / 2) / Jeu.Jeu.resolution.getHeight();
		xTarget -= (image.getWidth() / 2) / Jeu.Jeu.resolution.getWidth();
		yTarget -= (image.getHeight() / 2) / Jeu.Jeu.resolution.getHeight();
		
		setPosition(xStart, yStart);
		
		double longDeplaTT = xTarget - x;	// orienté
		double hautDeplaTT = yTarget - y;	// orienté
		double hypo = Math.sqrt((longDeplaTT * longDeplaTT) + (hautDeplaTT * hautDeplaTT));
		setVectorX(longDeplaTT / hypo);
		setVectorY(hautDeplaTT / hypo);
		
		super.moveSpeed = moveSpeed;
	}
	
	public Bullet(String name, double xStart, double yStart, double xTarget, double yTarget, double moveSpeed, int damage){
		this(name, xStart, yStart, xTarget, yTarget, moveSpeed);
		this.damage = damage;
	}
	
	// boss untargeted bullet
	public Bullet(TimedBullet tB, double xS, double yS){
		this(tB.name, xS, yS, xS + tB.x, yS + tB.y, tB.moveSpeed);
	}

	// boss targeted bullet
	public Bullet(TimedBullet tB, double xS, double yS, double xT, double yT){
		this(tB.name, xS, yS, xT, yT, tB.moveSpeed);
	}
	
	public int getDamage(){
		return damage;
	}

	@Override
	public void update(){
		// TODO Auto-generated method stub
		
	}
}
