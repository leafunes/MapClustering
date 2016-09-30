package gui;

import java.util.List;

import javax.swing.JDialog;

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

import map.MapData;

public class ClusterConfig extends JDialog{

	ClusterConfig(List<MapSolver<?>> list, MapData mapData){
		getContentPane().setLayout(null);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(26, 44, 193, 22);
		getContentPane().add(comboBox);
		
		String[] items= new String[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			
		
			items[i]= list.get(i).NAME;
		}
		comboBox.setModel(new DefaultComboBoxModel<String>(items));
		
		JCheckBox chckbxUsarLmite = new JCheckBox("Usar limite");
		chckbxUsarLmite.setBounds(26, 73, 97, 23);
		getContentPane().add(chckbxUsarLmite);
		
		JSlider slider = new JSlider();
		slider.setBounds(26, 134, 200, 24);
		getContentPane().add(slider);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, mapData.getPointsCant(), 1));
		spinner.setBounds(136, 169, 41, 22);
		getContentPane().add(spinner);
		
		JLabel lblCantidadClusters = new JLabel("Cantidad clusters");
		lblCantidadClusters.setBounds(26, 173, 97, 14);
		getContentPane().add(lblCantidadClusters);
		
		JLabel lblLmite = new JLabel("Limite");
		lblLmite.setBounds(109, 109, 46, 14);
		getContentPane().add(lblLmite);
		
		JLabel lblSeleccioneAlgoritmo = new JLabel("Seleccione Algoritmo");
		lblSeleccioneAlgoritmo.setBounds(26, 19, 129, 14);
		getContentPane().add(lblSeleccioneAlgoritmo);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(86, 239, 91, 23);
		getContentPane().add(btnAceptar);
		
	}
}
