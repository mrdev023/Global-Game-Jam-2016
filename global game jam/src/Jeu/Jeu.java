package Jeu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import Audio.*;
import Entity.*;
import IO.*;
import Input.*;
import Items.*;
import World.*;
import acm.graphics.*;
import acm.util.*;

public class Jeu  extends JFrame implements ActionListener, KeyListener{
	
	// systeme -----------------------------------------------------------------
	public static Dimension resolution;
	public static int nbreRender = 0;
	public static GCanvas gc = new GCanvas();
	public EcranListeners pan;
	public boolean isGameOver = false;
	
	// autre -------------------------------------------------------------------
	public Player player;
	public boolean keyUp = false, keyRight = false, keyDown = false, keyLeft = false, keyShift = false;
	public SwingTimer refresh = new SwingTimer(25, this);
	public GLabel dialogConfirmLabel,nbreRenderLabel,updateDebugLabel,inputDebugLabel,ritualDebugLvl,powerLabel,spellLabel,scoreLabel,highScoreLabel;
	
	public ArrayList<GLabel> dialogLabel = new ArrayList<GLabel>();
	public boolean dialogPhase = false;
	public GImage imageTetePerso = new GImage("res/textures/cadre perso.png");
	public GImage dialogImage;
	public Speach actualSpeach;
	public int indiceSpeachProgression;
	
	
	public GRect badRitualLevel,ritualLevel,powerLevel,spellLevel;
	public GImage vertProgressImage,vertProgressLImage,vertProgressImage2,vertProgressLImage2;
	public boolean isDebugMode = false,intro = true;
	public static int score = 0,highscore = 0;
	public static GImage backgroundGameImage[] = new GImage[3];
	
	public GImage fondMenu = new GImage("res/textures/fondMenu.png"),
			boutonJouer = new GImage("res/textures/boutonjouer.png"),
			boutonQuitter = new GImage("res/textures/boutonquitter.png");
	public boolean menuActif = false;
	
	public GImage imagesIntro[] = new GImage[3];
	public int currentIntro = 0;
	
	// Variable constante ------------------------------------------------------
	public static final float SIZE_OF_GAME = 68.75f/100.0f;
	public static final float MARGIN = 6.25f/100.0f;
	public static final float WIDTH_OF_STATS = 18.75f/100.0f;
	
	public ArrayList<Bullet> bulletsListPoubelle = new ArrayList<Bullet>();
	public ArrayList<Monster> monstersListPoubelle = new ArrayList<Monster>();
	
	public World world;
	public long currentTimeStage, startTimeStage, startTimePause = 0, currentTimePause = 0;
	
	public static Thread thread = null;
	
	// Ulti -----------------------------------------------------------------------
	
	public double xCenterUlti, yCenterUlti;
	public GImage imageUlti = new GImage("res/textures/ulti.png");
	public double widthUlti = imageUlti.getWidth(), heightUlti = imageUlti.getHeight();
	public SwingTimer timerAnimUlti = new SwingTimer(20, this);
	public int nbRepAnimUlti = 0;
	
	public static Jeu jeu;
	
	public Jeu(){
		String save = IOFile.getStringByFile("res/donnees/highscore.txt");
		try{if(save!="")highscore = Integer.parseInt(save.split(" ")[0]);}catch(Exception e){e.printStackTrace();}
		this.setUndecorated(true);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		resolution = new Dimension(gs[0].getDisplayMode().getWidth(), gs[0].getDisplayMode().getHeight());
		this.setSize(resolution);
		System.out.println("ACM loaded screen : " + resolution.getWidth() + "x" + resolution.getHeight());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.validate();
		this.setVisible(true);
		pan = new EcranListeners(resolution);
		gc.add(pan);
		gc.setBackground(Color.BLACK);
	    this.setContentPane(gc);
		this.show();
		
		pan.sourisClicked.addActionListener(this);
		pan.sourisDragged.addActionListener(this);
		pan.sourisEntered.addActionListener(this);
		pan.sourisExited.addActionListener(this);
		pan.sourisMoved.addActionListener(this);
		pan.sourisPressed.addActionListener(this);
		pan.sourisReleased.addActionListener(this);
		
		this.addKeyListener(this);
		
		player = new Player();
		player.getTimerAnimSpell().addActionListener(this);
		
		Joystick.init();
		initMenuP();/*
		initJeu();
		
		thread = (new Thread(new Runnable() {
			public void run() {
				while(true){
					loop();
					try{
						Thread.sleep(1000/120);
					}catch(Exception e){}
				}
			}
		}));
		thread.start();
		*/
	}
	
	
	public void initMenuP(){
		fondMenu.setSize(resolution.getWidth(), resolution.getHeight());
		gc.add(fondMenu);
		
		boutonJouer.setLocation(0.029 * resolution.getWidth(), 0.3 * resolution.getHeight());
		gc.add(boutonJouer);
		
		boutonQuitter.setLocation(0.02 * resolution.getWidth(), 0.6 * resolution.getHeight());
		gc.add(boutonQuitter);
		
		menuActif = true;
	}
	
	public void initIntro(){
		currentIntro = 0;
		imagesIntro[0] = new GImage("res/textures/Asg intro 1.png");
		imagesIntro[0].setSize(resolution.getWidth(), resolution.getHeight());
		
		imagesIntro[1] = new GImage("res/textures/Ygg intro 2.png");
		imagesIntro[1].setSize(resolution.getWidth(), resolution.getHeight());
		
		imagesIntro[2] = new GImage("res/textures/Gue intro 3.png");
		imagesIntro[2].setSize(resolution.getWidth(), resolution.getHeight());
		
		gc.add(imagesIntro[0]);
	}
	
	public void initJeu(){	// initalise le menu principale et affiche tout son contenu
		gc.removeAll();
		
		world = new Stage1();
		AudioManager.addAmbientSound("intro", "res/audio/intro.wav");
		AudioManager.addAmbientSound("boss1", "res/audio/boss_1.wav");
		init();
		
		gc.add(player.getImage());
		startTimeStage = System.currentTimeMillis();
		currentTimeStage = 0;
		
		startDialogue(world.getListSpeach().get(0));
		refresh.start();
		
		gc.add(pan);
		
	}
	
