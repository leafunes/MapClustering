package gui;

import graph.Distanciable;

import java.awt.Component;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	public <E extends Distanciable<E>> EditClusterPoints(final List<Cluster<E>> clusters, final E point){
		getContentPane().setLayout(null);
		
		this.setBounds(0, 0, 260, 235);
		final int currentClusterIndex = Cluster.getBelongsIndex(clusters, point);
		
		JLabel lblElPuntoSeleccionado = new JLabel("El punto seleccionado");
		JLabel lblPerteneceA = new JLabel("pertenece al cluster n°");
		lblPerteneceA.setBounds(39, 31, 168, 30);
		lblElPuntoSeleccionado.setBounds(39, 12, 168, 30);
		getContentPane().add(lblElPuntoSeleccionado);
		getContentPane().add(lblPerteneceA);
		
		String[] items = new String[clusters.size()];
		
		for(int i = 0; i< clusters.size(); i++){
			
			items [i] = "Cluster n° " + i;
			
		}
		
		final JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(items));
		comboBox.setSelectedIndex(currentClusterIndex);
		
		comboBox.setBounds(49, 73, 141, 24);
		getContentPane().add(comboBox);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(60, 168, 117, 25);
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clusters.get(comboBox.getSelectedIndex()).addPoint(point);
				clusters.get(currentClusterIndex).removePoint(point);
				dispose();
				
			}
		});
		getContentPane().add(btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(60, 129, 117, 25);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		getContentPane().add(btnCancelar);
		
		
	}
}
