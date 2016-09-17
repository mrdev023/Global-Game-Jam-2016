package Input;

import World.*;
import net.java.games.input.*;
import net.java.games.input.Component.*;
import net.java.games.util.Version;

public class Joystick {
	
	public static Controller con;
	public static float previousValueOfA = 0,previousValueOfX = 0,previousValueOfB = 0;
	public static float x = 0;
	public static float y = 0;

	public static void init(){
		System.out.println("JInput version: " + Version.getVersion()); 
		Controller[] cs = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for(Controller c : cs){
			if(c.getType() == Controller.Type.GAMEPAD){
				System.out.println(c.getType());
				con = c;
				break;
			}else{
				con = null;
			}
		}
	}
	
	public static void update(){
		if(con != null){
			for(Component c : con.getComponents()){
				Identifier componentIdentifier = c.getIdentifier();
				con.poll();
				if(c.isAnalog()){
					float a = c.getPollData();
					if(componentIdentifier == Component.Identifier.Axis.X){
						x = c.getPollData();
						x = (Math.sqrt(x*x) > 0.03)?(x*0.33f):0;
					}
					if(componentIdentifier == Component.Identifier.Axis.Y){
						y = c.getPollData();
						y = (Math.sqrt(y*y) > 0.03)?(y*0.33f):0;
					}
				}
				if(componentIdentifier == Component.Identifier.Button.A || componentIdentifier == Component.Identifier.Button._0){
					if(Jeu.Jeu.jeu.dialogPhase && c.getPollData() == 1.0f && c.getPollData() != previousValueOfA){
						previousValueOfA = 1.0f;
						Jeu.Jeu.jeu.nextDialogueReplique();
					}
					if(c.getPollData() != 1.0f && previousValueOfA == 1.0f){
						previousValueOfA = 0.0f;
					}
				}
				if(componentIdentifier == Component.Identifier.Button.B || componentIdentifier == Component.Identifier.Button._2){
					if(c.getPollData() == 1.0f && c.getPollData() != previousValueOfB){
						previousValueOfB = 1.0f;
						if(!Jeu.Jeu.jeu.dialogPhase && World.getUltiList().size() > 0){
							switch(World.getUltiList().size()){
							case 4:
								World.getUltiList().get(0).sacrifice();
								break;
							case 3:
								World.getUltiList().get(2).sacrifice();
								break;
							default:
								World.getUltiList().get(0).sacrifice();
								break;
							}
						}
					}
					if(c.getPollData() != 1.0f && previousValueOfB == 1.0f){
						previousValueOfB = 0.0f;
					}
				}
				if(componentIdentifier == Component.Identifier.Button.X || componentIdentifier == Component.Identifier.Button._1){
					if(!Jeu.Jeu.jeu.dialogPhase && c.getPollData() == 1.0f && c.getPollData() != previousValueOfX){
						previousValueOfX = 1.0f;
						if(Jeu.Jeu.jeu.player.getNbRepAnimSpell() == 0 && Jeu.Jeu.jeu.player.getSpellLevel() >= 1 && !Jeu.Jeu.jeu.dialogPhase){
							Jeu.Jeu.jeu.player.setSpellLevel(Jeu.Jeu.jeu.player.getSpellLevel() -1);
							Jeu.Jeu.jeu.player.setXCenterSpell(Jeu.Jeu.jeu.player.getX() + (Jeu.Jeu.jeu.player.getImage().getWidth() / 2) / Jeu.Jeu.jeu.resolution.getWidth());
							Jeu.Jeu.jeu.player.setYCenterSpell(Jeu.Jeu.jeu.player.getY() + (Jeu.Jeu.jeu.player.getImage().getHeight() / 2) / Jeu.Jeu.jeu.resolution.getHeight());
							Jeu.Jeu.jeu.player.getTimerAnimSpell().start();
							Jeu.Jeu.jeu.player.getImageSpell().setSize((Jeu.Jeu.jeu.player.getWidthSpell() * (double)Jeu.Jeu.jeu.player.getNbRepAnimSpell()) / 5.0, (Jeu.Jeu.jeu.player.getHeightSpell() * (double)Jeu.Jeu.jeu.player.getNbRepAnimSpell()) / 5.0);
							Jeu.Jeu.jeu.player.getImageSpell().setLocation(Jeu.Jeu.jeu.resolution.getWidth() * Jeu.Jeu.jeu.player.getXCenterSpell() - Jeu.Jeu.jeu.player.getImageSpell().getHeight() / 2.0,
									Jeu.Jeu.jeu.resolution.getHeight() * Jeu.Jeu.jeu.player.getYCenterSpell() - Jeu.Jeu.jeu.player.getImageSpell().getWidth() / 2.0);
							Jeu.Jeu.jeu.gc.add(Jeu.Jeu.jeu.player.getImageSpell());
						}
					}
					if(c.getPollData() != 1.0f && previousValueOfX == 1.0f){
						previousValueOfX = 0.0f;
					}
				}
				if(componentIdentifier == Component.Identifier.Button.LEFT_THUMB || componentIdentifier == Component.Identifier.Button._4){
					if(!Jeu.Jeu.jeu.dialogPhase && c.getPollData() == 1.0f){
						Jeu.Jeu.jeu.keyShift = true;
					}else{
						Jeu.Jeu.jeu.keyShift = false;
					}
				}
			}
		}
	}
	
}
