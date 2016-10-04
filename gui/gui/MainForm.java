package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainForm {

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private MapPanel mapPanel;
	private ClusterPanel clusterPanel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabsInit();
		

	}
	
	private void tabsInit(){
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 798, 575);
		frame.getContentPane().add(tabbedPane);
		
		mapPanel = new MapPanel(tabbedPane);
		clusterPanel = new ClusterPanel(tabbedPane);
		tabbedPane.addTab("Mapa", null, mapPanel, null);
		tabbedPane.addTab("Clusters", null, clusterPanel, null);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {

	            if(tabbedPane.getSelectedIndex() == 1){
	            	clusterPanel.actualizePoints(mapPanel.mapData.getPoints());
	            }
				
			}
		});
		
		
	}
	
	



	



}
