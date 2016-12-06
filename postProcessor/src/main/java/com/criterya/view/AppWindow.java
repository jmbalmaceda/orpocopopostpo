package com.criterya.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

@Component
public class AppWindow {

	private JFrame frame;
	@Autowired
	private LogsPanel logsPanel;
	@Autowired
	private RecorridosPanel recorridosPanel;
	private JLabel statusLabel;
	private JPanel centerPanel;

	public void show(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow.this.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public AppWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnLogs = new JMenu("Logs");
		menuBar.add(mnLogs);
		
		JMenuItem mntmCargarLogs = new JMenuItem("Cargar logs");
		mntmCargarLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logsPanel.loadLogs();
				centerPanel.removeAll();
				centerPanel.add(logsPanel, BorderLayout.CENTER);
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		});
		mnLogs.add(mntmCargarLogs);
		
		JMenu mnRecorridos = new JMenu("Recorridos");
		menuBar.add(mnRecorridos);
		
		JMenuItem mntmCargarRecorridos = new JMenuItem("Cargar recorridos");
		mntmCargarRecorridos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerPanel.removeAll();
				centerPanel.add(recorridosPanel, BorderLayout.CENTER);
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		});
		mnRecorridos.add(mntmCargarRecorridos);
		
		JMenu mnAplicacin = new JMenu("Aplicación");
		menuBar.add(mnAplicacin);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnAplicacin.add(mntmSalir);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new BorderLayout(0, 0));
		
		statusLabel = new JLabel();
		statusLabel.setText("Listo.");
		statusPanel.add(statusLabel);
		
		centerPanel = new JPanel();
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
	}

	public void setStatus(String status){
		statusLabel.setText(status);
	}
}
