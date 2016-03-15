package World;

import java.awt.event.*;
import java.util.ArrayList;

import Tools.CoordD;
import Entity.Boss;
import Entity.MonsterModel_chauve_souris_focus_bomb;
import Entity.MonsterModel_chauve_souris_focus_cross;
import Entity.MonsterModel_chauve_souris_focus_cube;
import Entity.MonsterModel_chauve_souris_focus_feint;
import Entity.MonsterModel_chauve_souris_focus_loop;
import Entity.MonsterModel_chauve_souris_focus_trace;
import Entity.MonsterModel_monstre;
import Entity.MonsterModel_squelette_focus_bomb;
import Entity.MonsterModel_squelette_focus_loop;
import Entity.ShootingPattern;
import Entity.ShootingPattern_0;
import Entity.ShootingPattern_1;

public class Stage1 extends World{
	
	public Stage1() {
		super("Stage1",new String[]{"res/audio/stage_1.wav"},"res/textures/abg.png");
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 6000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 6500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 7000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 7500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 8000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 8500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 9000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 9500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 10000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cross(), 10500));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_squelette_focus_bomb(), 18000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 22000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 22500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 23000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 23500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 24000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 24500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 25000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 25500));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 36000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 37000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 38000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 39000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_feint(), 44000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_feint(),46000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_squelette_focus_loop(), 52000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_squelette_focus_loop(), 52500));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_squelette_focus_loop(), 53000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_squelette_focus_loop(), 53500));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 70000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 72000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 74000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 76000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 71000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 73000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 75000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 77000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 78000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 80000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 82000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_cube(), 84000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 79000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 81000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 83000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 85000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_bomb(), 95000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_trace(), 95000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_trace(), 98000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 102000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 104000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 106000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_loop(), 108000));
		
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_trace(), 128000));
		monsterSpawnPattern.add(new MonsterSpawnPattern(new MonsterModel_chauve_souris_focus_trace(), 130000));
		
		
		listSpeach.add(new Speach("intro"));
		listSpeach.add(new Speach("fin_stage_1_0m"));
		listSpeach.add(new Speach("fin_stage_1_1m"));
		listSpeach.add(new Speach("fin_stage_1_2m"));
		listSpeach.add(new Speach("fin_stage_1_3m"));
		listSpeach.add(new Speach("fin_stage_1_4m"));
		
		ArrayList<CoordD> spotCoordDs = new ArrayList<CoordD>();
		spotCoordDs.add(new CoordD(0.1, 0.3));
		spotCoordDs.add(new CoordD(0.35, 0.5));
		spotCoordDs.add(new CoordD(0.2, 0.05));
		
		ArrayList<ShootingPattern> listShootingPattern = new ArrayList<ShootingPattern>();
		listShootingPattern.add(new ShootingPattern_1());
		listShootingPattern.add(new ShootingPattern_0());
		
		boss = new Boss("boss", "pattern_boss_0", 300, 0.010, 15000, spotCoordDs, listShootingPattern);
	}
	
	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		
	}
	
	
	public void update() {
		
	}

}
