package app;

import java.io.File;

public class tester {

	public static void main(String[] args) {
		File input = new File("./in.png");
		File output = new File("./out.png");
		String wasserzeichen = "Hier könnte Ihre Werbung stehen";
		Wasserzeichen.schreibeWasserzeichen(input, output, wasserzeichen);
		
		//Wasserzeichen auslesen
		String outputtest = Wasserzeichen.leseWasserzeichen(output);
		System.out.println(outputtest);		
	}
}