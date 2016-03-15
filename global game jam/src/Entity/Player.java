package Entity;

import java.awt.*;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.util.SwingTimer;



public class Player extends Entity{
	
	public static ArrayList<Bullet> bulletsList = new ArrayList<Bullet>();
	private int fireRate = 100;	// temps entre chaque tire en ms
	private long current,previous = 0,elapsed = 0,elapsedDead = 0,elapsedRitual = 0;
	private float ritualPercentage = 50f,power = 0.0f,spell = 0.0f,powerLevel = 0, spellLevel = 0;
	private boolean isDead = false;
	public boolean inBoss = false;
	
	private double minX = Jeu.Jeu.MARGIN + 0.11, maxX = Jeu.Jeu.MARGIN + Jeu.Jeu.SIZE_OF_GAME - 0.173, minY = 0.3, maxY = 0.8;
	
	private double xCenterSpell, yCenterSpell;
	private GImage imageSpell = new GImage("res/textures/spell.png");
	private double widthSpell = imageSpell.getWidth(), heightSpell = imageSpell.getHeight();
	private SwingTimer timerAnimSpell = new SwingTimer(20, null);
	private int nbRepAnimSpell = 0;
	private boolean isInvul = false;
	
	public Player(){
		super("res/textures/joueur.png");
		moveSpeed = 0.008;
		
		setPosition(0.38, 0.8);
		current = System.currentTimeMillis();
	}
	
	public ArrayList<Bullet> getBulletsList(){
		return bulletsList;
	}
	
	public void addBullet(String name, double xTarget, double yTarget, int damage){
		bulletsList.add(new Bullet(name, x + (image.getWidth() / 2) / Jeu.Jeu.resolution.getWidth(), y , xTarget, yTarget, 0.018, damage));
	}
	
	public boolean isTimeOut(){
		return elapsed >= fireRate;
	}
	
	public void resetTimer(){
		elapsed -= fireRate;
	}
	
	public void setTimerTo0(){
		current = System.currentTimeMillis();
		previous = 0;
		elapsed = 0;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
		if(isDead){
			setPosition(0.38, 0.8);
			powerLevel--;
			if(powerLevel <0 )powerLevel = 0;
			if(powerLevel == 0){
				ritualPercentage -= 10;
			}
			elapsedDead = 0;
		}
	}

	public void update() {
		previous = current;
		current = System.currentTimeMillis();
		elapsed += current - previous;
		elapsedDead += current - previous;
		if(!Jeu.Jeu.jeu.world.getBossSpawned())elapsedRitual += current - previous;
		try{for(Bullet b : bulletsList)b.update();}catch(Exception e){}
		if(ritualPercentage>100)ritualPercentage=100;
		if(ritualPercentage<0)ritualPercentage=0;
		if(power>100)power=100;
		if(power<0)power=0;
		if(spell>100)spell=100;
		if(spell<0)spell=0;
		if(elapsedDead <= 5000 && isDead){
			isInvul = true;
		}
		if(elapsedDead > 5000 && isDead){
			isDead = false;
			isInvul = false;
			elapsedDead = 0;
			image.setVisible(true);
		}
		if(isDead){
			if(System.currentTimeMillis()%500 < 250){
				image.setVisible(false);
			}else{
				image.setVisible(true);
			}
		}
		if(elapsedRitual>=3000 && !Jeu.Jeu.jeu.world.getBossSpawned()){
			ritualPercentage -= 1;
			elapsedRitual -= 3000;
		}
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

	public float getRitualPercentage() {
		return ritualPercentage;
	}

	public void setRitualPercentage(float ritualPercentage) {
		this.ritualPercentage = ritualPercentage;
	}

	public void setBulletsList(ArrayList<Bullet> bulletsList) {
		this.bulletsList = bulletsList;
	}
	
	
	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
		if(this.power >= 100){
			this.powerLevel++;
			this.power -= 100;
		}
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxY() {
		return maxY;
	}
	
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
	
	public void move(double x, double y){
		x += this.x;
		y += this.y;
		
		if(x < minX)	x = minX;
		if(x > maxX)	x = maxX;
		if(y < minY)	y = minY;
		if(y > maxY)	y = maxY;
		image.setLocation(Jeu.Jeu.resolution.getWidth() * x, Jeu.Jeu.resolution.getHeight() * y);
		this.x = x;
		this.y = y;
	}
	
	public float getSpell() {
		return spell;
	}
	
	public void setSpell(float spell) {
		this.spell = spell;
		if(this.spellLevel == 3){
			if(this.spell >= 100){
				this.spell = 100;
			}
		}
		else if(this.spell >= 100){
			this.spellLevel++;
			this.spell -= 100;
		}
	}
	
	public int getPowerLevel() {
		return (int)powerLevel;
	}
	
	public void setPowerLevel(float powerLevel) {
		this.powerLevel = powerLevel;
	}
	
	public int getSpellLevel() {
		return (int)spellLevel;
	}
	
	public void setSpellLevel(float spellLevel) {
		this.spellLevel = spellLevel;
	}
	
	public double getXCenterSpell(){
		return xCenterSpell;
	}
	
	public double getYCenterSpell(){
		return yCenterSpell;
	}
	
	public void setXCenterSpell(double p){
		xCenterSpell = p;
	}
	
	public void setYCenterSpell(double p){
		yCenterSpell = p;
	}
	
	public GImage getImageSpell(){
		return imageSpell;
	}
	
	public SwingTimer getTimerAnimSpell(){
		return timerAnimSpell;
	}
	
	public int getNbRepAnimSpell(){
		return nbRepAnimSpell;
	}
	
	public void setNbRepAnimSpell(int p){
		nbRepAnimSpell = p;
	}
	
	public double getWidthSpell(){
		return widthSpell;
	}
	
	public double getHeightSpell(){
		return heightSpell;
	}

	public long getElapsedDead() {
		return elapsedDead;
	}

	public void setElapsedDead(long elapsedDead) {
		this.elapsedDead = elapsedDead;
	}

	public double getxCenterSpell() {
		return xCenterSpell;
	}

	public void setxCenterSpell(double xCenterSpell) {
		this.xCenterSpell = xCenterSpell;
	}

	public double getyCenterSpell() {
		return yCenterSpell;
	}

	public void setyCenterSpell(double yCenterSpell) {
		this.yCenterSpell = yCenterSpell;
	}

	public boolean isInvul() {
		return isInvul;
	}

	public void setInvul(boolean isInvul) {
		this.isInvul = isInvul;
	}

	public void setImageSpell(GImage imageSpell) {
		this.imageSpell = imageSpell;
	}

	public void setWidthSpell(double widthSpell) {
		this.widthSpell = widthSpell;
	}

	public void setHeightSpell(double heightSpell) {
		this.heightSpell = heightSpell;
	}

	public void setTimerAnimSpell(SwingTimer timerAnimSpell) {
		this.timerAnimSpell = timerAnimSpell;
	}
	
	
}
