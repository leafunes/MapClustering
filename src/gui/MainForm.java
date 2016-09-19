package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.osgi.framework.SynchronousBundleListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainForm {

	private JFrame frame;
	private JFileChooser fileChooser;
	private JTabbedPane tabbedPane;
	private JPanel mapPanel;
	
	File file;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files *.map", "map");
		fileChooser.setFileFilter(filter);
		
		tabsInit();
		menuInit();
	}
	
	private void tabsInit(){
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 448, 275);
		frame.getContentPane().add(tabbedPane);
		
		mapPanel = new JPanel();
		tabbedPane.addTab("Mapa", null, mapPanel, null);
		mapPanel.setLayout(null);
	}
	
	private void menuInit(){
		//Map Menu
		JMenuBar mapMenu = new JMenuBar();
		mapMenu.setBounds(0, 0, 443, 21);
		mapPanel.add(mapMenu);
		
		JMenu mapArchivo = new JMenu("Archivo");
		mapMenu.add(mapArchivo);
		
		JMenu mapOpciones = new JMenu("Opciones");
		mapMenu.add(mapOpciones);
		
		JMenuItem mapImportar = new JMenuItem("Importar");
		mapArchivo.add(mapImportar);
		
		JMenuItem mapExportar = new JMenuItem("Exportar");
		mapArchivo.add(mapExportar);
		
		mapImportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {;
				fileChooser.showOpenDialog(null);
				file = fileChooser.getSelectedFile();
				printFile();
				
				fileChooser.setSelectedFile(null);
				
			}
		});
		
		
		
		//Clusters Menu
	}
	
	public void printFile(){
		
		if(file != null){
			try {
				FileReader reader = new FileReader(file);
				BufferedReader buff = new BufferedReader(reader);
				
				String line = "";
				
				while((line = buff.readLine()) != null) System.out.println(line);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
