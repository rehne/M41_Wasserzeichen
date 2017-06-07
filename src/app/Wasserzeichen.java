package app;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;  

public class Wasserzeichen {
	
	/**
	 * Ließt ein Bild ein, schreibt das Wasserzeichen ein und speichert ein neues Bild mit dem Wasserzeichen ab
	 * @param aktuellesBild - Pfad des originallen Bildes
	 * @param ziel - Pfad des Bildes mit dem Wasserzeichen
	 * @param wasserzeichen - Das Wasserzeichen, welches in das Bild eingeschrieben werden soll
	 */
	public static void schreibeWasserzeichen(File aktuellesBild, File ziel, String wasserzeichen) {
		//Bild laden
		BufferedImage bild = null;
		try {
			bild = ImageIO.read(aktuellesBild);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		//Schreibe den Anfangsmarker ÿÿÿ ins Bild
		for(int i = 0; i < 3; i++) {
			schreibeCharInPixel('ÿ', i, bild);
		}
		
		//Schreibe Wasserzeichen ins Bild
		for(int i = 0; i < wasserzeichen.length(); i++) {
			schreibeCharInPixel(wasserzeichen.charAt(i), i + 3, bild);
		}
		
		//Schreibe den Schlussmarker ÿÿÿ ins Bild
		for(int i = wasserzeichen.length(); i < wasserzeichen.length() + 3; i++) {
			schreibeCharInPixel('ÿ', i + 3, bild);
		}
		
		
		//Bild abspeichern
		try{
            ImageIO.write(bild, "png", ziel);
        }catch(IOException e){
            System.out.println(e);
        }
		
	}
	
	/**
	 * Ließt ein Wasserzeichen aus einem Bild aus.
	 * @param bildMitWasserzeichen - Das Bild, aus dem das Wasserzeichen gelesen werden soll
	 * @return gibt entweder das Wasserzeichen oder eine Fehlermeldung, falls das Bild kein Wasserzeichen hat, zurück
	 */
	public static String leseWasserzeichen(File bildMitWasserzeichen) {
		//Bild laden
		BufferedImage bild = null;
		try {
			bild = ImageIO.read(bildMitWasserzeichen);	
		} catch (IOException e) {	
			e.printStackTrace();		
		}
		
		//Bricht den Methodenaufruf ab, wenn das Bild zu wenige Pixel hat
		if( 6*8 >= bild.getWidth() * bild.getHeight() ) return "Das Bild ist zu klein für ein Wasserzeichen.";
		
		String Wasserzeichen = "";
		//Überprüfe, ob Anfangsmarker ÿÿÿ vorhanden ist
		if(leseCharAusPixel(0, bild) == 'ÿ' && leseCharAusPixel(1, bild) == 'ÿ' && leseCharAusPixel(2, bild) == 'ÿ') {
			
			//Lese Character aus dem Bild, solange der Endmarker noch nicht gefunden wurde und nicht auf Pixel außerhalb des Bildes zugegriffen wird
			for(int i = 3; !(leseCharAusPixel(i, bild)     == 'ÿ'  &&
							 leseCharAusPixel(i + 1, bild) == 'ÿ'  &&
							 leseCharAusPixel(i + 2, bild) == 'ÿ') &&
							 i * 8 <= bild.getWidth() * bild.getHeight(); i++) {
				Wasserzeichen += leseCharAusPixel(i, bild);
			}
			//Überprüfe, ob Endmarker ÿÿÿ gesetzt wurde und ob das Bild überhaupt noch genug Platz für den Endmarker hat
			if( (leseCharAusPixel(Wasserzeichen.length() + 3, bild) == 'ÿ'  &&
				 leseCharAusPixel(Wasserzeichen.length() + 4, bild) == 'ÿ'  &&
				 leseCharAusPixel(Wasserzeichen.length() + 5, bild) == 'ÿ') &&
				 (Wasserzeichen.length() + 3) * 8 <= bild.getWidth() * bild.getHeight()) {
				return Wasserzeichen;
			}
		}
		
		//Falls kein Wasserzeichen gefunden wurde
		return "Das Bild enthählt kein Wasserzeichen.";
	}
	
	/**
	 * Ließt ein Character aus dem Bild
	 * @param charNr - Nummer des Zeichen, welches aus dem Bild ausgelesen wird.
	 * @param bild - Das Bild, aus dem ausgelesen werden soll.
	 * @return Es wird das ausgelesene Character zurückgegeben
	 */
	private static char leseCharAusPixel(int charNr, BufferedImage bild) {
		int pixelNr = charNr * 8;
		
		//Koordinaten im Bild der pixelNr bestimmen
		int x = pixelNr % bild.getWidth();
		int y = (pixelNr) / bild.getWidth();
		
		//Binärzahl auslesen
		int[] binaer = new int[8];
		for(int i = 7; i >= 0; i--) {
			
			//RGB-Wert vom Pixel bei (x,y) abrufen
			Color farbe = new Color(bild.getRGB(x, y));
			int blauwert = farbe.getBlue();
			
			//Blauwert in Binärwert umwandeln, 0 = gerade Zahl, 1 = ungerade Zahl
			binaer[i] = blauwert % 2;
			
			//Zum nächsten Pixel gehen
			x++;
			if(x >= bild.getWidth()) {
				x=0;
				y++;
			}
		}
		return binaerZuChar(binaer);
	}
	
	/** 
	 * Schreibt ein Character in das Bild.
	 * 
	 * @param c - Character, das ins Bild geschrieben werden soll.
	 * @param charNr -Nummer des Zeichen, welches ins Bild geschrieben wird.
	 * @param bild - Das Bild, in das geschrieben werden soll.
	 */
	private static void schreibeCharInPixel(char c, int charNr, BufferedImage bild) {
		int pixelNr = charNr * 8;
		
		//Koordinaten im Bild der pixelNr bestimmen
		int x = pixelNr % bild.getWidth();
		int y = (pixelNr) / bild.getWidth();
		int[] binaer = charZuBinaer(c);
		
		for(int i = 7; i >= 0; i--) {//durchläuft Binärzahl
			
			//RGB-Wert vom Pixel bei (x, y) abrufen
			Color farbe = new Color(bild.getRGB(x, y));
			int blauwert = farbe.getBlue();
			
			//0 = gerade Zahl, 1 = ungerade Zahl
			if(binaer[i] == 0) {
				if(blauwert % 2 == 1) {//Blauwert ist ungerade und soll gerade werden
					blauwert--;	
				}
			} else {
				if(blauwert % 2 == 0) {//Blauwert ist gerade und soll ungerade werden
					blauwert++;
				}
			}
			
			//neuen Blauwert übernehmen
			farbe = new Color(farbe.getRed(), farbe.getGreen(), blauwert, farbe.getAlpha() );
			//Pixel im Bild verändern
			bild.setRGB(x, y, farbe.getRGB());
			
			//Zum nächsten Pixel gehen
			x++;
			if(x >= bild.getWidth()) {
				x=0;
				y++;
			}
		}
	}
	
	/** 
	 * Wandelt ein Character in eine Binärzahl um. 
	 * Die Binärzahl wird in ein Integer-Array abgelegt, wobei die Indizes der Stellenwertigkeit entsprechen.
	 * Funktioniert nur mit Werten zwischen 0 und 255.
	 * 
	 * @param c - Das Character, welches umgewandelt werden soll.
	 * @return binaer - Die c ensprechender Binärzahl in Form eines Arrays
	 */
	private static int[] charZuBinaer(char c) {
		int[] binaer = new int[8];
		for(int i = 0; i < 8; i++) binaer[i] = (c >>> i) &1; 
		return binaer;
	}
	
	/** 
	 * Wandelt eine Binärzahl in Form eines Integer-Arrays in ein Character um.
	 * 
	 * @param binaer - Die Binarzahl, die umgewandelt werden soll.
	 * @return c, Das der Binarzahl entsprechende Character
	 */
	private static char binaerZuChar(int[] binaer) {
		char c = 0;
		for(int i = 0; i < 8; i++) c += (binaer[i] << i);
		return c;
	}
	
	/** 
	 * Prüft, ob das Bild genug Pixel für das Wasserzeichen hat.
	 * 
	 * @param wasserzeichen - Das Wasserzeichen, das eingebetet werden soll.
	 * @param bild - Das Bild, in das das Wasserzeichen eingebetet werden soll.
	 * @return true, wenn das Bild genug Pixel hat
	 */
	public static boolean hatGenugPixel(File quelle, String wasserzeichen) {
		BufferedImage bild = null;
		try {
			bild = ImageIO.read(quelle);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Jedes Zeichen nimmt 8 Pixel in Anspruch.
		//6 * 8 repräsentiert die Marker, welche den Anfang und das Ende des Wasserzeichens kennzeichnen.
		long anzCharPixel = wasserzeichen.length() * 8 + 6 * 8;
		long anzPixel = bild.getWidth() * bild.getHeight();
		return anzPixel >= anzCharPixel;
	}
}