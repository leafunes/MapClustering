package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;

import map.Cluster;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import graph.Distanciable;

import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditColor extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> boxClusters;
	private JComboBox<String> boxColors;
	final Color[] colorRefs = {Color.RED, Color.YELLOW, Color.BLUE, Color.green, Color.cyan, Color.black, Color.orange, Color.pink, Color.white};
	final String[] colorStrings = {"Rojo", "Amarillo", "Azul", "Verde", "Cian ", "Negro", "Naranja", "Rosa", "Blanco"};
	
	public <E extends Distanciable<E>> EditColor (final List< Cluster<E>>clusters, Component parent){
		
		getContentPane().setLayout(null);
		this.setSize( new Dimension(185, 270) );
		
		this.setLocationRelativeTo(parent);
		
		
		
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		initComboBox(clusters);
		initGui();
		
		getContentPane().add(boxClusters);
		getContentPane().add(boxColors);
		
		
		
		
	}

	private void initGui() {
		JLabel clusterLbl = new JLabel("Cluster:");
		clusterLbl.setBounds(10, 39, 122, 14);
		getContentPane().add(clusterLbl);
		
		JLabel colorLbl = new JLabel("Color:");
		colorLbl.setBounds(10, 111, 46, 14);
		getContentPane().add(colorLbl);
		
		JButton aceptarBtn = new JButton("Aceptar");
		aceptarBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				dispose();
				
			}
		});
		aceptarBtn.setBounds(41, 207, 91, 23);
		getContentPane().add(aceptarBtn);
	}
	
	private <E extends Distanciable<E>> void initComboBox(final List< Cluster<E>>clusters){
		
		String[] items = generateItems(clusters);
		
		boxClusters = new JComboBox<>();
		boxClusters.setBounds(10, 64, 156, 22);

		boxColors = new JComboBox<>();
		boxColors.setBounds(10, 136, 156, 22);
		

		boxClusters.setModel(new DefaultComboBoxModel<String>(items));
		boxColors.setModel(new DefaultComboBoxModel<String>(colorStrings));
		
		
		boxColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				actualizeColor(clusters);
				
			}
		});
		
		boxClusters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				actualizeColor(clusters);
				
			}
		});
		
	}

	private <E extends Distanciable<E>> String[] generateItems(final List<Cluster<E>> clusters) {
		String [] items= new String  [clusters.size()];
		
		for (int i = 0; i < clusters.size(); i++) {
			items[i] = "Cluster n° " + (i+1);
		}
		return items;
	}
	
	private <E extends Distanciable<E>> void actualizeColor(List <Cluster<E>> list){
		
		int selectedCluster = boxClusters.getSelectedIndex();
		int selectedColor = boxColors.getSelectedIndex();
		
		list.get(selectedCluster).setColor(colorRefs[selectedColor]);
		
	}
}
