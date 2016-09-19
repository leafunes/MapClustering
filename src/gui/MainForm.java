package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.parser.ParseException;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import proc.MapData;
import proc.MapPoint;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

public class MainForm {

	private JFrame frame;
	private JFileChooser fileChooser;
	private JTabbedPane tabbedPane;
	private JPanel mapPanel;
	private JMapViewer map;
	
	private MapData mapData;
	
	private File file;
	
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
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mapData = new MapData();
		
		fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files *.map", "map");
		fileChooser.setFileFilter(filter);
		
		tabsInit();
		mapInit();

		menuInit();
	}
	
	private void tabsInit(){
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 798, 575);
		frame.getContentPane().add(tabbedPane);
		
		mapPanel = new JPanel();
		mapPanel.setBounds(0, 0, 448, 275);
		tabbedPane.addTab("Mapa", null, mapPanel, null);
		mapPanel.setLayout(null);
	}
	
	private void menuInit(){
		//Map Menu
		JMenuBar mapMenu = new JMenuBar();
		mapMenu.setBounds(0, 0, 793, 21);
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
				importMapFile();
				loadMapPoints();
				fileChooser.setSelectedFile(null);
				
			}
		});
		
		
		
		//Clusters Menu
	}
	
	private void loadMapPoints() {
		
		ArrayList<MapPoint> points = mapData.getPoints();
		
		for(MapPoint point : points){
			
			Coordinate coord = new Coordinate(point.getLat(), point.getLon());
			
			MapMarkerDot marker = new MapMarkerDot(coord);
			
			map.addMapMarker(marker);
			
		}
		
	}

	public void mapInit(){
		
		map = new JMapViewer();
		map.setBounds(new Rectangle(0, 21, 793, 527));
		mapPanel.add(map);
		
		
	}
	
	public void importMapFile(){
		
		if(file != null){
			try {
				mapData.loadFromFile(file);
				
			} catch (IOException | ParseException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(mapPanel, "No se pudo cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}
	
}
