package Gui;
import acm.graphics.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

public class Boutons {
	private GImage bouton;	// la GImage du bouton qui est affiché sur l'écran
	private Image boutonSurvole, boutonNormal;	// les différentes textures du bouton
	private GLabel texte;	// le texte du bouton
	private double posiX, posiY, tailleX, tailleY;	// la position et les dimension du bouton
	private boolean survole = false, affiche = false;
	private Dimension dim;
		
	public Boutons(String nomImage, int x ,int y, String texteP, String police, Color couleurTxt, Dimension dimP){	// constructeur du bouton
		dim = dimP;
		posiX = x;
		posiY = y;
		
		boutonNormal = new GImage("Ressources Editeur/HUD/Boutons/" + nomImage + ".png").getImage();
		
		boutonSurvole = new GImage("Ressources Editeur/HUD/Boutons/" + nomImage + "_survolé.png").getImage();
		
		bouton = new GImage(boutonNormal, posiX, posiY);
		bouton.setSize((bouton.getWidth() * dim.getWidth() / 1920), (bouton.getHeight() * dim.getHeight() / 1080));
		
		tailleX = (int)bouton.getWidth();
		tailleY = (int)bouton.getHeight();
		
		texte = new GLabel(texteP);
		texte.setFont(police);
		texte.setColor(couleurTxt);
		texte.setLocation(posiX + tailleX/2 - (int)texte.getWidth()/2, posiY + tailleY/2 + (int)texte.getHeight()/2 -7);
	}
	
	public GImage getBouton(){
		return bouton;
	}
	
	public Image getBoutonSurvole(){
		return boutonSurvole;
	}
	
	public Image getBoutonNormal(){
		return boutonNormal;
	}
	
	public boolean getSurvole(){
		return survole;
	}
	
	public GLabel getTexte(){
		return texte;
	}
	
	public void setSurvole(boolean p){
		survole = p;
	}
	
	public void setPositionCentre(double x, double y){
		posiX = x * dim.getWidth() - bouton.getWidth() / 2;
		posiY = y * dim.getHeight() - bouton.getHeight() / 2;
		
		bouton.setLocation(posiX, posiY);
		texte.setLocation(posiX + tailleX/2 - (int)texte.getWidth()/2, posiY + tailleY/2 + (int)texte.getHeight()/2 -7);
	}
	
	public boolean hitBox(int x,int y){	// méthode qui renvoie vrai si la sourie est sur le bouton ( pour le clic par exemple )
		if(x>=posiX && x<= posiX + tailleX && y>=posiY && y<=posiY+tailleY){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean gestionSurvole(int x, int y){
		if(hitBox(x, y) && survole == false){
			survole = true;
			bouton.setImage(boutonSurvole);
			bouton.setSize((bouton.getWidth() / 1920) * dim.getWidth(), (bouton.getHeight() / 1080) * dim.getHeight());
		}
		if(hitBox(x, y) == false && survole){
			survole = false;
			bouton.setImage(boutonNormal);
			bouton.setSize((bouton.getWidth() / 1920) * dim.getWidth(), (bouton.getHeight() / 1080) * dim.getHeight());
		}
		if(survole)	return true;
		return false;
	}
	
	public boolean getAffiche(){
		return affiche;
	}
	
	public void setAffiche(boolean p){
		affiche = p;
	}
}
