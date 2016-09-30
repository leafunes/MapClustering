package gui;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

import map.Cluster;
import map.MapPoint;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class ClusterPanel  extends JPanel{
	
	private JMapViewer map;
	
	ClusterPanel(Component parent){
		setLayout(null);
		
		initMenu();
		map = new JMapViewer();

		map.setBounds(new Rectangle(0, 21, 793, 527));
		this.add(map);
		
		super.setBounds(parent.getBounds());
		
		
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
		
		JMenuItem mntmEditarColor = new JMenuItem("Editar Color");
		mnOpciones.add(mntmEditarColor);
		
		JButton btnActualizar = new JButton("Actualizar");
		menuBar.add(btnActualizar);
		
		JButton btnEditarClusters = new JButton("Editar Clusters");
		menuBar.add(btnEditarClusters);
		
		JToggleButton tglbtnMostrarCamino = new JToggleButton("Mostrar Camino");
		menuBar.add(tglbtnMostrarCamino);
	}
	
}
