package Items;

import Entity.*;
import acm.graphics.*;

public abstract class Items {

	private double x,y;
	private GImage image;
	
	public Items(double x,double y,String url){
		image = new GImage(url);
		image.setLocation(x,y);
		this.x = x;
		this.y = y;
		Jeu.Jeu.gc.add(image);
	}
	
	public void move(double x,double y){
		image.move(x, y);
		x = image.getX();
		y = image.getY();
	}
	
	public abstract void action(Player player);

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public void delete(){
		Jeu.Jeu.gc.remove(image);
	}

	public void setY(double y) {
		this.y = y;
	}

	public GImage getImage() {
		return image;
	}

	public void setImage(GImage image) {
		this.image = image;
	}
	
	
	
}
