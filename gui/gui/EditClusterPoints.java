package gui;

import java.awt.Component;
import java.awt.Label;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

public class EditClusterPoints extends JDialog{
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public EditClusterPoints(){
		getContentPane().setLayout(null);
		
		JLabel lblElPuntoSeleccionado = new JLabel("El punto seleccionado");
		JLabel lblPerteneceA = new JLabel("pertenece al cluster n°");
		lblPerteneceA.setBounds(39, 31, 168, 30);
		lblElPuntoSeleccionado.setBounds(39, 12, 168, 30);
		getContentPane().add(lblElPuntoSeleccionado);
		getContentPane().add(lblPerteneceA);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(49, 73, 141, 24);
		getContentPane().add(comboBox);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Destino");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(8, 155, 114, 23);
		getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnAsignarComo = new JRadioButton("Origen\n");
		buttonGroup.add(rdbtnAsignarComo);
		rdbtnAsignarComo.setBounds(8, 182, 114, 23);
		getContentPane().add(rdbtnAsignarComo);
		
		JLabel lblAsignarComo = new JLabel("Asignar como...");
		lblAsignarComo.setBounds(12, 125, 110, 15);
		getContentPane().add(lblAsignarComo);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(60, 288, 117, 25);
		getContentPane().add(btnOk);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(60, 251, 117, 25);
		getContentPane().add(btnCancelar);
		
		
	}
}
