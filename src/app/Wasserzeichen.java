package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Wasserzeichen {
	
	private static final String VERSION = "Version 0.0.2";
	private static JFileChooser dateiauswahldialog = new JFileChooser(System.getProperty("user.dir"));
	
	private JFrame frame;
	private Bildflaeche bildflaeche;
	private Farbbild aktuellesBild;
	
	public Wasserzeichen(){
		fensterErzeugen();
	}
	
	private void oeffnen(){
		int ergebnis = dateiauswahldialog.showOpenDialog(frame);
		
		// Wenn bei der Bildauswahl im Explorer "Abbrechen" gewählt wird,
		// wird keine Fehlermeldung ausgegeben
		if(ergebnis != JFileChooser.APPROVE_OPTION){
			return;
		}
		File selektierteDatei = dateiauswahldialog.getSelectedFile();
		aktuellesBild = Dateimanager.ladeBild(selektierteDatei);
		bildflaeche.setzeBild(aktuellesBild);
	}
	
	private void schliessen(){
		
	}
	
	private void speichernUnter(){
		
	}
	
	private void beenden(){
		System.exit(0);
	}
	
	// Info Box mit Versionsnummer
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
		contentPane.setBorder(new EmptyBorder(7, 7, 7, 7));
		
		menuBarErzeugen(frame);

		bildflaeche = new Bildflaeche();
		contentPane.add(bildflaeche, BorderLayout.CENTER);
		
		FlowLayout flow = new FlowLayout();
		JPanel addWatermarkPane = new JPanel();
		
		JLabel watermarkLabel = new JLabel("Wasserzeichen: ");
		addWatermarkPane.add(watermarkLabel);
		
		JTextField watermark = new JTextField("hier eingeben");
		addWatermarkPane.add(watermark);
		
		JButton burnWatermark = new JButton("Einbrennen");
		addWatermarkPane.add(burnWatermark);
		
		addWatermarkPane.setLayout(flow);
		
		frame.add(addWatermarkPane, BorderLayout.SOUTH);
		
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
			public void actionPerformed(ActionEvent e) {
				oeffnen();
			}
		});
		dateiMenue.add(oeffnen);
		
		JMenuItem schliessen = new JMenuItem("Schliessen");
		schliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schliessen();
			}
		});
		dateiMenue.add(schliessen);

		dateiMenue.addSeparator();
		
		JMenuItem speichernUnter = new JMenuItem("Speichern Unter ...");
		speichernUnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speichernUnter();
			}
		});
		dateiMenue.add(speichernUnter);
		
		dateiMenue.addSeparator();
		
		JMenuItem beenden = new JMenuItem("Beenden");
		beenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beenden();
			}
		});
		dateiMenue.add(beenden);
		
		JMenu infoMenu = new JMenu("?");
		menuBar.add(infoMenu);
		
		JMenuItem info = new JMenuItem("Info");
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				info();
			}
		});
		infoMenu.add(info);
	}
}