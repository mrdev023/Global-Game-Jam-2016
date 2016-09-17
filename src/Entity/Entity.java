package Entity;

import java.awt.Dimension;

import acm.graphics.GImage;

public abstract class Entity {

	protected double x,y;
	protected double vectorX = 0.0, vectorY = 0.0;
	protected GImage image;
	protected double moveSpeed = 5.0;
	
	public Entity(int x,int y,String url){
		this.x = x;
		this.y = y;
		this.image = new GImage(url, Jeu.Jeu.resolution.getWidth() * x, Jeu.Jeu.resolution.getHeight() * y);
		
		image.setSize(image.getWidth() / 1920.0 * Jeu.Jeu.resolution.getWidth(), image.getHeight() / 1080.0 * Jeu.Jeu.resolution.getHeight());
	}
	
	public Entity(String url){
		this(-200,-200,url);
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getVectorX(){
		return vectorX;
	}

	public double getVectorY(){
		return vectorY;
	}

	public void setVectorX(double vectorX){
		this.vectorX = vectorX;
	}

	public void setVectorY(double vectorY){
		this.vectorY = vectorY;
	}
	
	public void setPosition(double x,double y){

		image.setLocation(Jeu.Jeu.resolution.getWidth() * x, Jeu.Jeu.resolution.getHeight() * y);
		this.x = x;
		this.y = y;
	}
	
	public void move(double x, double y){
		image.move(Jeu.Jeu.resolution.getWidth() * x, Jeu.Jeu.resolution.getHeight() * y);
		this.x += x;
		this.y += y;
	}
	
	public GImage getImage() {
		return image;
	}

	public void setImage(GImage image) {
		this.image = image;
	}
	
	public double getMoveSpeed(){
		return moveSpeed;
	}
	
	public void setMoveSpeed(double moveSpeed){
		this.moveSpeed = moveSpeed;
	}
	
	public abstract void update();
	
}
