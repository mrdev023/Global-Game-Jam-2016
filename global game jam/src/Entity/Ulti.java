package Entity;

import acm.graphics.GImage;
import acm.util.SwingTimer;

public class Ulti extends Entity{

	public static final int TIME = 1000;
	private long current, previous = 0, elapsed = 0,elapsedSacrifice = 0;
	private boolean isSacrifice = false;
	
	public Ulti(double x, double y, String url) {
		super(url);
		setPosition(x, y);
		current = System.currentTimeMillis();
	}

	public void update() {
		if(!Jeu.Jeu.jeu.isGameOver){
			previous = current;
			current = System.currentTimeMillis();
			elapsed += current - previous;
			if(isSacrifice)elapsedSacrifice += current - previous;
			if(elapsed >= TIME){
				Player.bulletsList.add(new Bullet("tir joueur", x + (image.getWidth() / 2) / Jeu.Jeu.resolution.getWidth(), y , x + (image.getWidth() / 2) / Jeu.Jeu.resolution.getWidth(), y - 0.05, 0.018, 1));
				Jeu.Jeu.gc.add(Player.bulletsList.get(Player.bulletsList.size() - 1).getImage());
				elapsed -= TIME;
			}
			
			if(isSacrifice && y > 0.5){
				move(0, -.006);
			}
		}
	}
	
	public void resetTimer(){
		elapsed = 0;
	}
	
	public boolean isSacrifice(){
		return isSacrifice && elapsedSacrifice > 1000;
	}
	
	public void sacrifice(){
		isSacrifice = true;
	}
	
	public void setTimerTo0(){
		current = System.currentTimeMillis();
		previous = 0;
		elapsed = 0;
	}
	
}
