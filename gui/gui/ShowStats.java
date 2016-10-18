package gui;

import javax.swing.JDialog;

import graph.Distanciable;
import mapSolvers.MapSolver;

import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ShowStats  extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public <E extends Distanciable<E>> ShowStats(MapSolver<E> selectedSolver, Component parent) {
		setTitle("Estadisticas");
		getContentPane().setLayout(null);
		
		this.setSize( new Dimension(240, 260) );	
		this.setLocationRelativeTo(parent);
		
		JLabel lblCantDeClusters = new JLabel("Cant. de clusters");
		JLabel lblPromDeTamao = new JLabel("Tama\u00F1o prom.");
		JLabel lblTamaoMax = new JLabel("Tama\u00F1o Max");
		JLabel lblTamaoMin = new JLabel("Tama\u00F1o Min.");
		JLabel lblDesviacionEstandar = new JLabel("Desviacion estandar");
		
		lblCantDeClusters.setBounds(30, 34, 101, 14);
		lblPromDeTamao.setBounds(30, 53, 134, 14);
		lblTamaoMax.setBounds(30, 72, 76, 14);	
		lblTamaoMin.setBounds(30, 91, 76, 14);
		lblDesviacionEstandar.setBounds(30, 110, 134, 14);
		
		getContentPane().add(lblCantDeClusters);
		getContentPane().add(lblPromDeTamao);
		getContentPane().add(lblTamaoMax);
		getContentPane().add(lblTamaoMin);
		getContentPane().add(lblDesviacionEstandar);
		
		
		double median = selectedSolver.getClusterProm();
		JLabel cantClus = new JLabel(Integer.toString( selectedSolver.getClustersCant() ));
		JLabel tamProm = new JLabel(Double.toString( selectedSolver.getClusterProm() ));
		JLabel tamMax = new JLabel(Integer.toString( selectedSolver.getClusterMax() ));
		JLabel tamMin = new JLabel(Integer.toString( selectedSolver.getClusterMin() ));	
		JLabel desvEst = new JLabel(Double.toString( selectedSolver.getClusterDesv(median) ));
		
		cantClus.setBounds(159, 34, 55, 14);
		tamProm.setBounds(159, 53, 55, 14);
		tamMax.setBounds(159, 72, 55, 14);
		tamMin.setBounds(159, 91, 55, 14);
		desvEst.setBounds(159, 110, 55, 14);
		
		getContentPane().add(tamProm);
		getContentPane().add(tamMax);
		getContentPane().add(tamMin);
		getContentPane().add(cantClus);
		getContentPane().add(desvEst);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(67, 164, 87, 23);
		getContentPane().add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		
	}
}
