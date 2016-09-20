package gui;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.parser.ParseException;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import proc.MapData;
import proc.MapPoint;

public class MapPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private JMapViewer map;

	private boolean addMarkerFromMap;
	private boolean removeMarker;
	
	private MapData mapData;
	
	private File file;
	
	public MapPanel(Component parent){
		
		super.setBounds(parent.getBounds());
		super.setLayout(null);
		
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files *.map", "map");
		fileChooser.setFileFilter(filter);
		
		
		mapInit();
		menuInit();
		
	}

	private void mapInit() {
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
		this.add(map);
		
	}
	
	private void newMap() {
		mapData = new MapData();
		map.removeAllMapMarkers();
	}

	private void menuInit() {
		JMenuBar mapMenu = new JMenuBar();
		mapMenu.setBounds(0, 0, 793, 21);
		this.add(mapMenu);
		
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
		
	}
	
	public void importMapFile(){
		
		if(file != null){
			try {
				newMap();
				mapData.loadFromFile(file);
				
			} catch (IOException | ParseException e) {
				JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}
	
	protected void exportMapFile() {
		try {
			mapData.saveToFile(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			
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
	
}
