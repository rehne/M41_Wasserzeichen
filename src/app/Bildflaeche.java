package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Bildflaeche extends JComponent {

	private static final long serialVersionUID = 1L;
	private int breite, hoehe;
	private Farbbild bild;
	
	// die Ausgangsgröße der Bildfläche in der GUI bestimmen
	public Bildflaeche(){
		breite = 360;
		hoehe = 240;
		bild = null;
	}
	
    public void setzeBild(Farbbild bild) {
        if(bild != null) {
            breite = bild.getWidth();
            hoehe = bild.getHeight();
            this.bild = bild;
            repaint();
        }
    }
    
    public void loeschen(){
    	Graphics bildGraphics = bild.getGraphics();
    	bildGraphics.setColor(Color.LIGHT_GRAY);
    	bildGraphics.fillRect(0, 0, breite, hoehe);
    	repaint();
    }
    
    public void paintComponent(Graphics g) {
    	Dimension size = getSize();
    	g.clearRect(0, 0, size.width, size.height);
    	if(bild != null) {
    		g.drawImage(bild, 0, 0, null);
    	}
    }
}