package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class WasserzeichenFenster {
	
	private static final String VERSION = "Version 1.0.0";
	private static JFileChooser dateiauswahldialog = new JFileChooser(System.getProperty("user.dir"));
	
	private JFrame frame;
	private Bildflaeche bildflaeche;
	private File selektierteDatei;
	private Farbbild aktuellesBild;
	private String watermarkText = "";
	
	public WasserzeichenFenster(){
		fensterErzeugen();
	}
	
	private void oeffnen(){
		int ergebnis = dateiauswahldialog.showOpenDialog(frame);
		
		// Wenn bei der Bildauswahl im Explorer "Abbrechen" gewählt wird,
		// wird keine Fehlermeldung ausgegeben
		if(ergebnis != JFileChooser.APPROVE_OPTION){
			return;
		}
		selektierteDatei = dateiauswahldialog.getSelectedFile();
		aktuellesBild = Dateimanager.ladeBild(selektierteDatei);
		bildflaeche.setzeBild(aktuellesBild);
	}
	
	private void schliessen(){
		aktuellesBild = null;
		bildflaeche.loeschen();
	}
	
	private void speichernUnter(){
		
	}
	
	private void beenden(){
		System.exit(0);
	}
	
	// Info Box mit Versionsnummer
	private void info(){
		JOptionPane.showMessageDialog(
			frame,
			"Gruppe M41\n"
			+ "Wasserzeichen\n\n"
			+ "Gruppenmitglieder:\n\n"
			+ "Nathalie Schmitz\n"
			+ "Christopher Poloczek\n"
			+ "Philipp Schmeier\n"
			+ "Maik Foitzik\n"
			+ "René Honnen\n\n"
			+ VERSION,
			"Info über Wasserzeichen",
			JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	private void fensterErzeugen(){
		frame = new JFrame("Wasserzeichen App");
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setBorder(new EmptyBorder(7, 7, 7, 7));
		
		menuBarErzeugen(frame);

		bildflaeche = new Bildflaeche();
		contentPane.add(bildflaeche, BorderLayout.CENTER);
		contentPane.setPreferredSize(new Dimension(700, 460));
		
		FlowLayout watermarks = new FlowLayout();
		JPanel watermarkPanes = new JPanel();
		watermarkPanes.setLayout(watermarks);
		
		FlowLayout write = new FlowLayout();
		JPanel writeWatermarkPane = new JPanel();
		writeWatermarkPane.setPreferredSize(new Dimension(385, 130));
		writeWatermarkPane.setLayout(write);
		
		FlowLayout read = new FlowLayout();
		JPanel readWatermarkPane = new JPanel();
		readWatermarkPane.setPreferredSize(new Dimension(385, 130));
		readWatermarkPane.setLayout(read);
		
		JLabel addWatermarkLabel = new JLabel("Wasserzeichen: ");
		writeWatermarkPane.add(addWatermarkLabel, BorderLayout.NORTH);
		
		JTextField watermark = new JTextField("hier eingeben");
		watermark.setPreferredSize(new Dimension(150, 100));
		writeWatermarkPane.add(watermark, BorderLayout.CENTER);
		
		JButton burnWatermark = new JButton("Einbrennen");
		writeWatermarkPane.add(burnWatermark, BorderLayout.SOUTH);
		burnWatermark.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File output = new File("./out.png");
				Wasserzeichen.schreibeWasserzeichen(selektierteDatei, output, watermark.getText());
			}
		});

		JLabel readWatermarkLabel = new JLabel("Wasserzeichen ausgelesen: ");
		readWatermarkPane.add(readWatermarkLabel, BorderLayout.NORTH);

		JLabel watermarkTextLabel = new JLabel(watermarkText);
		readWatermarkPane.add(watermarkTextLabel, BorderLayout.SOUTH);
		
		JButton readWatermark = new JButton("Auslesen");
		readWatermarkPane.add(readWatermark, BorderLayout.SOUTH);
		readWatermark.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				watermarkTextLabel.setText(Wasserzeichen.leseWasserzeichen(selektierteDatei));
			}
		});
		
		frame.add(watermarkPanes, BorderLayout.SOUTH);
		watermarkPanes.add(writeWatermarkPane, BorderLayout.WEST);
		watermarkPanes.add(readWatermarkPane, BorderLayout.EAST);
		
		frame.pack();
		
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	private void menuBarErzeugen(JFrame frame){
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		// Das Datei-Menü erzeugen
		JMenu dateiMenue = new JMenu("Datei");
		menuBar.add(dateiMenue);
		
		JMenuItem oeffnen = new JMenuItem("Öffnen ...");
		oeffnen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				oeffnen();
			}
		});
		dateiMenue.add(oeffnen);
		
		JMenuItem schliessen = new JMenuItem("Schliessen");
		schliessen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				schliessen();
			}
		});
		dateiMenue.add(schliessen);
		
		JMenuItem speichernUnter = new JMenuItem("Speichern Unter");
		speichernUnter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				speichernUnter();
			}
		});

		dateiMenue.addSeparator();
		
		dateiMenue.add(speichernUnter);
		
		dateiMenue.addSeparator();
		
		JMenuItem beenden = new JMenuItem("Beenden");
		beenden.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				beenden();
			}
		});
		dateiMenue.add(beenden);
		
		// Das Info-Menü erzeugen
		JMenu infoMenu = new JMenu("Info");
		menuBar.add(infoMenu);
		
		JMenuItem info = new JMenuItem("Über diese App");
		info.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				info();
			}
		});
		infoMenu.add(info);
	}
}