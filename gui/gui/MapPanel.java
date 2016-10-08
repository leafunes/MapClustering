package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.parser.ParseException;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import map.Cluster;
import map.MapData;
import map.MapPoint;

import javax.swing.JToolBar;
import javax.swing.JToggleButton;

public class MapPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private JMapViewer map;

	private boolean addMarkerFromMap;
	private boolean removeMarker;
	
	MapData<MapPoint> mapData;
	
	MapPoint.Exportator exportator = new MapPoint.Exportator();
	
	private GetCoord getCoordWin;
	
	private File file;
	
	private final double CLOSEST_LIMIT = 2E-1;
	
	public MapPanel(Component parent){
		
		super.setBounds(parent.getBounds());
		setLayout(null);
		
		getCoordWin = new GetCoord();
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files *.map", "map");
		fileChooser.setFileFilter(filter);
		
		
		mapInit();
		newMap();
		menuInit();
		
	}

	private void mapInit() {
		map = new JMapViewer();
		
		new DefaultMapController(map){
			
			@Override
			public void mouseMoved(MouseEvent e){
				
				if(removeMarker){

		    		Coordinate coord = map.getPosition(e.getX(),e.getY());
					plotPointToRemove(coord);
				}
				
				if(addMarkerFromMap){

		    		Coordinate coord = map.getPosition(e.getX(),e.getY());
					plotPointToAdd(coord);
				}
			}

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if(e.getButton() == MouseEvent.BUTTON1){
		    		Coordinate coord = map.getPosition(e.getX(),e.getY());
		    		
		        	if(addMarkerFromMap) addPoint(coord);
		        	if(removeMarker) removePoint(coord);
		        }
		    }
			};
			
		map.setBounds(new Rectangle(0, 21, 793, 527));
		this.add(map);
		
	}
	
	private void newMap() {
		mapData = new MapData<>(exportator);
		actualizePoints();
	}
	
	

	private void menuInit() {
		JMenuBar mapMenu = new JMenuBar();
		mapMenu.setBounds(0, 0, 793, 21);
		this.add(mapMenu);
		
		JMenu mapArchivo = new JMenu("Archivo");
		mapMenu.add(mapArchivo);
		
		JMenu mapOpciones = new JMenu("Opciones");
		mapMenu.add(mapOpciones);
		
		JMenuItem mapAgregaCoordenadas = new JMenuItem("Agrregar desde coord.");
		mapOpciones.add(mapAgregaCoordenadas);
		
		mapAgregaCoordenadas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getCoordWin.setVisible(true);
				
				MapPoint point = getCoordWin.getPoint();
					
				Coordinate coord = point.toCoordinate();
					
				mapData.addPoint(point);
				MapMarkerDot toAdd = new MapMarkerDot(coord);
				map.addMapMarker(toAdd);
				
			}
		});
		
		JMenuItem removerConCoordenadas = new JMenuItem("Remover desde coord.");
		removerConCoordenadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				getCoordWin.setVisible(true);
				
				MapPoint point = getCoordWin.getPoint();
					
				mapData.removePoint(point);
				
				loadMapPoints();
				
			}
		});
		mapOpciones.add(removerConCoordenadas);
		
		JMenuItem mntmNuevoMapa = new JMenuItem("Nuevo mapa");
		mapArchivo.add(mntmNuevoMapa);
		mntmNuevoMapa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mapData = new MapData<>(exportator);
				actualizePoints();
				
			}
		});
		
		JMenuItem mapImportar = new JMenuItem("Importar");
		mapArchivo.add(mapImportar);
		
		JMenuItem mapExportar = new JMenuItem("Exportar");
		mapArchivo.add(mapExportar);
		
		final JToggleButton tglbtnRemove = new JToggleButton("Remover marcadores");
		final JToggleButton tglbtnAgrega = new JToggleButton("Agregar marcadores");
		
		tglbtnAgrega.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				actualizePoints();
				
				if (tglbtnRemove.isSelected()){
					tglbtnRemove.setSelected(false);
					removeMarker = false;
				};
				addMarkerFromMap = tglbtnAgrega.isSelected();
				
			}
		});
		mapMenu.add(tglbtnAgrega);
		
		tglbtnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (tglbtnAgrega.isSelected()){
					tglbtnAgrega.setSelected(false);
					addMarkerFromMap = false;
				}
				removeMarker = tglbtnRemove.isSelected();
				
			}
		});
		
		mapMenu.add(tglbtnRemove);
		
		
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
				if (fileChooser.getFileFilter().getDescription().equals("Map Files *.map") && file != null)
					file = new File(file.getAbsoluteFile() + ".map");
					
				exportMapFile();
				fileChooser.setSelectedFile(null);
				
			}
		});
		
	}
	
	private void plotPointToRemove(Coordinate coord){

		MapPoint point = new MapPoint(coord.getLat(), coord.getLon());
		
		MapPoint closest = mapData.closestTo(point, CLOSEST_LIMIT);
		
		actualizePoints();
		
		if(closest != null){
			
			Coordinate closestCoord = new Coordinate(closest.getLat(), closest.getLon());
			
			MapMarkerDot marker = new MapMarkerDot(closestCoord);
			marker.setBackColor(Color.magenta);
			marker.setColor(Color.magenta);
			
			map.addMapMarker(marker);
			
		}
	}
	
	private void plotPointToAdd(Coordinate coord){
		
		actualizePoints();
		
		MapMarkerDot marker = new MapMarkerDot(coord);
			
		map.addMapMarker(marker);
			
		
	}
	
	private void addPoint(Coordinate coord){
		
		mapData.addPoint(new MapPoint(coord.getLat(), coord.getLon()));
			
		MapMarkerDot toAdd = new MapMarkerDot(coord);
		map.addMapMarker(toAdd);
			
	}
	
	private void removePoint(Coordinate coord){
		
		MapPoint toDelete = new MapPoint(coord.getLat(), coord.getLon());
		
		mapData.removeClosestTo(toDelete, CLOSEST_LIMIT);
		
		actualizePoints();
		
	}
	
	private void importMapFile(){
		
		if(file != null){
			try {
				newMap();
				mapData.loadFromFile(file);
				
			} 
			catch (IOException e) {
				JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		
			catch (ClassCastException | ParseException e){
				JOptionPane.showMessageDialog(this, "El archivo no tiene el formato requerido", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}
	
	private void exportMapFile() {
		try {
			if(file != null)mapData.saveToFile(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	private void loadMapPoints() {
		
		
		List<MapPoint> points = mapData.getPoints();
		
		for(MapPoint point : points){
			
			//TODO
			//map.setDisplayPositionByLatLon(point.getLat(), point.getLon(), 10);
			
			Coordinate coord = new Coordinate(point.getLat(), point.getLon());
			
			MapMarkerDot marker = new MapMarkerDot(coord);
			
			map.addMapMarker(marker);
			
		}
		
	}
	
	private void actualizePoints(){
		
		map.removeAllMapMarkers();
		loadMapPoints();
	}
}
