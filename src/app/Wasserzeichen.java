package app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Wasserzeichen {
	
	private static final String VERSION = "Version 0.0.1";
	private JFrame frame;
	
	public Wasserzeichen(){
		fensterErzeugen();
	}
	
	private void oeffnen(){
		
	}
	
	private void schliessen(){
		
	}
	
	private void speichernUnter(){
		
	}
	
	private void beenden(){
		System.exit(0);
	}
	
	private void info(){
		JOptionPane.showMessageDialog(frame,
				"Gruppe M41\n"
				+ "Wasserzeichen\n\n"
				+ "Gruppenmitglieder:\n\n"
				+ "Nathalie Schmitz\n"
				+ "Christopher Poloczek\n"
				+ "Philipp Schmeier\n"
				+ "Maik Foitzek\n"
				+ "René Honnen\n\n"
				+ VERSION,
				"Info über Wasserzeichen",
				JOptionPane.INFORMATION_MESSAGE);
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
		
		JMenuItem oeffnen = new JMenuItem("Öffnen ...");
		oeffnen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				oeffnen();
			}
		});
		dateiMenue.add(oeffnen);
		
		JMenuItem schliessen = new JMenuItem("Schliessen");
		schliessen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				schliessen();
			}
		});
		dateiMenue.add(schliessen);
		
		JMenuItem speichernUnter = new JMenuItem("Speichern Unter ...");
		speichernUnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speichernUnter();
			}
		});
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
		
		JMenu infoMenu = new JMenu("?");
		menuBar.add(infoMenu);
		
		JMenuItem info = new JMenuItem("Info");
		info.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				info();
			}
		});
		infoMenu.add(info);
	}
}