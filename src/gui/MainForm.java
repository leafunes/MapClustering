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
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainForm {

	private JFrame frame;
	private JFileChooser fileChooser;
	private JTabbedPane tabbedPane;
	private JPanel mapPanel;
	private JMapViewer map;
	
	private boolean addMarkerFromMap;
	private boolean removeMarker;
	
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
		
		
		fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files *.map", "map");
		fileChooser.setFileFilter(filter);
		
		tabsInit();
		mapInit();
		
		newMap();

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
		
		JMenu mapAgregarMarcador = new JMenu("Agregar marcador");
		mapOpciones.add(mapAgregarMarcador);
		
		JMenuItem mapCoordenadas = new JMenuItem("Con coordenadas");
		mapAgregarMarcador.add(mapCoordenadas);
		
		JMenuItem mapDesdeMapa = new JMenuItem("Desde el mapa");
		mapAgregarMarcador.add(mapDesdeMapa);
		
		JMenu mnRemoverMarcador = new JMenu("Remover marcador");
		mapOpciones.add(mnRemoverMarcador);
		
		JMenuItem mntmConCoordenadas = new JMenuItem("Con coordenadas");
		mnRemoverMarcador.add(mntmConCoordenadas);
		
		JMenuItem mntmDesdeElMapa = new JMenuItem("Desde el mapa");
		mnRemoverMarcador.add(mntmDesdeElMapa);
		
		JMenuItem mntmNuevoMapa = new JMenuItem("Nuevo mapa");
		mapArchivo.add(mntmNuevoMapa);
		
		JMenuItem mapImportar = new JMenuItem("Importar");
		mapArchivo.add(mapImportar);
		
		JMenuItem mapExportar = new JMenuItem("Exportar");
		mapArchivo.add(mapExportar);
		
		mapImportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(null);
				file = fileChooser.getSelectedFile();
				importMapFile();
				loadMapPoints();
				fileChooser.setSelectedFile(null);
				
			}
		});
		
		mapExportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showSaveDialog(null);
				file = fileChooser.getSelectedFile();
				if (fileChooser.getFileFilter().getDescription().equals("Map Files *.map"))
					file = new File(file.getAbsoluteFile() + ".map");
					
				exportMapFile();
				fileChooser.setSelectedFile(null);
				
			}
		});
		
		mapDesdeMapa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addMarkerFromMap = true;
				
			}
		});
		
		mapCoordenadas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String latStr = JOptionPane.showInputDialog("Latitud");
				String lonStr = JOptionPane.showInputDialog("Longitud");
				if(latStr != null && lonStr != null){
					double lat = Double.parseDouble(latStr);
					double lon = Double.parseDouble(lonStr);
					
					Coordinate coord = new Coordinate(lat, lon);
					
					mapData.addPoint(new MapPoint(coord.getLat(), coord.getLon()));
					
					MapMarkerDot toAdd = new MapMarkerDot(coord);
					map.addMapMarker(toAdd);
				}
			}
		});
		
		mntmDesdeElMapa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeMarker = true;
			}
		});
		
		
		
		//Clusters Menu
	}
	
	protected void exportMapFile() {
		try {
			mapData.saveToFile(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mapPanel, "No se pudo guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}

	private void loadMapPoints() {
		
		ArrayList<MapPoint> points = mapData.getPoints();
		
		for(MapPoint point : points){
			
			//TODO
			//map.setDisplayPositionByLatLon(point.getLat(), point.getLon(), 10);
			
			Coordinate coord = new Coordinate(point.getLat(), point.getLon());
			
			MapMarkerDot marker = new MapMarkerDot(coord);
			
			map.addMapMarker(marker);
			
		}
		
	}

	public void mapInit(){
		
		map = new JMapViewer();
		map.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Coordinate coord = map.getPosition(e.getX(),e.getY());
				
				if(addMarkerFromMap){
					
					
					mapData.addPoint(new MapPoint(coord.getLat(), coord.getLon()));
					
					MapMarkerDot toAdd = new MapMarkerDot(coord);
					map.addMapMarker(toAdd);
					
					addMarkerFromMap = false;
					
					
				}
				
				if(removeMarker){
					
					MapPoint toDelete = new MapPoint(coord.getLat(), coord.getLon());
					
					mapData.removeClosestTo(toDelete);
					
					map.removeAllMapMarkers();
					loadMapPoints();
					removeMarker = false;
				
				}
			}
		});
		map.setBounds(new Rectangle(0, 21, 793, 527));
		mapPanel.add(map);
		
		
	}
	
	public void importMapFile(){
		
		if(file != null){
			try {
				newMap();
				mapData.loadFromFile(file);
				
			} catch (IOException | ParseException e) {
				JOptionPane.showMessageDialog(mapPanel, "No se pudo cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}

	private void newMap() {
		mapData = new MapData();
		map.removeAllMapMarkers();
	}
}
