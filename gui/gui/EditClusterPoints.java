package gui;

import graph.Distanciable;

import java.awt.Component;
import java.awt.Label;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

import map.Cluster;
import javax.swing.DefaultComboBoxModel;

public class EditClusterPoints extends JDialog{
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public <E extends Distanciable<E>> EditClusterPoints(List<Cluster<E>> clusters, E point){
		getContentPane().setLayout(null);
		
		this.setBounds(0, 0, 260, 365);
		System.out.println(clusters.size());
		int currentClusterIndex = Cluster.getBelongsIndex(clusters, point);
		
		JLabel lblElPuntoSeleccionado = new JLabel("El punto seleccionado");
		JLabel lblPerteneceA = new JLabel("pertenece al cluster nÂ°");
		lblPerteneceA.setBounds(39, 31, 168, 30);
		lblElPuntoSeleccionado.setBounds(39, 12, 168, 30);
		getContentPane().add(lblElPuntoSeleccionado);
		getContentPane().add(lblPerteneceA);
		
		String[] items = new String[clusters.size()];
		
		for(int i = 0; i< clusters.size(); i++){
			
			items [i] = "Cluster nº " + i;
			
		}
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(items));
		comboBox.setSelectedIndex(currentClusterIndex);
		comboBox.setBounds(49, 73, 141, 24);
		getContentPane().add(comboBox);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Destino");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(63, 155, 114, 23);
		getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnAsignarComo = new JRadioButton("Origen\n");
		buttonGroup.add(rdbtnAsignarComo);
		rdbtnAsignarComo.setBounds(63, 181, 114, 23);
		getContentPane().add(rdbtnAsignarComo);
		
		JLabel lblAsignarComo = new JLabel("Asignar como...");
		lblAsignarComo.setBounds(63, 132, 110, 15);
		getContentPane().add(lblAsignarComo);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(60, 288, 117, 25);
		getContentPane().add(btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(60, 251, 117, 25);
		getContentPane().add(btnCancelar);
		
		
	}
}
