package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;

public class ClusterPanel  extends JPanel{
	
	private JMapViewer map;
	private EditColor editColor;
	private ClusterConfig clusterConfig;
	private boolean hasActualized;
	private boolean editPoints;

	LongerEdge<MapPoint> solverLongerEdge;
	LongerEdgeProm<MapPoint> solverLongerEdgeProm;
	List <MapPoint> pointList;
	List<MapSolver<MapPoint>> mapSolversList;
	
	List<Cluster<MapPoint>> clusters;
	
	MapSolver<MapPoint> selectedSolver;
	
	private final double CLOSEST_LIMIT = 5E-2;
	
	MapPoint.Exportator exportator = new MapPoint.Exportator();
	
	int cantClusters;
	
	ClusterPanel(Component parent){
		
		setLayout(null);
		super.setBounds(parent.getBounds());
		
		solverLongerEdge = new LongerEdge<>();
		solverLongerEdgeProm = new LongerEdgeProm<>();
		map = new JMapViewer();
		
		mapSolversList = new ArrayList<>();
		mapSolversList.add(solverLongerEdge);
		mapSolversList.add(solverLongerEdgeProm);
		
		map.setBounds(new Rectangle(0, 21, 793, 527));
		
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
		
		JMenuItem mntmExportar = new JMenuItem("Exportar");
		mnArchivo.add(mntmExportar);
		
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
		
		JToggleButton tglbtnMostrarCamino = new JToggleButton("Mostrar Camino");
		menuBar.add(tglbtnMostrarCamino);
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
		}
	}

	private void plotPoints() {
		
		map.removeAllMapMarkers();
		
		for (Cluster<MapPoint> cluster : clusters) {
			for(MapPoint point: cluster){
				
				MapMarker marker = new MapMarkerDot(point.getLat(), point.getLon());
				
				Color color = cluster.getColor();
				
				marker.getStyle().setBackColor(color);
				map.addMapMarker(marker);
				
			}
		}
		
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
			plotPoints();
		}
	}
}
