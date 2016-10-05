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

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import graph.Graphable;
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

public class ClusterPanel  extends JPanel{
	
	private JMapViewer map;
	private EditColor editColor;
	private ClusterConfig clusterConfig;
	private boolean hasActualized;

	LongerEdge<MapPoint> solverLongerEdge;
	LongerEdgeProm<MapPoint> solverLongerEdgeProm;
	List <MapPoint> pointList;
	List<MapSolver<MapPoint>> mapSolversList;
	
	List<Cluster<MapPoint>> clusters;
	
	MapSolver<MapPoint> selectedSolver;
	
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
					System.out.println(clusters.size());
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
				
			}
		});
		menuBar.add(btnActualizar);
		
		JToggleButton btnEditarClusters = new JToggleButton("Editar Clusters");
		menuBar.add(btnEditarClusters);
		
		JToggleButton tglbtnMostrarCamino = new JToggleButton("Mostrar Camino");
		menuBar.add(tglbtnMostrarCamino);
	}
	
	public void actualizePoints(List<MapPoint> list){
		pointList = list;
	}
	
	private void actualizeData(){
		

		if(isListOk() && isSolverOk()){
			hasActualized = true;
			selectedSolver.actualizeData(pointList);
			generateClsuters();
			plotPoints();
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
		if(!hasActualized) actualizeData();
		clusters = selectedSolver.solveMap(cantClusters);
		System.out.println(selectedSolver.NAME);
	}
	
}
