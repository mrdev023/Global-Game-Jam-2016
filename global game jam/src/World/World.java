package World;

import java.awt.event.*;
import java.util.*;

import Audio.*;
import Entity.*;
import Items.*;

public abstract class World {
	
	private String stageName = "";
	private String backgroundImage;
	private static ArrayList<Monster> monstersList = new ArrayList<Monster>();
	private static ArrayList<Bullet> bulletsList = new ArrayList<Bullet>();
	private static ArrayList<Bullet> bossBulletsList = new ArrayList<Bullet>();
	private static ArrayList<Items> itemsList = new ArrayList<Items>();
	private static ArrayList<Ulti> ultiList = new ArrayList<Ulti>();
	protected static Boss boss;
	private boolean bossSpawned = false;
	
	protected ArrayList<MonsterSpawnPattern> monsterSpawnPattern = new ArrayList<MonsterSpawnPattern>();
	protected ArrayList<Speach> listSpeach = new ArrayList<Speach>();
	
	public World(String stageName,String[] ambientSound,String backgroundUrl){
		this.stageName = stageName;
		for(String a : ambientSound){
			AudioManager.addAmbientSound(stageName, a);
		}
//		this.backgroundImage = new GImage(backgroundUrl);
	}
	
	public abstract void keyPressed(KeyEvent e);
	
	public abstract void keyReleased(KeyEvent e);
	
	public abstract void keyTyped(KeyEvent e);
	
	public abstract void actionPerformed(ActionEvent e);
	
	public void updateWorld(){
		try{
			for(Monster m : monstersList){
				m.update();
			}
			for(Bullet b : bulletsList){
				b.update();
			}
			for(Bullet b : bossBulletsList){
				b.update();
			}
			if(bossSpawned)	boss.update();
		}
		catch(Exception e){}
	}
	
	public abstract void update();

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	
	public ArrayList<Monster> getMonstersList(){
		return monstersList;
	}
	
	public static ArrayList<Bullet> getBulletsList(){
		return bulletsList;
	}
	
	public static ArrayList<Bullet> getBossBulletsList(){
		return bossBulletsList;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public ArrayList<MonsterSpawnPattern> getMonsterSpawnPattern() {
		return monsterSpawnPattern;
	}

	public void setMonsterSpawnPattern(ArrayList<MonsterSpawnPattern> monsterSpawnPattern) {
		this.monsterSpawnPattern = monsterSpawnPattern;
	}

	public static void setMonstersList(ArrayList<Monster> monstersList) {
		World.monstersList = monstersList;
	}

	public static void setBulletsList(ArrayList<Bullet> bulletsList) {
		World.bulletsList = bulletsList;
	}

	public static ArrayList<Items> getItemsList() {
		return itemsList;
	}

	public static void setItemsList(ArrayList<Items> itemsList) {
		World.itemsList = itemsList;
	}

	public static ArrayList<Ulti> getUltiList() {
		return ultiList;
	}

	public static void setUltiList(ArrayList<Ulti> ultiList) {
		World.ultiList = ultiList;
	}
	
	public ArrayList<Speach> getListSpeach(){
		return listSpeach;
	}
	
	public Boss getBoss(){
		return boss;
	}
	
	public boolean getBossSpawned(){
		return bossSpawned;
	}
	
	public void setBossSpawned(boolean p){
		bossSpawned = p;
	}
}
