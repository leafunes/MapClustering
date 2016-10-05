package gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import map.MapPoint;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GetCoord extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private double lat;
	private double lon;

	/**
	 * Create the dialog.
	 */
	public GetCoord() {
		
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		setTitle("Ingrese las coordenadas");
		setBounds(100, 100, 360, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblLongitud = new JLabel("Longitud: ");
		lblLongitud.setBounds(47, 69, 80, 15);
		contentPanel.add(lblLongitud);
		
		JLabel lblLatitud = new JLabel("Latitud:");
		lblLatitud.setBounds(47, 27, 80, 15);
		contentPanel.add(lblLatitud);
		
		final JFormattedTextField inLon = new JFormattedTextField();
		inLon.setText("-123456789.0");
		inLon.setBounds(129, 67, 187, 19);
		contentPanel.add(inLon);
		
		final JFormattedTextField inLat = new JFormattedTextField();
		inLat.setText("-123456789.0");
		inLat.setBounds(129, 25, 187, 19);
		contentPanel.add(inLat);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {

							lat = Double.parseDouble(inLat.getText());
							lon = Double.parseDouble(inLon.getText());
							dispose();
						} catch (ClassCastException | NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Los datos ingresados no son validos", "Error", JOptionPane.ERROR_MESSAGE);
							
						}
						
					}
				});
				okButton.setActionCommand("OK");		
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	

	public MapPoint getPoint(){
		return new MapPoint(lat, lon);
	}
	
}
