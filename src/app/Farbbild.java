package app;

import java.awt.image.BufferedImage;

public class Farbbild extends BufferedImage {
	
	public Farbbild(BufferedImage image){
		super(image.getColorModel(), image.copyData(null),
				image.isAlphaPremultiplied(), null);
	}
	
	public Farbbild(int breite, int hoehe){
		super(breite, hoehe, TYPE_INT_RGB);
	}
}