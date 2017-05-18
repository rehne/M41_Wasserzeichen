package app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Wasserzeichen {
	
	private JFrame frame;
	
	public Wasserzeichen(){
		fensterErzeugen();
	}
	
	private void beenden(){
		System.exit(0);
	}
	
	private void fensterErzeugen(){
		frame = new JFrame("Wasserzeichen App");
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));
		
		menuBarErzeugen(frame);
		
		frame.pack();
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
		frame.setVisible(true);
	}
	
	private void menuBarErzeugen(JFrame frame){
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		// Das Datei-Menü erzeugen
		JMenu dateiMenue = new JMenu("Datei");
		menuBar.add(dateiMenue);
		
		JMenuItem oeffnen = new JMenuItem("Öffnen..");
		dateiMenue.add(oeffnen);
		
		JMenuItem schliessen = new JMenuItem("Schliessen");
		dateiMenue.add(schliessen);
		
		JMenuItem speichernUnter = new JMenuItem("Speichern Unter..");
		dateiMenue.add(speichernUnter);
		
		dateiMenue.addSeparator();
		
		JMenuItem beenden = new JMenuItem("Beenden");
		beenden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				beenden();
			}
		});
		dateiMenue.add(beenden);
	}
}