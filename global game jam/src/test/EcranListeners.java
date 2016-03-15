package test;
/*
 * Cette classe sert a récupérer les actions de la souris et a déclancher le timer qui lui correspond.
 * Ce timer est ensuite utilisé dans la classe Jeu de la meme manière qu'un " public void mouseClicked(MouseEvent e) "
 */

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import acm.util.SwingTimer;


@SuppressWarnings("serial")
public class EcranListeners extends JPanel implements MouseListener, MouseMotionListener{
	public int posiSourisX, posiSourisY, noBouton;
	public SwingTimer sourisClicked = new SwingTimer(0, null), sourisEntered = new SwingTimer(0, null), sourisExited = new SwingTimer(0, null), sourisPressed = new SwingTimer(0, null), sourisReleased = new SwingTimer(0, null), sourisDragged = new SwingTimer(0, null), sourisMoved = new SwingTimer(0, null);
	
	public EcranListeners(Dimension dm){
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setSize(dm);
		this.setOpaque(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		posiSourisX = e.getX();
		posiSourisY = e.getY();
		noBouton = e.getButton();
		sourisClicked.start();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		posiSourisX = e.getX();
		posiSourisY = e.getY();
		noBouton = e.getButton();
		sourisEntered.start();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		posiSourisX = e.getX();
		posiSourisY = e.getY();
		noBouton = e.getButton();
		sourisExited.start();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		posiSourisX = e.getX();
		posiSourisY = e.getY();
		noBouton = e.getButton();
		sourisPressed.start();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		posiSourisX = e.getX();
		posiSourisY = e.getY();
		noBouton = e.getButton();
		sourisReleased.start();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		posiSourisX = e.getX();
		posiSourisY = e.getY();
		noBouton = e.getModifiers();
		sourisDragged.start();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		posiSourisX = e.getX();
		posiSourisY = e.getY();
		sourisMoved.start();
	}

}
