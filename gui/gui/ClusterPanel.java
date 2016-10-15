package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.parser.ParseException;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import graph.Distanciable;
import map.Cluster;
import map.MapPoint;
import mapSolvers.LongerEdge;
import mapSolvers.LongerEdgeProm;
import mapSolvers.MapSolver;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

public class ClusterPanel  extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private JMapViewer map;
	private File file;
	private int cantClusters;
	
	//Pop-ups windows
	private EditColor editColor;
	private ClusterConfig clusterConfig;
	private JFileChooser fileChooser;
	
	//Variables de estados
	private boolean hasActualized;
	private boolean editPoints;
	
	//Constantes
	private final Random gen = new Random();
	private final double CLOSEST_LIMIT = 5E-2;

	//Generics
	List <MapPoint> pointList;
	List<MapSolver<MapPoint>> mapSolversList;
	List<Cluster<MapPoint>> clusters;
	MapSolver<MapPoint> selectedSolver;
	
	//Exportators
	MapPoint.Exportator exportator = new MapPoint.Exportator();
	
	//-----------------------------------------------//
	
	ClusterPanel(Component parent){
		
		setLayout(null);
		super.setBounds(parent.getBounds());
		
		LongerEdge<MapPoint> solverLongerEdge = new LongerEdge<>();
		LongerEdgeProm<MapPoint> solverLongerEdgeProm = new LongerEdgeProm<>();

		mapSolversList = new ArrayList<>();
		mapSolversList.add(solverLongerEdge);
		mapSolversList.add(solverLongerEdgeProm);
		
		map = new JMapViewer();
		map.setBounds(new Rectangle(0, 21, 793, 527));
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Cluster Files *.clus", "clus");
		fileChooser.setFileFilter(filter);
		
		
		
		new DefaultMapController(map){
			MapPoint toEdit = null;
			
			@Override
			public void mouseMoved(MouseEvent e){
				if(editPoints){
					Coordinate coord = map.getPosition(e.getX(), e.getY());
					
					MapPoint point = new MapPoint(coord.getLat(), coord.getLon());
					
					toEdit = Cluster.getClosestToList(clusters, point, CLOSEST_LIMIT);
					selectPoint(toEdit);
				}
			}

		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if(e.getButton() == MouseEvent.BUTTON1){
		    		if(editPoints && toEdit != null){
		    			
		    			EditClusterPoints editor = new EditClusterPoints(clusters, toEdit);
		    			editor.setVisible(true);
		    			
		    		}
		    				
		        }
		    }
		};
		
		this.add(map);
		
		initMenu();
		
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 793, 21);
		add(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmImportar = new JMenuItem("Importar");
		mnArchivo.add(mntmImportar);
		
		mntmImportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(null);
				file = fileChooser.getSelectedFile();
				importClusters();
				
				fileChooser.setSelectedFile(null);
				
			}
		});
		
		JMenuItem mntmExportar = new JMenuItem("Exportar");
		mnArchivo.add(mntmExportar);
		
		mntmExportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.showSaveDialog(null);
				file = fileChooser.getSelectedFile();
				if (fileChooser.getFileFilter().getDescription().equals("Cluster Files *.clus") && file != null)
					file = new File(file.getAbsoluteFile() + ".clus");
					
				exportClusters();
				fileChooser.setSelectedFile(null);
				
			}
		});
		
		JMenu mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		JMenuItem mntmConfiguracion = new JMenuItem("Configuracion");
		mnOpciones.add(mntmConfiguracion);
		mntmConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					if(isListOk()){
						clusterConfig = new ClusterConfig(mapSolversList, pointList);
						clusterConfig.setVisible(true);
						changeClustersConfig();
					}
				}
			
			});
		
		JMenuItem mntmEditarColor = new JMenuItem("Editar Color");
		mntmEditarColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(isListOk() && isSolverOk()  && isClustersOk()){
					
					editColor = new EditColor(clusters);
					editColor.setVisible(true);
					plotPoints();
				}
				
			}
		});
		mnOpciones.add(mntmEditarColor);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				actualizeData();
				generateClsuters();
				changeColorsRandomly();
				plotPoints();
				
			}
		});
		menuBar.add(btnActualizar);
		
		final JToggleButton btnEditarClusters = new JToggleButton("Editar Clusters");
		btnEditarClusters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				editPoints = btnEditarClusters.isSelected();
				plotPoints();
				
			}
		});
		menuBar.add(btnEditarClusters);
	}
	
	public void exportClusters(){
		
		try {
			Cluster.saveListToFile(clusters, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void importClusters(){
		if(file != null){
			try {
				clusters = Cluster.loadListFromFile(exportator, file);
			}
			catch (IOException e) {
				JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
			}
		
			catch (ClassCastException | ParseException e){
				JOptionPane.showMessageDialog(this, "El archivo no tiene el formato requerido", "Error", JOptionPane.ERROR_MESSAGE);
			}
	
			changeColorsRandomly();
			plotPoints();
			centerMap(12);
		}
	}
	
	public void selectPoint(MapPoint toSelect){

		plotPoints();
		
		if(toSelect != null){
		
			MapMarker marker = new MapMarkerDot(toSelect.getLat(), toSelect.getLon());
			marker.getStyle().setColor(Color.MAGENTA);
			marker.getStyle().setBackColor(Color.MAGENTA);
			
			map.addMapMarker(marker);
		}
		
	}
	
	public void actualizePoints(List<MapPoint> list){
		pointList = list;
	}
	
	private void actualizeData(){
		
		if(isListOk() && isSolverOk()){
			hasActualized = true;
			selectedSolver.actualizeData(pointList);
			
		}
	}
	
	private void changeClustersConfig() {
		if(isListOk()){
			cantClusters = clusterConfig.cantClusters;
			selectedSolver = mapSolversList.get( clusterConfig.getSelectedSolverIndex());
			generateClsuters();
			changeColorsRandomly();
			plotPoints();
			centerMap(map.getZoom());
		}
	}

	private void plotPoints() {
		
		map.removeAllMapMarkers();
		
		if(clusters != null) for (Cluster<MapPoint> cluster : clusters) {
			
			for(MapPoint point: cluster){
				
				MapMarker marker = new MapMarkerDot(point.getLat(), point.getLon());
				
				Color color = cluster.getColor();
				
				marker.getStyle().setBackColor(color);
				map.addMapMarker(marker);
				
			}
		}
		
	}
	
	private void centerMap(int zoom){
		Coordinate center = getPromMapData();
		
		map.setDisplayPositionByLatLon(center.getLat(), center.getLon(), zoom);
	}
	
	private Coordinate getPromMapData(){
		//Este metodo no deberia estar aca, deberia estar en MapData
		//Pero queda mucho mas comodo que quede aqui.
		
		if(pointList.size() == 0) return map.getPosition();
		
		return MapPoint.listToMedianCoordinate(pointList);
		
	}
	
	private boolean isSolverOk(){
		
		if(selectedSolver == null){
			JOptionPane.showMessageDialog(null,
				 	"No se configuro el algoritmo a usar.",
				 	"Error",
				 	JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
		
		return true;
		
	}
	
	private boolean isListOk(){
		
		if(pointList.size() <= 0){
			JOptionPane.showMessageDialog(null,
				 	"No hay puntos en el mapa. Hay que agregarlos desde la pestaña mapa.\n Tal vez sea necesario actualizar.",
				 	"Info",
				 	JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private boolean isClustersOk(){
		if(clusters == null){
			JOptionPane.showMessageDialog(null,
				 	"No se generaron los clusters.",
				 	"Error",
				 	JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void generateClsuters(){
		if(isSolverOk()){
			if(!hasActualized) actualizeData();
			try{
				clusters = selectedSolver.solveMap(cantClusters, exportator);
				
			}catch(IllegalArgumentException e){
				actualizeData();
			}
		}
	}
	
	private void changeColorsRandomly(){
		if(clusters != null)
			for (Cluster<MapPoint> cluster : clusters) {
				
				Color randomColor = genRandomColor();
	
				cluster.setColor(randomColor);
			}
	}
	
	private Color genRandomColor(){
		
		return new Color(gen.nextInt(127)*2, gen.nextInt(127)*2, gen.nextInt(127)*2);
		//Se utilizan valores de 0 a 127, y luego se multiplica por dos para que 
		//los valores esten mas espaciados entre si, y no aparezcan colores muy
		//"juntos" o "parecidos" segun ojo humano
		
	}
}
