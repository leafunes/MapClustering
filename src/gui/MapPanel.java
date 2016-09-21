package gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
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
	
	private MapData mapData;
	
	private GetCoord getCoordWin;
	
	private File file;
	
	public MapPanel(Component parent){
		
		super.setBounds(parent.getBounds());
		setLayout(null);
		
		getCoordWin = new GetCoord();
		getCoordWin.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map Files *.map", "map");
		fileChooser.setFileFilter(filter);
		
		
		mapInit();
		newMap();
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
					
				}
				
				if(removeMarker){
					
					MapPoint toDelete = new MapPoint(coord.getLat(), coord.getLon());
					
					mapData.removeClosestTo(toDelete);
					
					map.removeAllMapMarkers();
					loadMapPoints();
				
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
				
				System.out.println("asd");
					
				mapData.removePoint(point);
				
				loadMapPoints();
				
			}
		});
		mapOpciones.add(removerConCoordenadas);
		
		JMenuItem mntmNuevoMapa = new JMenuItem("Nuevo mapa");
		mapArchivo.add(mntmNuevoMapa);
		
		JMenuItem mapImportar = new JMenuItem("Importar");
		mapArchivo.add(mapImportar);
		
		JMenuItem mapExportar = new JMenuItem("Exportar");
		mapArchivo.add(mapExportar);
		
		JToolBar toolBar = new JToolBar();
		mapMenu.add(toolBar);
		
		final JToggleButton tglbtnRemove = new JToggleButton("Remover marcadores");
		final JToggleButton tglbtnAgrega = new JToggleButton("Agregar marcadores");
		
		tglbtnAgrega.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (tglbtnRemove.isSelected()){
					tglbtnRemove.setSelected(false);
					removeMarker = false;
				};
				addMarkerFromMap = tglbtnAgrega.isSelected();
				
			}
		});
		toolBar.add(tglbtnAgrega);
		
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
		
		toolBar.add(tglbtnRemove);
		
		
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
		
	}
	
	public void importMapFile(){
		
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
