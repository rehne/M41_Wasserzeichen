package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dateimanager {
	
	public static Farbbild ladeBild(File bildDatei){
		try {
			BufferedImage bild = ImageIO.read(bildDatei);
			
			// Abfrage ob das Bild geladen werden kann
			if(bild == null || (bild.getWidth() < 0)){
				return null;
			} else {
				return new Farbbild(bild);
			}
		} catch (IOException e) {
			return null;
		}
	}
}