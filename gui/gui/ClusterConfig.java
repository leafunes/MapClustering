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

import graph.Distanciable;
import map.MapData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ClusterConfig extends JDialog{
	
	int cantClusters;
	private JComboBox<String> comboBox;
	private JSlider slider;
	private JCheckBox chckbxUsarLmite;
	private JLabel percentLbl;
	private JSpinner spinner;
	private JButton btnAceptar;

	<E extends Distanciable<E>> ClusterConfig(List<MapSolver<E>> list, List<E> pointList){
		getContentPane().setLayout(null);
		this.setBounds(0, 0, 250, 300);
		
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		initComboBox(list);
		initClusterCant(pointList);
		initButton(pointList);
		initLabels();
		
		getContentPane().add(btnAceptar);
		getContentPane().add(percentLbl);	
		getContentPane().add(comboBox);
		getContentPane().add(chckbxUsarLmite);
		getContentPane().add(spinner);
		getContentPane().add(slider);
	}

	public int getSelectedSolverIndex(){
		return comboBox.getSelectedIndex();
	}
	
	private void initLabels() {
		JLabel lblCantidadClusters = new JLabel("Cantidad clusters");
		lblCantidadClusters.setBounds(26, 203, 129, 14);
		getContentPane().add(lblCantidadClusters);
		
		JLabel lblLmite = new JLabel("Limite");
		lblLmite.setBounds(107, 118, 46, 14);
		getContentPane().add(lblLmite);
		
		JLabel lblSeleccioneAlgoritmo = new JLabel("Seleccione Algoritmo");
		lblSeleccioneAlgoritmo.setBounds(26, 19, 177, 14);
		getContentPane().add(lblSeleccioneAlgoritmo);
		
		
		
		JLabel lblPorcentajeDeClusters = new JLabel("Porcentaje de clusters");
		lblPorcentajeDeClusters.setBounds(36, 157, 183, 15);
		getContentPane().add(lblPorcentajeDeClusters);
	}

	private <E extends Distanciable<E>> void initButton(final List<E> points) {
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxUsarLmite.isSelected()){
					int percent = (int)slider.getValue();
					cantClusters = (int)((percent * points.size()) /100);
					
				}
				else
					cantClusters = (int)spinner.getValue();
				dispose();
				
			}
		});
		btnAceptar.setBounds(86, 239, 91, 23);
	}

	private <E extends Distanciable<E>> void initClusterCant(List<E> pointList) {
		chckbxUsarLmite = new JCheckBox("Usar limite");
		chckbxUsarLmite.setBounds(26, 91, 129, 23);
		
		slider = new JSlider();
		slider.setEnabled(false);
		slider.setBounds(26, 134, 200, 24);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, pointList.size(), 1));
		spinner.setBounds(166, 200, 53, 22);
		
		percentLbl = new JLabel("50%");
		percentLbl.setBounds(107, 170, 46, 15);
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				percentLbl.setText(slider.getValue() + "%");
			}
		});
		
		chckbxUsarLmite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxUsarLmite.isSelected()){
					slider.setEnabled(true);
					spinner.setEnabled(false);	
				}
				else{
					slider.setEnabled(false);
					spinner.setEnabled(true);	
				}
			}
		});
	}

	private <E extends Distanciable<E>> void initComboBox(final List<MapSolver<E>> list) {
		comboBox = new JComboBox<>();
		comboBox.setBounds(26, 44, 193, 22);

		String[] items= new String[list.size()];
		for (int i = 0; i < list.size(); i++) items[i]= list.get(i).NAME;
		
		comboBox.setModel(new DefaultComboBoxModel<String>(items));
	}
}
