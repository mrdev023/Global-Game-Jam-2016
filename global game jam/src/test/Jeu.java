package test;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Input.KeyCode;
import acm.graphics.GCanvas;
import acm.graphics.GContainer;
import acm.graphics.GDimension;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.JTFTools;
import acm.util.SwingTimer;

public class Jeu  extends JFrame implements ActionListener, KeyListener{
	
	// systeme -----------------------------------------------------------------
	Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	private GCanvas gc = new GCanvas();
	private EcranListeners pan = new EcranListeners(resolution);
	
	// autre -------------------------------------------------------------------
	private ArrayList<Coord> coords = new ArrayList<Coord>();
	private int enregister = -1;
	private SwingTimer refresh = new SwingTimer(25, this);
	
	private GLabel afficheur_timer = new GLabel("", 100, 1000);
	private long timeStart, timeElapsed, timePaused, timeStartPause;
	private boolean paused = false;
	
	public Jeu(){
		
		this.setUndecorated(true);
		this.setSize(this.getToolkit().getScreenSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.validate();
		this.setVisible(true);
		gc.add(pan);
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
		
		refresh.start();
		timeStart = System.currentTimeMillis();
		timeElapsed = timeStart;
		timePaused = 0;
		initJeu();
	}
	
	public void initJeu(){	// initalise le menu principale et affiche tout son contenu
		gc.removeAll();
		
		afficheur_timer = new GLabel("temps : " + (timeElapsed - timeStart - timePaused), 100, 1000);
		afficheur_timer.setFont("Arial-18");
		gc.add(afficheur_timer);
		
		gc.add(pan);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO keyPressed
		switch(e.getKeyCode()){
		case KeyCode.KEY_ESCAPE:
			System.exit(0);
			break;
		case KeyCode.KEY_S:
			if(enregister == -1)
				coords.clear();
			else{
				try{
					PrintWriter fSortie = new PrintWriter("pattern_mob.txt");
					for(Coord c : coords){
						
						fSortie.println(c.x + " " + c.y);
					}
					fSortie.close();
				}
				catch(IOException exception){
					System.out.println("ERREUR D'ECRITURE du fichier");
				}
			}
			enregister *= -1;
			break;
			
		case KeyCode.KEY_SPACE:
			if(!paused){
				timeStartPause = System.currentTimeMillis();
				paused = true;
			}
			else{
				paused = false;
			}
			break;
			
		case KeyCode.KEY_R:
			timeStart = System.currentTimeMillis();
			timeElapsed = timeStart;
			timePaused = 0;
			break;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO keyReleased
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO keyTyped
		
	}
	
	// ************************************************************************************
	
	public void actionPerformed(ActionEvent e) {
		
		// TODO sourisPressed
		
		if(e.getSource() == pan.sourisPressed){	// quand on appuit sur un bouton de la souris
			
			switch(pan.noBouton){
				case MouseEvent.BUTTON1:
					
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
		
		if(e.getSource() == refresh){
			coords.add(new Coord(pan.posiSourisX, pan.posiSourisY));
			timeElapsed = System.currentTimeMillis();
			if(paused){
				timePaused += timeElapsed - timeStartPause;
				timeStartPause = System.currentTimeMillis();
			}
			afficheur_timer.setLabel("temps : " + (timeElapsed - timeStart - timePaused));
		}
	}
	
}
