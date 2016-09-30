package gui;

import java.awt.Color;
import java.util.List;

import javax.swing.JDialog;


import map.Cluster;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.MultipleGradientPaint.ColorSpaceType;

public class EditColor extends JDialog{
	
	EditColor(List< Cluster<?> > list){
		getContentPane().setLayout(null);
		super.setBounds(0, 0, 185, 270);
		
		
		JComboBox<String> boxClusters = new JComboBox<>();
		boxClusters.setBounds(10, 64, 156, 22);
		getContentPane().add(boxClusters);
		
		String [] items= new String  [list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			items[i] = "Cluster nº " + (i+1);
		}
		
		boxClusters.setModel(new DefaultComboBoxModel<String>(items));
		
		JComboBox boxColors = new JComboBox();
		boxColors.setModel(new DefaultComboBoxModel(new String[] {"Rojo", "Amarillo", "Azul", "Verde", "Cian ", "Magenta", "Negro", "Naranja", "Rosa", "Blanco"}));
		boxColors.setBounds(10, 136, 156, 22);
		getContentPane().add(boxColors);
		
		JLabel lblCluster = new JLabel("Cluster:");
		lblCluster.setBounds(10, 39, 46, 14);
		getContentPane().add(lblCluster);
		
		JLabel lblNewLabel = new JLabel("Color:");
		lblNewLabel.setBounds(10, 111, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setBounds(41, 207, 91, 23);
		getContentPane().add(btnNewButton);
		
		
	}
}
