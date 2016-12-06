package com.criterya.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.criterya.PostProcessorApplication;
import com.criterya.daos.RecorridoRepository;
import com.criterya.model.Interaccion;
import com.criterya.model.Recorrido;
@Component
public class RecorridosPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9015046907028762767L;
	@Autowired
	private RecorridoRepository recorridoRepository;
	private JSpinner spinnerDesde;
	private JSpinner spinnerHasta;
	private DefaultComboBoxModel<Recorrido> modelRecorridos;
	private Recorrido recorridoSeleccionado;
	private Date dateFrom;
	private Date dateTo;
	private JPanel videoPanel;
	private JList<Interaccion> listInteracciones;

	/**
	 * Create the panel.
	 */
	public RecorridosPanel() {
		setLayout(new BorderLayout(2, 2));
		
		modelRecorridos = new DefaultComboBoxModel<>();
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		videoPanel = new JPanel();
		centerPanel.add(videoPanel, BorderLayout.CENTER);
		
		JPanel accionesRecorridoPanel = new JPanel();
		centerPanel.add(accionesRecorridoPanel, BorderLayout.WEST);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/add.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Recorrido newRecorrido = new Recorrido();
				newRecorrido.setAltura(0);
				newRecorrido.setDuracion(0);
				newRecorrido.setEdad(0);
				newRecorrido.setFrameEntrada(0);
				newRecorrido.setFrameSalida(0);
				if (recorridoSeleccionado!=null)
					newRecorrido.setVideo(recorridoSeleccionado.getVideo());
			}
		});
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (recorridoSeleccionado!=null){
					recorridoRepository.delete(recorridoSeleccionado);
					loadRecorridos(dateFrom, dateTo);
				}
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/delete.png")));
		GroupLayout gl_accionesRecorridoPanel = new GroupLayout(accionesRecorridoPanel);
		gl_accionesRecorridoPanel.setHorizontalGroup(
			gl_accionesRecorridoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_accionesRecorridoPanel.createSequentialGroup()
					.addGroup(gl_accionesRecorridoPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnNewButton_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 37, Short.MAX_VALUE)))
		);
		gl_accionesRecorridoPanel.setVerticalGroup(
			gl_accionesRecorridoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_accionesRecorridoPanel.createSequentialGroup()
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1)
					.addContainerGap(348, Short.MAX_VALUE))
		);
		accionesRecorridoPanel.setLayout(gl_accionesRecorridoPanel);
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		JLabel lblFechaDesde = new JLabel("Fecha desde:");
		
		Calendar calendarDesde = Calendar.getInstance();
		calendarDesde.set(Calendar.HOUR, 0);
		calendarDesde.set(Calendar.MINUTE, 0);
		calendarDesde.set(Calendar.SECOND, 0);
		calendarDesde.add(Calendar.DAY_OF_MONTH, -10);
		SpinnerDateModel dateModelDesde = new SpinnerDateModel();
		dateModelDesde.setValue(calendarDesde.getTime());
		spinnerDesde = new JSpinner(dateModelDesde);
		
		JLabel lblFechaHasta = new JLabel("Fecha hasta:");
		
		SpinnerDateModel dateModelHasta = new SpinnerDateModel();
		calendarDesde.set(Calendar.HOUR, 23);
		calendarDesde.set(Calendar.MINUTE, 59);
		calendarDesde.set(Calendar.SECOND, 59);
		calendarDesde.add(Calendar.DAY_OF_MONTH, 10);
		dateModelHasta.setValue(calendarDesde.getTime());
		spinnerHasta = new JSpinner(dateModelHasta);
		
		JButton btnFiltrarRegistros = new JButton("Filtrar Recorridos");
		btnFiltrarRegistros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFrom = (Date)spinnerDesde.getValue();
				dateTo = (Date)spinnerHasta.getValue();
				loadRecorridos(dateFrom, dateTo);
			}
		});
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblFechaDesde)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerDesde, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(lblFechaHasta, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerHasta, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(btnFiltrarRegistros)
					.addContainerGap(260, Short.MAX_VALUE))
		);
		gl_topPanel.setVerticalGroup(
			gl_topPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFechaDesde)
						.addComponent(spinnerDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFechaHasta)
						.addComponent(spinnerHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFiltrarRegistros)))
		);
		topPanel.setLayout(gl_topPanel);
		
		JSplitPane panel = new JSplitPane();
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(panel, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.setLeftComponent(scrollPane);
		final JList<Recorrido> listRecorridos = new JList<>(modelRecorridos);
		listRecorridos.setBorder(new TitledBorder(null, "Recorridos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listRecorridos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					recorridoSeleccionado = (Recorrido) listRecorridos.getSelectedValue();
					
					// Info del recorrido
					/*
					RecorridoPanel infoPanel = PostProcessorApplication.getContext().getBean(RecorridoPanel.class);
					infoPanel.setRecorrido(recorridoSeleccionado);
					*/
					
					// Interacciones del recorrido
					recorridoSeleccionado = recorridoRepository.loadInteracciones(recorridoSeleccionado);
					List<Interaccion> interacciones = recorridoSeleccionado.getInteracciones();
					DefaultComboBoxModel<Interaccion> model = new DefaultComboBoxModel<>();
					for (Interaccion interaccion : interacciones) {
						model.addElement(interaccion);
					}
					listInteracciones.setModel(model);
					
					// Video del recorrido
					VideoPanel videoPanelBean = PostProcessorApplication.getContext().getBean(VideoPanel.class);
					videoPanelBean.setVideoFile("D:\\Videos_SantaRosa\\VideoRGB2016-9-1___9-0.avi");
					videoPanelBean.setFrameNum(recorridoSeleccionado.getFrameEntrada());
					videoPanel.add(videoPanelBean);
					videoPanel.revalidate();
					videoPanel.repaint();
				}
			}
		});
		listRecorridos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listRecorridos);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.setRightComponent(scrollPane_1);
		
		listInteracciones = new JList<>();
		listInteracciones.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					Interaccion interaccionSeleccionada = listInteracciones.getSelectedValue();
					if (interaccionSeleccionada!=null){
						VideoPanel videoPanelBean = PostProcessorApplication.getContext().getBean(VideoPanel.class);
						videoPanelBean.setFrameNum(interaccionSeleccionada.getFrame());
					}
				}
			}
		});
		listInteracciones.setBorder(new TitledBorder(null, "Interacciones del recorrido seleccionado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setViewportView(listInteracciones);
	}

	public void loadRecorridos(Date dateFrom, Date dateTo) {
		modelRecorridos.removeAllElements();
		List<Recorrido> recorridos = recorridoRepository.getRecorridos(dateFrom, dateTo);
		for (Recorrido recorrido : recorridos) {
			modelRecorridos.addElement(recorrido);
		}
		this.revalidate();
	}
}
