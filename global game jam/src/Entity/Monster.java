package Entity;

import java.io.*;
import java.util.*;

import Items.*;
import Tools.*;

public class Monster extends Entity{
	private int fireRate;	// temps entre chaque tire en ms
	protected long current,previous = 0,elapsed = 0;
	protected int HP;
	
	private ArrayList<Coord> pattern = new ArrayList<Coord>();
	private int indicePattern = 0;
	private boolean fireModeFocus;
	
	private float dropChance;
	private int scoreDead;
	
	public Monster(String name, String patternName, int HP, double moveSpeed, int fireRate, boolean fireModeFocus, float dropChance,int scoreDead){
		super("res/textures/" + name + ".png");
		this.moveSpeed = moveSpeed;
		this.HP = HP;
		this.fireRate = fireRate;
		this.fireModeFocus = fireModeFocus;
		this.dropChance = dropChance;
		this.scoreDead = scoreDead;
		
		try{
			File fichier = new File("res/donnees/" + patternName + ".txt");
			Scanner fEntree = new Scanner(new BufferedInputStream(new FileInputStream(fichier)));
			String line, tab[];
			
			while(fEntree.hasNext()){
				
				line = fEntree.nextLine();
				tab = line.split(" ");
				pattern.add(new Coord(Integer.parseInt(tab[0]), Integer.parseInt(tab[1])));
			}
			fEntree.close();
		}
		catch(IOException e){
			System.out.println("ERREUR DE LECTURE du fichier : " + patternName + ".txt");
		}
		
		current = System.currentTimeMillis();
		previous = 0;
		elapsed = 0;
	}
	
	public void setTimerTo0(){
		current = System.currentTimeMillis();
		previous = 0;
		elapsed = 0;
	}
	
	public void drop(){
		if(Math.random() <= dropChance){
			if(Math.random()>0.5){
				World.World.getItemsList().add(new PowerItems(image.getX(),image.getY()));
			}else{
				World.World.getItemsList().add(new SpellItems(image.getX(),image.getY()));
			}
		}
	}
	
	public void addBullet(double xTarget, double yTarget){
		World.World.getBulletsList().add(new Bullet("tireau", x + (image.getWidth() / 2) / Jeu.Jeu.resolution.getWidth(), y + image.getHeight() / Jeu.Jeu.resolution.getHeight(), xTarget, yTarget, 0.0085));
	}
	
	public boolean isTimeOut(){
		return elapsed >= fireRate;
	}
	
	public void resetTimer(){
		elapsed -= fireRate;
	}
	
	public void update(){
		previous = current;
		current = System.currentTimeMillis();
		elapsed += current - previous;
	}
	
	public ArrayList<Coord> getPattern(){
		return pattern;
	}
	
	public int getIndicePattern(){
		return indicePattern;
	}
	
	
	
	public int getScoreDead() {
		return scoreDead;
	}

	public void setScoreDead(int scoreDead) {
		this.scoreDead = scoreDead;
	}

	public boolean incrementIndicePattern(){
		indicePattern++;
		if(indicePattern == pattern.size())	return false;
		else return true;
	}
	
	public boolean getFireModeFocus(){
		return fireModeFocus;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public long getCurrent() {
		return current;
	}

	public void setCurrent(long current) {
		this.current = current;
	}

	public long getPrevious() {
		return previous;
	}

	public void setPrevious(long previous) {
		this.previous = previous;
	}

	public long getElapsed() {
		return elapsed;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public float getDropChance() {
		return dropChance;
	}

	public void setDropChance(float dropChance) {
		this.dropChance = dropChance;
	}

	public void setPattern(ArrayList<Coord> pattern) {
		this.pattern = pattern;
	}

	public void setIndicePattern(int indicePattern) {
		this.indicePattern = indicePattern;
	}

	public void setFireModeFocus(boolean fireModeFocus) {
		this.fireModeFocus = fireModeFocus;
	}
	
	
}
