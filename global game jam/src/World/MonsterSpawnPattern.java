package World;

import Entity.Monster;


/*
 * définit le momant d'apparition d'un mob et le mob lui-même
 */
public class MonsterSpawnPattern {
	private Monster monster;
	private long spawnTimer;
	
	public MonsterSpawnPattern(Monster monster, long spawnTimer){
		this.monster = monster;
		this.spawnTimer = spawnTimer;
		
		this.monster.setTimerTo0();
	}
	
	public Monster getMonster(){
		return monster;
	}
	
	public long getSpawnTimer(){
		return spawnTimer;
	}
}