	public void init(){
		
		//Interface
		//---------------------------------------------------
		GImage backgroundStatsImage = new GImage("res/textures/cote droit.png");
		backgroundStatsImage.setSize(resolution.getWidth() * WIDTH_OF_STATS, resolution.getHeight());
		backgroundStatsImage.setLocation((MARGIN + SIZE_OF_GAME) * resolution.getWidth(),0);
		gc.add(backgroundStatsImage);
		for(int i = 0;i < backgroundGameImage.length;i++){
			backgroundGameImage[i] = new GImage("res/textures/bg1.png");
			backgroundGameImage[i].setSize(resolution.getWidth() * SIZE_OF_GAME, resolution.getHeight());
			backgroundGameImage[i].setLocation((MARGIN) * resolution.getWidth(),-i * resolution.getHeight());
			gc.add(backgroundGameImage[i]);
		}
		ritualLevel = new GRect(0,0);
		ritualLevel.setColor(Color.BLUE);
		ritualLevel.setFilled(true);
		ritualLevel.setFillColor(Color.BLUE);
		ritualLevel.setSize(resolution.getWidth() * 0.0097f, resolution.getHeight() * 0.43f);
		ritualLevel.setLocation((MARGIN + SIZE_OF_GAME + WIDTH_OF_STATS/2.0f - 0.005f) * resolution.getWidth(),resolution.getHeight() * (0.24f/2.0f));
		gc.add(ritualLevel);
		badRitualLevel = new GRect(0,0);
		badRitualLevel.setColor(Color.RED);
		badRitualLevel.setFilled(true);
		badRitualLevel.setFillColor(Color.RED);
		badRitualLevel.setSize(resolution.getWidth() * 0.00957f, resolution.getHeight() * 0.43f * ((100.0f-player.getRitualPercentage())/100.0f));
		badRitualLevel.setLocation((MARGIN + SIZE_OF_GAME + WIDTH_OF_STATS/2.0f - 0.005f) * resolution.getWidth(),resolution.getHeight() * (0.24f/2.0f));
		gc.add(badRitualLevel);
		GImage barreImage = new GImage("res/textures/barre.png");
		barreImage.setSize(resolution.getWidth() * 0.02f, resolution.getHeight() * 0.50f);
		barreImage.setLocation((MARGIN + SIZE_OF_GAME + WIDTH_OF_STATS/2.0f - 0.01f) * resolution.getWidth(),resolution.getHeight() * (0.17f/2.0f));
		gc.add(barreImage);
		scoreLabel = new GLabel("");
		scoreLabel.setLocation((MARGIN + SIZE_OF_GAME + 0.02) * resolution.getWidth(),resolution.getHeight() * 0.65f);
		scoreLabel.setColor(Color.BLACK);
		scoreLabel.setFont("Arial-18");
		gc.add(scoreLabel);
		highScoreLabel = new GLabel("");
		highScoreLabel.setLocation((MARGIN + SIZE_OF_GAME + 0.02) * resolution.getWidth(),resolution.getHeight() * 0.7f);
		highScoreLabel.setColor(Color.BLACK);
		highScoreLabel.setFont("Arial-18");
		gc.add(highScoreLabel);
		powerLabel = new GLabel("Power");
		powerLabel.setLocation((MARGIN + SIZE_OF_GAME + 0.02f) * resolution.getWidth(),resolution.getHeight() * (0.765f));
		powerLabel.setColor(Color.BLACK);
		powerLabel.setFont("Arial-18");
		gc.add(powerLabel);
		powerLevel = new GRect(0,0);
		powerLevel.setColor(Color.RED);
		powerLevel.setFilled(true);
		powerLevel.setFillColor(Color.RED);
		powerLevel.setSize(resolution.getWidth() * 0.08f * (player.getPower()/100.0f), resolution.getHeight() * 0.02f);
		powerLevel.setLocation((MARGIN + SIZE_OF_GAME + 0.1f) * resolution.getWidth(),resolution.getHeight() * (0.75f));
		gc.add(powerLevel);
		spellLabel = new GLabel("Spell");
		spellLabel.setLocation((MARGIN + SIZE_OF_GAME + 0.02f) * resolution.getWidth(),resolution.getHeight() * (0.805f));
		spellLabel.setColor(Color.BLACK);
		spellLabel.setFont("Arial-18");
		gc.add(spellLabel);
		spellLevel = new GRect(0,0);
		spellLevel.setColor(Color.RED);
		spellLevel.setFilled(true);
		spellLevel.setFillColor(Color.RED);
		spellLevel.setSize(resolution.getWidth() * 0.08f * (player.getSpell()/100.0f), resolution.getHeight() * 0.02f);
		spellLevel.setLocation((MARGIN + SIZE_OF_GAME + 0.1f) * resolution.getWidth(),resolution.getHeight() * (0.79f));
		gc.add(spellLevel);
		
		dialogImage = new GImage("res/textures/boite dialogues.png");
		dialogImage.setSize(SIZE_OF_GAME * resolution.getWidth(),0.3 * resolution.getHeight());
		dialogImage.setLocation((MARGIN - 0.015) * resolution.getWidth(), 0.65 * resolution.getHeight());
		dialogConfirmLabel = new GLabel("Appuyer sur entrer pour continuer...");
		dialogConfirmLabel.setColor(Color.WHITE);
		dialogConfirmLabel.setFont("Arial-15");
		dialogConfirmLabel.setLocation((MARGIN + SIZE_OF_GAME/2.0 + 0.1) * resolution.getWidth(), 0.92 * resolution.getHeight());
		dialogLabel.add(new GLabel("", 0.18 * resolution.getWidth(), 0.77 * resolution.getHeight()));
		dialogLabel.add(new GLabel("", 0.18 * resolution.getWidth(), 0.80 * resolution.getHeight()));
		dialogLabel.add(new GLabel("", 0.18 * resolution.getWidth(), 0.83 * resolution.getHeight()));
		dialogLabel.add(new GLabel("", 0.18 * resolution.getWidth(), 0.86 * resolution.getHeight()));
		int fontSize = (int)(18.0 / 1080.0 * resolution.getHeight());
		dialogLabel.get(0).setFont("Arial-" + fontSize);
		dialogLabel.get(0).setColor(Color.WHITE);
		dialogLabel.get(1).setFont("Arial-" + fontSize);
		dialogLabel.get(1).setColor(Color.WHITE);
		dialogLabel.get(2).setFont("Arial-" + fontSize);
		dialogLabel.get(2).setColor(Color.WHITE);
		dialogLabel.get(3).setFont("Arial-" + fontSize);
		dialogLabel.get(3).setColor(Color.WHITE);
		
		vertProgressImage = new GImage("res/textures/barre-horizontale.png");
		vertProgressImage.setSize(resolution.getWidth() * 0.085f, resolution.getHeight() * 0.022f);
		vertProgressImage.setLocation((MARGIN + SIZE_OF_GAME + 0.098f) * resolution.getWidth(),resolution.getHeight() * (0.79f));
		gc.add(vertProgressImage);
		vertProgressLImage = new GImage("res/textures/barre-horizontale-graduations.png");
		vertProgressLImage.setSize(resolution.getWidth() * 0.082f, resolution.getHeight() * 0.022f);
		vertProgressLImage.setLocation((MARGIN + SIZE_OF_GAME + 0.098f) * resolution.getWidth(),resolution.getHeight() * (0.79f));
		gc.add(vertProgressLImage);
		vertProgressImage2 = new GImage("res/textures/barre-horizontale.png");
		vertProgressImage2.setSize(resolution.getWidth() * 0.085f, resolution.getHeight() * 0.022f);
		vertProgressImage2.setLocation((MARGIN + SIZE_OF_GAME + 0.098f) * resolution.getWidth(),resolution.getHeight() * (0.75f));
		gc.add(vertProgressImage2);
		vertProgressLImage2 = new GImage("res/textures/barre-horizontale-graduations.png");
		vertProgressLImage2.setSize(resolution.getWidth() * 0.082f, resolution.getHeight() * 0.022f);
		vertProgressLImage2.setLocation((MARGIN + SIZE_OF_GAME + 0.098f) * resolution.getWidth(),resolution.getHeight() * (0.75f));
		gc.add(vertProgressLImage2);
		World.getUltiList().add(new Ulti(0.22f, 0.88f,"res/textures/amis1.png"));
		World.getUltiList().add(new Ulti(0.32f, 0.88f,"res/textures/amis2.png"));
		World.getUltiList().add(new Ulti(0.42f, 0.88f,"res/textures/amis3.png"));
		World.getUltiList().add(new Ulti(0.52f, 0.88f,"res/textures/amis4.png"));
		
		for(Ulti u : World.getUltiList())gc.add(u.getImage());
		//---------------------------------------------------
		
		//Debug
		//---------------------------------------------------
		updateDebugLabel = new GLabel("");
		updateDebugLabel.setFont("Arial-15");
		updateDebugLabel.setColor(Color.WHITE);
		updateDebugLabel.setLocation(10, 30);
		gc.add(updateDebugLabel);
		inputDebugLabel = new GLabel("");
		inputDebugLabel.setFont("Arial-15");
		inputDebugLabel.setColor(Color.WHITE);
		inputDebugLabel.setLocation(10,50);
		gc.add(inputDebugLabel);
		ritualDebugLvl = new GLabel("");
		ritualDebugLvl.setFont("Arial-15");
		ritualDebugLvl.setColor(Color.WHITE);
		ritualDebugLvl.setLocation(10, 70);
		gc.add(ritualDebugLvl);
		nbreRenderLabel = new GLabel("");
		nbreRenderLabel.setFont("Arial-15");
		nbreRenderLabel.setColor(Color.WHITE);
		nbreRenderLabel.setLocation(10, 90);
		gc.add(nbreRenderLabel);
		inputDebugLabel.setVisible(isDebugMode);
		updateDebugLabel.setVisible(isDebugMode);
		ritualDebugLvl.setVisible(isDebugMode);
		nbreRenderLabel.setVisible(isDebugMode);
		//---------------------------------------------------
		
		currentTimePause = 0;
		imageTetePerso.setLocation(0.065 * resolution.getWidth(), 0.71 * resolution.getHeight());
	}
	
