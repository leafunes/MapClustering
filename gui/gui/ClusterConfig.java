package gui;

import java.util.List;

import javax.swing.JDialog;
import java.awt.Dialog;
import java.awt.event.MouseListener;

import mapSolvers.MapSolver;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

import graph.Graphable;
import map.MapData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClusterConfig extends JDialog{
	
	int cantClusters;

	<E extends Graphable<E>> ClusterConfig(List<MapSolver<E>> list, List<E> pointList){
		getContentPane().setLayout(null);
		this.setBounds(0, 0, 250, 300);
		
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(26, 44, 193, 22);
		getContentPane().add(comboBox);
		
		String[] items= new String[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			
		
			items[i]= list.get(i).NAME;
		}
		comboBox.setModel(new DefaultComboBoxModel<String>(items));
		
		JCheckBox chckbxUsarLmite = new JCheckBox("Usar limite");
		chckbxUsarLmite.setBounds(26, 91, 129, 23);
		getContentPane().add(chckbxUsarLmite);
		
		JSlider slider = new JSlider();
		slider.setBounds(26, 134, 200, 24);
		getContentPane().add(slider);
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, pointList.size(), 1));
		spinner.setBounds(166, 170, 53, 22);
		getContentPane().add(spinner);
		
		JLabel lblCantidadClusters = new JLabel("Cantidad clusters");
		lblCantidadClusters.setBounds(26, 173, 129, 14);
		getContentPane().add(lblCantidadClusters);
		
		JLabel lblLmite = new JLabel("Limite");
		lblLmite.setBounds(107, 118, 46, 14);
		getContentPane().add(lblLmite);
		
		JLabel lblSeleccioneAlgoritmo = new JLabel("Seleccione Algoritmo");
		lblSeleccioneAlgoritmo.setBounds(26, 19, 177, 14);
		getContentPane().add(lblSeleccioneAlgoritmo);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cantClusters = (int)spinner.getValue();
				dispose();
				
			}
		});
		btnAceptar.setBounds(86, 239, 91, 23);
		getContentPane().add(btnAceptar);
		
	}
}
