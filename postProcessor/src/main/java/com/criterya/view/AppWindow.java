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

@Component
public class AppWindow {

	private JFrame frame;
	@Autowired
	private LogsPanel logsPanel;
	@Autowired
	private RecorridosPanel recorridosPanel;

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
				frame.getContentPane().removeAll();
				frame.getContentPane().add(logsPanel, BorderLayout.CENTER);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
		mnLogs.add(mntmCargarLogs);
		
		JMenu mnRecorridos = new JMenu("Recorridos");
		menuBar.add(mnRecorridos);
		
		JMenuItem mntmCargarRecorridos = new JMenuItem("Cargar recorridos");
		mntmCargarRecorridos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(recorridosPanel, BorderLayout.CENTER);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
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
	}

}