	public void initStage2(){
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO keyPressed
	//	System.out.println("Code touche pressée : " + e.getKeyCode());
		switch(e.getKeyCode()){
		case KeyCode.KEY_UP:
			keyUp = true;
			break;
		case KeyCode.KEY_RIGHT:
			keyRight = true;
			break;
		case KeyCode.KEY_DOWN:
			keyDown = true;
			break;
		case KeyCode.KEY_LEFT:
			keyLeft = true;
			break;
		case KeyCode.KEY_ESCAPE:
			System.exit(0);
			break;
		case KeyCode.KEY_F2:
			isDebugMode = !isDebugMode;
			inputDebugLabel.setVisible(isDebugMode);
			updateDebugLabel.setVisible(isDebugMode);
			ritualDebugLvl.setVisible(isDebugMode);
			nbreRenderLabel.setVisible(isDebugMode);
			break;
		case KeyCode.KEY_M:
			if(!dialogPhase){
				world.getMonstersList().add(new MonsterModel_chauve_souris_focus_bomb());
				gc.add(world.getMonstersList().get(world.getMonstersList().size() - 1).getImage());
			}
			break;
		case KeyCode.KEY_SHIFT:
			keyShift = true;
			break;
			
		case KeyCode.KEY_W:
			if(player.getNbRepAnimSpell() == 0 && player.getSpellLevel() >= 1 && !dialogPhase){
				player.setSpellLevel(player.getSpellLevel() -1);
				player.setXCenterSpell(player.getX() + (player.getImage().getWidth() / 2) / resolution.getWidth());
				player.setYCenterSpell(player.getY() + (player.getImage().getHeight() / 2) / resolution.getHeight());
				player.getTimerAnimSpell().start();
				player.getImageSpell().setSize((player.getWidthSpell() * (double)player.getNbRepAnimSpell()) / 5.0, (player.getHeightSpell() * (double)player.getNbRepAnimSpell()) / 5.0);
				player.getImageSpell().setLocation(resolution.getWidth() * player.getXCenterSpell() - player.getImageSpell().getHeight() / 2.0,
						resolution.getHeight() * player.getYCenterSpell() - player.getImageSpell().getWidth() / 2.0);
				gc.add(player.getImageSpell());
			}
			break;
			
		case KeyCode.KEY_X:
			if(!dialogPhase && World.getUltiList().size() > 0){
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
			break;
			
		case KeyCode.KEY_D:
			dialogPhase = true;
			gc.add(dialogImage);
			gc.add(dialogConfirmLabel);
			for(GLabel gl : dialogLabel){
				gc.add(gl);
			}
			break;
		}
	//	inputDebugLabel.setLabel("KeyPressed:" + e.getKeyCode());
	//	world.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO keyReleased
		switch(e.getKeyCode()){
		case KeyCode.KEY_UP:
			keyUp = false;
			break;
		case KeyCode.KEY_RIGHT:
			keyRight = false;
			break;
		case KeyCode.KEY_DOWN:
			keyDown = false;
			break;
		case KeyCode.KEY_LEFT:
			keyLeft = false;
			break;
			
		case KeyCode.KEY_V:
		//	player.setImage("res_test/carre_vert.png");
			break;
			
		case KeyCode.KEY_R:
		//	carre.setImage("res_test/carre_rouge.png");
			break;
			
		case KeyCode.KEY_SHIFT:
			keyShift = false;
			break;
			
		case KeyCode.KEY_ENTER:
			if(dialogPhase){
				nextDialogueReplique();
			}
			if(isGameOver){
//				player.setDead(false);
//				player.setSpell(0);
//				player.setInvul(false);
//				player.resetTimer();
//				player.setTimerTo0();
//				player.setRitualPercentage(50);
//				player.setPower(0);
//				player.setPowerLevel(0);
//				player.setSpellLevel(0);
//				score = 0;
//				for(Ulti u : World.getUltiList()){
//					gc.remove(u.getImage());
//				}
//				World.getUltiList().add(new Ulti(0.22f, 0.88f,"res/textures/amis1.png"));
//				World.getUltiList().add(new Ulti(0.32f, 0.88f,"res/textures/amis2.png"));
//				World.getUltiList().add(new Ulti(0.42f, 0.88f,"res/textures/amis3.png"));
//				World.getUltiList().add(new Ulti(0.52f, 0.88f,"res/textures/amis4.png"));
//				
//				for(Ulti u : World.getUltiList())gc.add(u.getImage());
//				gameover.setVisible(false);
				isGameOver = false;
				System.exit(0);
			}
			
			if(currentIntro < 2){
				currentIntro++;
				gc.add(imagesIntro[currentIntro]);
			}
			else if(currentIntro == 2){
				currentIntro++;
				menuActif = false;
				initJeu();
				
				thread = (new Thread(new Runnable() {
					public void run() {
						while(true){
							loop();
							try{
								Thread.sleep(1000/120);
							}catch(Exception e){}
						}
					}
				}));
				thread.start();
			}
			break;
		}
	//	world.keyReleased(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO keyTyped
	//	world.keyTyped(e);
	}
	
	public static void addScore(int sc){
		score += sc;
		if(score > highscore){
			highscore = score;
			IOFile.saveFileByString(String.valueOf(highscore), "res/donnees/highscore.txt");
		}
	}
	
	// ************************************************************************************
	
	public void actionPerformed(ActionEvent e) {
		
		// TODO sourisPressed
		
		if(e.getSource() == pan.sourisPressed){	// quand on appuit sur un bouton de la souris
			
			switch(pan.noBouton){
				case MouseEvent.BUTTON1:
					if(menuActif){
						if(pan.posiSourisX >= boutonJouer.getX() && pan.posiSourisX <= boutonJouer.getX() + boutonJouer.getWidth() &&
								pan.posiSourisY >= boutonJouer.getY() && pan.posiSourisY <= boutonJouer.getY() + boutonJouer.getHeight()){
							
							initIntro();
						}
						else if(pan.posiSourisX >= boutonQuitter.getX() && pan.posiSourisX <= boutonQuitter.getX() + boutonQuitter.getWidth() &&
								pan.posiSourisY >= boutonQuitter.getY() && pan.posiSourisY <= boutonQuitter.getY() + boutonQuitter.getHeight()){
							
							menuActif = false;
							System.exit(0);
						}
					}
					break;
					
				case MouseEvent.BUTTON3:
					
					break;
			}
			
			pan.sourisPressed.stop();
		}
		
		// TODO sourisReleased
		
		if(e.getSource() == pan.sourisReleased){

			switch(pan.noBouton){
				case MouseEvent.BUTTON1:
					
					break;
					
				case MouseEvent.BUTTON3:
					
					break;
					
			}
			
			pan.sourisReleased.stop();
		}
		
		// TODO sourisMoved
		
		if(e.getSource() == pan.sourisMoved){
			
			
			
			pan.sourisMoved.stop();
		}
		
		// TODO sourisDragged
		
		if(e.getSource() == pan.sourisDragged){
			
			if((pan.noBouton & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {	// si on darg la souris avec le bouton gauche
				
	        }
			
			if((pan.noBouton & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
				
	        }
			
			pan.sourisDragged.stop();
		}
		
		// *************************************************************
		
		if(e.getSource() == refresh && !dialogPhase){
			
			double speed = keyShift ? player.getMoveSpeed() / 2.0 : player.getMoveSpeed();
			if(keyUp || keyDown){
				if(keyRight)	player.move(speed / 1.7, 0);
				if(keyLeft)		player.move(-speed / 1.7, 0);
			}
			else{
				if(keyRight)	player.move(speed, 0);
				if(keyLeft)		player.move(-speed, 0);
			}
			
			if(keyRight || keyLeft){
				if(keyUp) player.move(0, -(speed * 1.4) / 1.7);
				if(keyDown) player.move(0, (speed * 1.4) / 1.7);
			}
			else{
				if(keyUp) player.move(0, -(speed * 1.4));
				if(keyDown) player.move(0, (speed * 1.4));
			}
			speed = (!keyShift)?1:0.5;
			player.move(Joystick.x/40f*speed, Joystick.y/40f*speed);
			
			if(world.getMonsterSpawnPattern().size() > 0){
				boolean repeatSpawn;
				currentTimeStage = System.currentTimeMillis() - startTimeStage - currentTimePause;
				do{
					repeatSpawn = false;
					if(world.getMonsterSpawnPattern().get(0).getSpawnTimer() < currentTimeStage){
						repeatSpawn = true;
						
						world.getMonsterSpawnPattern().get(0).getMonster().setTimerTo0();
						world.getMonstersList().add(world.getMonsterSpawnPattern().get(0).getMonster());
						gc.add(world.getMonstersList().get(world.getMonstersList().size() - 1).getImage());
						
						world.getMonsterSpawnPattern().remove(0);
					}
				}while(repeatSpawn && world.getMonsterSpawnPattern().size() > 0);
			}
			
			if(world.getBossSpawned() && world.getBoss().isAlive())
			for(int i=0; i<world.getBoss().getListShootingPattern().get(0).getListTimedBullet().size(); i++){
				
				// test pour que le boss lance un bullet
				if(!world.getBoss().getListShootingPattern().get(0).getListTimedBullet().get(i).isAlreadyShot
						&& world.getBoss().getListShootingPattern().get(0).getListTimedBullet().get(i).shotTimer < world.getBoss().getElapsed()){
					
					world.getBoss().getListShootingPattern().get(0).getListTimedBullet().get(i).isAlreadyShot = true;
					
					// targeted bullet
					if(world.getBoss().getListShootingPattern().get(0).getListTimedBullet().get(i).isFocusOnPlayer){
						World.getBossBulletsList().add(
								new Bullet(
										world.getBoss().getListShootingPattern().get(0).getListTimedBullet().get(i),
										world.getBoss().getX() + ((world.getBoss().getImage().getWidth() / 2) / resolution.getWidth()),
										world.getBoss().getY() + ((world.getBoss().getImage().getHeight() / 2) / resolution.getHeight()),
										player.getX() + ((player.getImage().getWidth() / 2) / resolution.getWidth()),
										player.getY() + ((player.getImage().getHeight() / 2) / resolution.getHeight())));
					}
					// untargeted bullet
					else{
						World.getBossBulletsList().add(
								new Bullet(
										world.getBoss().getListShootingPattern().get(0).getListTimedBullet().get(i),
										world.getBoss().getX() + ((world.getBoss().getImage().getWidth() / 2) / resolution.getWidth()),
										world.getBoss().getY() + ((world.getBoss().getImage().getHeight() / 2) / resolution.getHeight())));
					}
					
					gc.add(World.getBossBulletsList().get(World.getBossBulletsList().size() - 1).getImage());
				}
			}
	//		if(world.getBoss().g)
			
			try{
				for(Monster m : world.getMonstersList()){
					m.setPosition((double)m.getPattern().get(m.getIndicePattern()).x / 1920.0, (double)m.getPattern().get(m.getIndicePattern()).y / 1080.0);
					if(!m.incrementIndicePattern()){	// on a fini le pattern du monstre
						monstersListPoubelle.add(m);
						gc.remove(m.getImage());
				/*		for(Bullet b : m.getBulletsList()){
							bulletsListMonsterDead.add(b);
							gc.add(b.getImage());
							gc.remove(b.getImage());
						}
						m.getBulletsList().clear();*/
					}
				}
				
				for(Monster m : monstersListPoubelle){
					world.getMonstersList().remove(m);
				}
				
				monstersListPoubelle.clear();
				
				for(int i=0; i<player.getBulletsList().size(); i++){
					if(player.getBulletsList().get(i).getY() + (player.getBulletsList().get(i).getImage().getHeight() / resolution.getHeight()) < 0.0
							|| player.getBulletsList().get(i).getY() > 1.0){
						bulletsListPoubelle.add(player.getBulletsList().get(i));
						gc.remove(player.getBulletsList().get(i).getImage());
					}
					else{
						player.getBulletsList().get(i).move(player.getBulletsList().get(i).getMoveSpeed() * player.getBulletsList().get(i).getVectorX(),
								player.getBulletsList().get(i).getMoveSpeed() * player.getBulletsList().get(i).getVectorY());
					}
				}
				
				for(Bullet b : bulletsListPoubelle){
					player.getBulletsList().remove(b);
				}
				bulletsListPoubelle.clear();
				
				for(Bullet b : World.getBulletsList()){
					if(b.getY() + (b.getImage().getHeight() / resolution.getHeight()) < 0.0
							|| b.getY() > 1.0){
						bulletsListPoubelle.add(b);
						gc.remove(b.getImage());
					}
					else{
						b.move(b.getMoveSpeed() * b.getVectorX(),
								b.getMoveSpeed() * b.getVectorY());
					}
				}
				
				for(Bullet b : bulletsListPoubelle){
					World.getBulletsList().remove(b);
				}
				bulletsListPoubelle.clear();
				
				for(Bullet b : World.getBossBulletsList()){
					if(b.getY() + (b.getImage().getHeight() / resolution.getHeight()) < 0.0
							|| b.getY() > 1.0){
						bulletsListPoubelle.add(b);
						gc.remove(b.getImage());
					}
					else{
						b.move(b.getMoveSpeed() * b.getVectorX(),
								b.getMoveSpeed() * b.getVectorY());
					}
				}
				
				for(Bullet b : bulletsListPoubelle){
					World.getBossBulletsList().remove(b);
				}
				bulletsListPoubelle.clear();
				
			}catch(Exception e2){}
			
			if(world.getBoss() != null)
			if(world.getBossSpawned() && world.getBoss().getPatternFini()){
				
			}
			
			// permet de faire spawn le boss quand tout le stage est fini
			// n'est exécuté qu'une fois
			if(world != null)
			if(!world.getBossSpawned() && world.getMonstersList().size() == 0 && world.getMonsterSpawnPattern().size() == 0){
				world.setBossSpawned(true);
				world.getBoss().setTimerTo0();
				gc.add(world.getBoss().getImage(), -500, -500);
			}
			
			if(world != null)
			if(world.getBossSpawned()){
				// le boss continu son pettern
				if(!world.getBoss().getPatternFini()){
					world.getBoss().setPosition((double)world.getBoss().getPattern().get(world.getBoss().getIndicePattern()).x / 1920.0, (double)world.getBoss().getPattern().get(world.getBoss().getIndicePattern()).y / 1080.0);
					if(!world.getBoss().incrementIndicePattern()){	// on a fini le pattern du boss
						world.getBoss().setAFiniSonParttern();
					}
				}
				// le boss ce deplace normalement
				else{
					world.getBoss().deplacer();
				}
			}
		}
		
		if(player != null)
		if(e.getSource() == player.getTimerAnimSpell()){
			if(player.getNbRepAnimSpell() == 30){
				ArrayList<Monster> trashMonster = new ArrayList<Monster>();
				
				player.getTimerAnimSpell().stop();
				player.setNbRepAnimSpell(0);
				gc.remove(player.getImageSpell());
				
				for(Monster m : world.getMonstersList()){
					m.setHP(m.getHP() - 15);
					if(m.getHP() <= 0){
						m.drop();
						addScore(m.getScoreDead());
					//	player.setRitualPercentage(player.getRitualPercentage() + m.getScoreDead()/10.0f);
						gc.remove(m.getImage());
						trashMonster.add(m);
					}
				}
				
				for(Monster m : trashMonster){
					world.getMonstersList().remove(m);
				}
				
				if(world.getBoss().isAlive()){
					world.getBoss().setHP(world.getBoss().getHP() - 15);
					System.out.println("nb PV boss : " + world.getBoss().getHP());
					
					// mort du boss
					if(!world.getBoss().isAlive()){
						mortBoss();
					}
				}
			}
			else{
				player.setNbRepAnimSpell(player.getNbRepAnimSpell() + 1);
				player.getImageSpell().setSize((player.getWidthSpell() * (double)player.getNbRepAnimSpell()) / 5.0, (player.getHeightSpell() * (double)player.getNbRepAnimSpell()) / 5.0);
				player.getImageSpell().setLocation(resolution.getWidth() * player.getXCenterSpell() - player.getImageSpell().getHeight() / 2.0,
						resolution.getHeight() * player.getYCenterSpell() - player.getImageSpell().getWidth() / 2.0);
			}
		}
		
		if(e.getSource() == timerAnimUlti){
			if(nbRepAnimUlti == 30){
				ArrayList<Monster> trashMonster = new ArrayList<Monster>();
				
				timerAnimUlti.stop();
				nbRepAnimUlti = 0;
				gc.remove(imageUlti);
				
				for(Monster m : world.getMonstersList()){
					m.setHP(m.getHP() - 50);
					if(m.getHP() <= 0){
						m.drop();
						addScore(m.getScoreDead());
					//	player.setRitualPercentage(player.getRitualPercentage() + m.getScoreDead()/10.0f);
						gc.remove(m.getImage());
						trashMonster.add(m);
					}
				}
				
				for(Monster m : trashMonster){
					world.getMonstersList().remove(m);
				}
				
				if(world.getBoss().isAlive()){
					world.getBoss().setHP(world.getBoss().getHP() - 50);
					System.out.println("nb PV boss : " + world.getBoss().getHP());
					
					// mort du boss
					if(!world.getBoss().isAlive()){
						mortBoss();
					}
				}
			}
			else{
				nbRepAnimUlti ++;
				imageUlti.setSize((widthUlti * (double)nbRepAnimUlti) / 5.0, (heightUlti * (double)nbRepAnimUlti) / 5.0);
				imageUlti.setLocation(resolution.getWidth() * xCenterUlti - imageUlti.getHeight() / 2.0,
						resolution.getHeight() * yCenterUlti - imageUlti.getWidth() / 2.0);
			}
		}
		
		
		
	/*	if(e.getSource() == player.getTimerShoot()){
			player.addBullet(player.getX() + player.getImage().getWidth() / 2, player.getY() - 5);
			gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
		}*/
		
	//	world.actionPerformed(e);
	}
	
	public void loop(){
		long current = System.currentTimeMillis();
		String stage = world.getStageName();
		
		if(intro && dialogPhase){
			stage = "intro";
		}else if(intro && !dialogPhase)intro = false;
		
		if(world.getBossSpawned()){
			stage = "boss1";
		}

		AudioManager.update(stage);
		
		if(isGameOver){
			keyLeft = false;
			keyRight = false;
			keyUp = false;
			keyDown = false;
			
			gameOver();
			
		}
		
		if(!dialogPhase){
			player.update();
			world.updateWorld();
			if(player.isTimeOut()){
				
				if(!isGameOver){
					// selection du type d'attaque en fonction du power
					switch(player.getPowerLevel()){
					case 0:
						player.addBullet("tir joueur", player.getX() + (player.getImage().getWidth() / 2) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						break;
						
					case 1:
						player.addBullet("tir joueur", player.getX() + (player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						player.addBullet("tir joueur", player.getX() + (2 * player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						break;
						
					case 2:
						player.addBullet("tir joueur", player.getX() + (player.getImage().getWidth() / 2) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						player.addBullet("tir joueur", player.getX() + (player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						player.addBullet("tir joueur", player.getX() + (2 * player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						break;
						
					case 3:
						player.addBullet("bullet_default", player.getX() + (player.getImage().getWidth() / 2) / resolution.getWidth(), player.getY() - 0.05, 10);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						player.addBullet("tir joueur", player.getX() + (player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						player.addBullet("tir joueur", player.getX() + (2 * player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						break;
						
					default:
						player.addBullet("bullet_default", player.getX() + (player.getImage().getWidth() / 2) / resolution.getWidth(), player.getY() - 0.05, 10);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						player.addBullet("tir joueur", player.getX() + (player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						player.addBullet("tir joueur", player.getX() + (2 * player.getImage().getWidth() / 3) / resolution.getWidth(), player.getY() - 0.05, 2);
						gc.add(player.getBulletsList().get(player.getBulletsList().size() - 1).getImage());
						player.resetTimer();
						break;
					}
				}
			}
			
			synchronized(world.getMonstersList()){
				try{
					for(Monster m : world.getMonstersList()){
						if(m.isTimeOut()){
							if(m.getFireModeFocus())	m.addBullet(player.getX() + (player.getImage().getWidth() / 2) / resolution.getWidth(), player.getY() + (player.getImage().getHeight() / 2) / resolution.getHeight());
							else	m.addBullet(m.getX() + (m.getImage().getWidth() / 2) / resolution.getWidth(), m.getY() + 0.05);
							gc.add(World.getBulletsList().get(World.getBulletsList().size() - 1).getImage());
							m.resetTimer();
						}
					}
				}catch(Exception e){}
			}
			
			// gestion du lancement des pattern de tir du boss
			if(world.getBossSpawned() && world.getBoss().isAlive())
			if(world.getBoss().isTimeOut()){
				world.getBoss().resetTimer();
				world.getBoss().gestionTir();
				for(int i=0; i<world.getBoss().getListShootingPattern().get(0).getListTimedBullet().size(); i++){
					world.getBoss().getListShootingPattern().get(0).getListTimedBullet().get(i).isAlreadyShot = false;
				}
			}
			
			ArrayList<Monster> trashMonster = new ArrayList<Monster>();
			ArrayList<Bullet> trashBullet = new ArrayList<Bullet>();
			ArrayList<Bullet> trashBulletM = new ArrayList<Bullet>();
			ArrayList<Bullet> trashBulletB = new ArrayList<Bullet>();
			ArrayList<Items> trashItems = new ArrayList<Items>();
			try{
				
			for(Ulti u : World.getUltiList())u.update();
				
			for(Monster m : world.getMonstersList()){
				for(Bullet b : player.getBulletsList()){
					if(isCollide(b,m)){
						m.setHP(m.getHP() - 1);
						if(m.getHP() <= 0){
							m.drop();
							addScore(m.getScoreDead());
							player.setRitualPercentage(player.getRitualPercentage() + .5f);
							gc.remove(m.getImage());
							trashMonster.add(m);
						}
						gc.remove(b.getImage());
						trashBullet.add(b);
					}
				}
			}
			
			for(Bullet b : World.getBulletsList()){
				if(isCollide(b,player) && !player.isInvul()){
					player.setDead(true);
					trashBulletM.add(b);
				}
			}
			
			for(Bullet b : World.getBossBulletsList()){
				if(isCollide(b, player) && !player.isInvul()){
					player.setDead(true);
					trashBulletB.add(b);
				}
			}
			
			for(Bullet b : player.getBulletsList()){
				if(isCollide(b,world.getBoss())){
					world.getBoss().setHP(world.getBoss().getHP() - b.getDamage());
					System.out.println("nb PV boss : " + world.getBoss().getHP());
					
					// mort du boss
					if(!world.getBoss().isAlive()){
						mortBoss();
						
						
					//	initStage2
					}
					gc.remove(b.getImage());
					trashBullet.add(b);
				}
			}
			
			for(Items i : World.getItemsList()){
				if(isCollide(i,player)){
					i.action(player);
					player.setRitualPercentage(player.getRitualPercentage() + 10.0f/10.0f);
					i.delete();
					trashItems.add(i);
				}
				for(Ulti u : World.getUltiList()){
					if(isCollide(i,u)){
						i.action(player);
						player.setRitualPercentage(player.getRitualPercentage() + 10.0f/10.0f);
						i.delete();
						trashItems.add(i);
					}
				}
			}
			
			for(Monster m : trashMonster){
				world.getMonstersList().remove(m);
			}
			
			for(Bullet b : trashBullet){
				player.getBulletsList().remove(b);
			}
			
			for(Bullet b : trashBulletM){
				gc.remove(b.getImage());
				World.getBulletsList().remove(b);
			}
			
			for(Bullet b : trashBulletB){
				gc.remove(b.getImage());
				World.getBossBulletsList().remove(b);
			}
			
			for(Items i : trashItems){
				World.getItemsList().remove(i);
			}
			}catch(Exception e){}
			ritualDebugLvl.setLabel("Ritual Level:" + player.getRitualPercentage() + "%");
			badRitualLevel.setSize(resolution.getWidth() * 0.0097f, resolution.getHeight() * 0.43f * ((100.0f-player.getRitualPercentage())/100.0f));
			highScoreLabel.setLabel("HighScore: " + highscore);
			scoreLabel.setLabel("Score: " + score);
			nbreRenderLabel.setLabel("Nbre de rendu:" + nbreRender);
			updateDebugLabel.setLabel("Time update:" + (System.currentTimeMillis() - current) + "ms");
			powerLabel.setLabel("Power [" + player.getPowerLevel() + "]");
			powerLevel.setSize(resolution.getWidth() * 0.08f * (player.getPower()/100.0f), resolution.getHeight() * 0.02f);
			spellLabel.setLabel("Spell [" + player.getSpellLevel() + "]");
			spellLevel.setSize(resolution.getWidth() * 0.08f * (player.getSpell()/100.0f), resolution.getHeight() * 0.02f);
			
			for(Items i : World.getItemsList()){
				i.move(0, 1);
			}
			
			for(int i=0; i<World.getUltiList().size(); i++){
				if(World.getUltiList().get(i).isSacrifice()){
				
					xCenterUlti = World.getUltiList().get(i).getX() + (World.getUltiList().get(i).getImage().getWidth() / 2) / resolution.getWidth();
					yCenterUlti = World.getUltiList().get(i).getY() + (World.getUltiList().get(i).getImage().getHeight() / 2) / resolution.getHeight();
					timerAnimUlti.start();
					imageUlti.setSize((widthUlti * (double)nbRepAnimUlti) / 5.0, (heightUlti * nbRepAnimUlti) / 5.0);
					imageUlti.setLocation(resolution.getWidth() * xCenterUlti - imageUlti.getHeight() / 2.0,
							resolution.getHeight() * yCenterUlti - imageUlti.getWidth() / 2.0);
					gc.add(imageUlti);
					
					player.setRitualPercentage(player.getRitualPercentage() + 20);
					gc.remove(World.getUltiList().get(i).getImage());
					World.getUltiList().remove(World.getUltiList().get(i));
				}
			}
			
			if(!isGameOver){
				for(int i=0; i < backgroundGameImage.length; i++){
					backgroundGameImage[i].move(0, 1);
				}
				
				for(int i=0; i < backgroundGameImage.length; i++){
					if(backgroundGameImage[i].getY() > resolution.getHeight()){
						backgroundGameImage[i].move(0, -2.0 * backgroundGameImage[i].getHeight());
					}
				}
			}
			
		}
		Joystick.update();
		if(player.getRitualPercentage()<=0){
			isGameOver = true;
		}
		
	}
	
	public static void dialog(String text){
		
	}
	
	public static boolean isCollide(Entity bullet,Entity entity){
		
		// collision horizontale
		if(bullet.getImage().getX() + bullet.getImage().getWidth() > entity.getImage().getX() + (entity.getImage().getWidth() / 3)
				&& bullet.getImage().getX() < entity.getImage().getX() + (2 * entity.getImage().getWidth() / 3)){
			
			// collision verticale
			if(bullet.getImage().getY() + bullet.getImage().getHeight() > entity.getImage().getY() + (entity.getImage().getHeight() / 3)
					&& bullet.getImage().getY() < entity.getImage().getY() + (2 * entity.getImage().getHeight() / 3)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isCollide(Items bullet,Entity entity){
		
		// collision horizontale
		if(bullet.getImage().getX() + bullet.getImage().getWidth() > entity.getImage().getX() + (entity.getImage().getWidth() / 3)
				&& bullet.getImage().getX() < entity.getImage().getX() + (2 * entity.getImage().getWidth() / 3)){
			
			// collision verticale
			if(bullet.getImage().getY() + bullet.getImage().getHeight() > entity.getImage().getY() + (entity.getImage().getHeight() / 3)
					&& bullet.getImage().getY() < entity.getImage().getY() + (2 * entity.getImage().getHeight() / 3)){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		jeu = new Jeu();
	}
	
	public void mortBoss(){
		world.getBoss().drop();
		addScore(world.getBoss().getScoreDead());
	//	player.setRitualPercentage(player.getRitualPercentage() + m.getScoreDead()/10.0f);
		gc.remove(world.getBoss().getImage());
		
		startDialogue(world.getListSpeach().get(1 + 4 - World.getUltiList().size()));
	}
	
	public void gameOver(){
		gc.add(new GImage("res/textures/gameover.png"), 300, 300);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void startDialogue(Speach speach){
		actualSpeach = speach;
		indiceSpeachProgression = 0;
		dialogPhase = true;
		startTimePause = System.currentTimeMillis();
		gc.add(dialogImage);
		gc.add(dialogConfirmLabel);
		for(int i=0; i<dialogLabel.size(); i++){
			dialogLabel.get(i).setLabel(speach.getListReplique().get(0).getLignesReplique()[i]);
			gc.add(dialogLabel.get(i));
		}
		imageTetePerso.setImage("res/textures/" + actualSpeach.getListReplique().get(indiceSpeachProgression).getSpeakerName() + ".png");
		imageTetePerso.setSize(imageTetePerso.getWidth() * (2 / 1920.0 * resolution.getWidth()), imageTetePerso.getHeight() * (2 / 1080.0 * resolution.getHeight()));
		gc.add(imageTetePerso);
	}
	
	
	public void nextDialogueReplique(){
		indiceSpeachProgression ++;
		
		if(indiceSpeachProgression < actualSpeach.getListReplique().size()){
			for(int i=0; i<dialogLabel.size(); i++){
				dialogLabel.get(i).setLabel(actualSpeach.getListReplique().get(indiceSpeachProgression).getLignesReplique()[i]);
			}
			imageTetePerso.setImage("res/textures/" + actualSpeach.getListReplique().get(indiceSpeachProgression).getSpeakerName() + ".png");
			imageTetePerso.setSize(imageTetePerso.getWidth() * (2 / 1920.0 * resolution.getWidth()), imageTetePerso.getHeight() * (2 / 1080.0 * resolution.getHeight()));
		}
		else{	// fin du dialogue
			stopDialogue();
		}
	}
	
	public void stopDialogue(){
		gc.remove(dialogImage);
		gc.remove(dialogConfirmLabel);
		for(GLabel gl : dialogLabel){
			gc.remove(gl);
		}
		for(Ulti u : World.getUltiList())u.setTimerTo0();
		player.setTimerTo0();
		for(Monster m : world.getMonstersList()){
			m.setTimerTo0();
		}
		gc.remove(imageTetePerso);
		currentTimePause += System.currentTimeMillis() - startTimePause;
		
		if(world.getBossSpawned() && !world.getBoss().isAlive()){
			gc.add(new GImage("res/textures/stage clear.png"), 300, 300);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		dialogPhase = false;
	}
}
