package com.criterya.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.criterya.PostProcessorApplication;
import com.criterya.PostProcessorCommons;
import com.criterya.daos.AccionRepository;
import com.criterya.daos.InteraccionRepository;
import com.criterya.daos.RecorridoRepository;
import com.criterya.model.Accion;
import com.criterya.model.Interaccion;
import com.criterya.model.Recorrido;

import java.awt.Font;
@Component
@Lazy
public class RecorridosPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9015046907028762767L;
	@Autowired
	private RecorridoRepository recorridoRepository;
	@Autowired
	private AccionRepository accionRepository;
	@Autowired
	private InteraccionRepository interaccionRepository;
	private JSpinner spinnerDesde;
	private JSpinner spinnerHasta;
	private DefaultComboBoxModel<Recorrido> modelRecorridos;
	private Recorrido recorridoSeleccionado;
	private Interaccion interaccionSeleccionada;
	private Date dateFrom;
	private Date dateTo;
	private JPanel videoPanel;
	private JList<Interaccion> listInteracciones;
	private JTable accionesTable;
	private DefaultTableModel accionesTableModel;
	private volatile boolean editandoAcciones = false;
	private JSpinner entradaHora;
	private JSpinner entradaFrame;
	private JComboBox<String> entradaSentido;
	private JComboBox<String> salidaSentido;
	private JSpinner salidaHora;
	private JSpinner salidaFrame;
	private JSpinner entradaX;
	private JSpinner entradaY;
	private JSpinner edadSpinner;
	private JComboBox<String> sexoComboBox;
	private JSpinner interaccionFrameInicio;
	private int indexRecorridoSeleccionado = -1;
	private JList<Recorrido> listRecorridos;
	private JSpinner interaccionHora;
	private JSpinner interaccionFrameFin;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("unchecked")
	public RecorridosPanel() {
		setLayout(new BorderLayout(2, 2));

		modelRecorridos = new DefaultComboBoxModel<>();

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		videoPanel = new JPanel();
		centerPanel.add(videoPanel, BorderLayout.CENTER);

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
						.addContainerGap(234, Short.MAX_VALUE))
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
								.addComponent(btnFiltrarRegistros))
								.addGap(10))
				);
		topPanel.setLayout(gl_topPanel);
		accionesTableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] {"Acci\u00F3n", "Realizada?"}
				) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, Boolean.class
			};
			@SuppressWarnings({ "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		accionesTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (!editandoAcciones && interaccionSeleccionada!=null){
					int row = e.getFirstRow();
					int column = e.getColumn();
					if (column==1){ // Sólo se puede editar la segunda columna
						String accion = (String) accionesTable.getValueAt(row, 0); 
						Boolean value = (Boolean) accionesTable.getValueAt(row, 1);
						if (value){
							interaccionRepository.agregarAccion(interaccionSeleccionada, accion);
						}else{
							interaccionRepository.quitarAccion(interaccionSeleccionada, accion);
						}
					}
				}
			}
		});

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.3);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_1.add(splitPane_1, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		splitPane_1.setLeftComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_4.add(scrollPane);
		listRecorridos = new JList<>(modelRecorridos);
		listRecorridos.setBorder(new TitledBorder(null, "Recorridos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listRecorridos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					indexRecorridoSeleccionado = listRecorridos.getSelectedIndex();
					recorridoSeleccionado = (Recorrido) listRecorridos.getSelectedValue();
					if (recorridoSeleccionado!=null){
						showRecorridoInfo();
					}
					interaccionSeleccionada = null;
					showInteraccionInfo();
				}
			}
		});
		listRecorridos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listRecorridos);

		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5, BorderLayout.EAST);

		JButton btnNewButton = new JButton("");
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/add.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Agregar nuevo recorrido
				Recorrido newRecorrido = new Recorrido();
				newRecorrido.setAltura(0);
				newRecorrido.setDuracion(0);
				newRecorrido.setEdad(40);
				newRecorrido.setSexo(PostProcessorCommons.MUJER);
				newRecorrido.setFrameEntrada(0);
				newRecorrido.setFrameSalida(0);
				if (recorridoSeleccionado!=null){
					newRecorrido.setVideo(recorridoSeleccionado.getVideo());
					newRecorrido.setHorarioEntrada(recorridoSeleccionado.getHorarioEntrada());
					newRecorrido.setHorarioSalida(recorridoSeleccionado.getHorarioSalida());
					newRecorrido.setX(recorridoSeleccionado.getX());
					newRecorrido.setY(recorridoSeleccionado.getY());
				}else{
					Calendar cal = Calendar.getInstance();
					newRecorrido.setHorarioEntrada(cal.getTime());
					newRecorrido.setHorarioSalida(cal.getTime());
					newRecorrido.setX(0);
					newRecorrido.setY(0);
				}
				recorridoSeleccionado = newRecorrido;
				showRecorridoInfo();
			}
		});

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Eliminar recorrido
				if (recorridoSeleccionado!=null){
					recorridoRepository.delete(recorridoSeleccionado);
					if (modelRecorridos.getSize()>1){
						if (indexRecorridoSeleccionado>0){
							indexRecorridoSeleccionado--;
						}
					}else
						indexRecorridoSeleccionado = -1;
					loadRecorridos(dateFrom, dateTo);
				}
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/delete.png")));

		JLabel lblEntrada = new JLabel("Entrada:");
		lblEntrada.setFont(new Font("Tahoma", Font.BOLD, 11));

		entradaHora = new JSpinner();
		SpinnerDateModel entradaHoraModel = new SpinnerDateModel(new Date(1481425200000L), null, null, Calendar.DAY_OF_YEAR);
		entradaHora.setModel(entradaHoraModel);
		SimpleDateFormat entradaHoraFormat = ((JSpinner.DateEditor) entradaHora.getEditor()).getFormat();
		entradaHoraFormat.applyPattern("dd/MM/yy HH:mm:ss");

		entradaFrame = new JSpinner();
		entradaFrame.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));

		entradaSentido = new JComboBox<String>();
		entradaSentido.setModel(new DefaultComboBoxModel<String>(new String[] {PostProcessorCommons.IZQUIERDA, PostProcessorCommons.DERECHA}));

		JLabel lblSalida = new JLabel("Salida:");
		lblSalida.setFont(new Font("Tahoma", Font.BOLD, 11));

		salidaHora = new JSpinner();
		salidaHora.setModel(new SpinnerDateModel(new Date(1481425200000L), null, null, Calendar.DAY_OF_YEAR));
		SimpleDateFormat salidaHoraFormat = ((JSpinner.DateEditor) salidaHora.getEditor()).getFormat();
		salidaHoraFormat.applyPattern("dd/MM/yy HH:mm:ss");

		salidaFrame = new JSpinner();

		salidaSentido = new JComboBox<String>();
		salidaSentido.setModel(new DefaultComboBoxModel<String>(new String[] {PostProcessorCommons.IZQUIERDA, PostProcessorCommons.DERECHA}));

		JButton btnGuardarRecorrido = new JButton("");
		btnGuardarRecorrido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (recorridoSeleccionado!=null){
					recorridoSeleccionado.setEdad(((Number)edadSpinner.getValue()).intValue());
					recorridoSeleccionado.setSexo(sexoComboBox.getSelectedItem().toString());
					recorridoSeleccionado.setHorarioEntrada((Date) entradaHora.getValue());
					recorridoSeleccionado.setFrameEntrada(((Number)entradaFrame.getValue()).intValue());
					recorridoSeleccionado.setSentidoEntrada(entradaSentido.getSelectedItem().toString());
					recorridoSeleccionado.setX(((Number)entradaX.getValue()).intValue());
					recorridoSeleccionado.setY(((Number)entradaY.getValue()).intValue());
					recorridoSeleccionado.setHorarioSalida((Date) salidaHora.getValue());
					recorridoSeleccionado.setFrameSalida(((Number)salidaFrame.getValue()).intValue());
					recorridoSeleccionado.setSentidoSalida(salidaSentido.getSelectedItem().toString());
					recorridoSeleccionado = recorridoRepository.save(recorridoSeleccionado);
					loadRecorridos(dateFrom, dateTo);
				}
			}
		});
		btnGuardarRecorrido.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/save.png")));

		JLabel lblX = new JLabel("X:");

		entradaX = new JSpinner();

		JLabel lblY = new JLabel("Y:");

		entradaY = new JSpinner();

		JLabel lblEdad = new JLabel("Edad:");

		edadSpinner = new JSpinner();

		sexoComboBox = new JComboBox<>();
		sexoComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {PostProcessorCommons.HOMBRE, PostProcessorCommons.MUJER}));

		JLabel lblFrame = new JLabel("Frame:");

		JLabel lblFrame_1 = new JLabel("Frame:");

		JButton btnEntrada = new JButton("Entrada");
		btnEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoPanel videoPanelBean = PostProcessorApplication.getContext().getBean(VideoPanel.class);
				entradaX.setValue(videoPanelBean.getxClicked());
				entradaY.setValue(videoPanelBean.getyClicked());
				entradaFrame.setValue(new Double(videoPanelBean.getFrameNum()).intValue());
			}
		});
		btnEntrada.setToolTipText("Copiar frame y (X,Y) como datos de entrada.");

		JButton btnSali = new JButton("Salida");
		btnSali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoPanel videoPanelBean = PostProcessorApplication.getContext().getBean(VideoPanel.class);
				salidaFrame.setValue(new Double(videoPanelBean.getFrameNum()).intValue());
			}
		});
		btnSali.setToolTipText("Copia frame como dato de salida.");
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
				gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_5.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_5.createSequentialGroup()
										.addComponent(btnGuardarRecorrido, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
										.addComponent(entradaHora, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
										.addComponent(lblEntrada, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
										.addComponent(entradaSentido, 0, 138, Short.MAX_VALUE)
										.addComponent(lblSalida, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
										.addComponent(salidaHora, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
										.addComponent(salidaSentido, 0, 138, Short.MAX_VALUE)
										.addGroup(gl_panel_5.createSequentialGroup()
												.addComponent(lblEdad)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(edadSpinner, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(sexoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel_5.createSequentialGroup()
														.addComponent(lblFrame)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(entradaFrame, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
														.addGroup(gl_panel_5.createSequentialGroup()
																.addComponent(lblFrame_1)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(salidaFrame, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
																.addGroup(gl_panel_5.createSequentialGroup()
																		.addComponent(lblX)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
																				.addGroup(gl_panel_5.createSequentialGroup()
																						.addComponent(entradaX, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
																						.addGap(1)
																						.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(entradaY, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
																						.addGroup(gl_panel_5.createSequentialGroup()
																								.addGap(10)
																								.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
																										.addComponent(btnEntrada, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																										.addComponent(btnSali, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
																										.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)))))
																										.addGap(0))
				);
		gl_panel_5.setVerticalGroup(
				gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_1)
								.addComponent(btnNewButton)
								.addComponent(btnGuardarRecorrido, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addGap(12)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblEdad)
										.addComponent(edadSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(sexoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblEntrada)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(entradaHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
												.addComponent(entradaFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFrame))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(entradaSentido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblX)
														.addComponent(entradaX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblY)
														.addComponent(entradaY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(btnEntrada)
														.addGap(3)
														.addComponent(btnSali)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblSalida)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(salidaHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
																.addComponent(salidaFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(lblFrame_1))
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(salidaSentido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel_5.setLayout(gl_panel_5);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);

		JPanel panel_3 = new JPanel();
		splitPane_2.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6, BorderLayout.EAST);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Nueva Interacción
				if (recorridoSeleccionado!=null){
					VideoPanel videoPanelBean = PostProcessorApplication.getContext().getBean(VideoPanel.class);
					interaccionSeleccionada = new Interaccion();
					interaccionSeleccionada.setAcciones(new ArrayList<Accion>());
					interaccionSeleccionada.setFrameInicio(new Double(videoPanelBean.getFrameNum()).intValue());
					interaccionSeleccionada.setFrameFin(interaccionSeleccionada.getFrameInicio());
					interaccionSeleccionada.setHorario(recorridoSeleccionado.getHorarioEntrada());
					interaccionSeleccionada.setX(videoPanelBean.getxClicked());
					interaccionSeleccionada.setY(videoPanelBean.getyClicked());
					interaccionSeleccionada.setZ(0);
					recorridoSeleccionado.getInteracciones().add(interaccionSeleccionada);
					showInteraccionInfo();
				}
			}
		});
		button.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/add.png")));
		button.setVerticalAlignment(SwingConstants.TOP);

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Eliminar Interacción
				if (recorridoSeleccionado!=null && interaccionSeleccionada!=null){
					recorridoSeleccionado.getInteracciones().remove(listInteracciones.getSelectedIndex());
					recorridoSeleccionado = recorridoRepository.save(recorridoSeleccionado);
					showRecorridoInfo();
				}
			}
		});
		button_1.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/delete.png")));

		interaccionHora = new JSpinner();
		interaccionHora.setModel(new SpinnerDateModel(new Date(1481425200000L), null, null, Calendar.DAY_OF_YEAR));
		SimpleDateFormat interaccionHoraFormat = ((JSpinner.DateEditor) interaccionHora.getEditor()).getFormat();
		interaccionHoraFormat.applyPattern("dd/MM/yy HH:mm:ss");

		interaccionFrameFin = new JSpinner();

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (recorridoSeleccionado!=null && interaccionSeleccionada!=null){
					//TODO: Guardar Interaccion
					interaccionSeleccionada.setHorario((Date) interaccionHora.getValue());
					interaccionSeleccionada.setFrameFin(((Number)interaccionFrameFin.getValue()).intValue());
					interaccionSeleccionada.setFrameInicio(((Number)interaccionFrameInicio.getValue()).intValue());
					recorridoSeleccionado = recorridoRepository.save(recorridoSeleccionado);
					showRecorridoInfo();
				}
			}
		});
		button_2.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/save.png")));

		interaccionFrameInicio = new JSpinner();

		JLabel lblInicio = new JLabel("Inicio:");

		JLabel lblFin = new JLabel("Fin:");
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
				gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_6.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(button, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
										.addComponent(interaccionHora, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
										.addGroup(gl_panel_6.createSequentialGroup()
												.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
														.addComponent(lblFin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblInicio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
																.addComponent(interaccionFrameFin, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
																.addComponent(interaccionFrameInicio, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))))
																.addContainerGap())
				);
		gl_panel_6.setVerticalGroup(
				gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
						.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(interaccionHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
										.addComponent(interaccionFrameInicio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblInicio))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
												.addComponent(interaccionFrameFin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFin))
												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		panel_6.setLayout(gl_panel_6);

		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_7.add(scrollPane_1);

		listInteracciones = new JList<>();
		listInteracciones.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					interaccionSeleccionada = listInteracciones.getSelectedValue();
					if (interaccionSeleccionada!=null){
						showInteraccionInfo();
					}
				}
			}
		});
		listInteracciones.setBorder(new TitledBorder(null, "Interacciones del recorrido seleccionado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setViewportView(listInteracciones);

		JPanel panel_8 = new JPanel();
		splitPane_2.setRightComponent(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.WEST);
		panel_9.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_9.add(scrollPane_2);

		accionesTable = new JTable();
		accionesTable.setModel(accionesTableModel);
		scrollPane_2.setViewportView(accionesTable);

		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.EAST);

		JButton addActionBtn = new JButton("");
		addActionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//int result = JOptionPane.showConfirmDialog(RecorridosPanel.this, "Nombre de la acción", "Nueva acción", JOptionPane.OK_CANCEL_OPTION);
				String newActionName = JOptionPane.showInputDialog("Nombre de la acción");
				if (newActionName!=null){
					Accion newAction = new Accion();
					newAction.setNombre(newActionName);
					newAction.setDescripcion(newActionName);
					accionRepository.save(newAction);
					showInteraccionInfo();
				}

			}
		});
		addActionBtn.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/add.png")));
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(
				gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
						.addContainerGap()
						.addComponent(addActionBtn)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		gl_panel_10.setVerticalGroup(
				gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
						.addComponent(addActionBtn)
						.addContainerGap(55, Short.MAX_VALUE))
				);
		panel_10.setLayout(gl_panel_10);
		initDataBindings();
	}

	@PostConstruct
	private void init(){
		System.out.println("Init");
		// Cargo todas las acciones posibles
		List<Accion> acciones = accionRepository.findAll();
		for (Accion accion : acciones) {
			accionesTableModel.addRow(new Object[]{accion.getNombre(), Boolean.FALSE});
		}
	}

	public void loadRecorridos(Date dateFrom, Date dateTo) {
		int oldIndexRecorridoSeleccionado = indexRecorridoSeleccionado;	// Guardo temporalmente el índice del recorrido seleccionado.
		modelRecorridos.removeAllElements();
		indexRecorridoSeleccionado = oldIndexRecorridoSeleccionado;		// Recupero el índice
		List<Recorrido> recorridos = recorridoRepository.getRecorridos(dateFrom, dateTo);
		for (Recorrido recorrido : recorridos) {
			modelRecorridos.addElement(recorrido);
		}
		this.revalidate();
		if (indexRecorridoSeleccionado!=-1 && !recorridos.isEmpty())
			listRecorridos.setSelectedIndex(indexRecorridoSeleccionado);
	}

	private void showRecorridoInfo() {
		// Info del recorrido
		//TODO: Info recorrido
		edadSpinner.setValue(recorridoSeleccionado.getEdad());
		sexoComboBox.setSelectedItem(recorridoSeleccionado.getSexo());
		entradaHora.setValue(recorridoSeleccionado.getHorarioEntrada());
		entradaFrame.setValue(recorridoSeleccionado.getFrameEntrada());
		entradaSentido.setSelectedItem(recorridoSeleccionado.getSentidoEntrada());
		entradaX.setValue(recorridoSeleccionado.getX());
		entradaY.setValue(recorridoSeleccionado.getY());
		salidaHora.setValue(recorridoSeleccionado.getHorarioSalida());
		salidaFrame.setValue(recorridoSeleccionado.getFrameSalida());
		salidaSentido.setSelectedItem(recorridoSeleccionado.getSentidoSalida());

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
		videoPanelBean.setVideoFile(recorridoSeleccionado.getVideo().getUbicacion()+File.separator+recorridoSeleccionado.getVideo().getNombre());
		videoPanelBean.setXY(recorridoSeleccionado.getX(), recorridoSeleccionado.getY());
		videoPanelBean.setFrameNum(recorridoSeleccionado.getFrameEntrada());
		videoPanelBean.setLastFrameToPlay(recorridoSeleccionado.getFrameSalida());
		videoPanel.add(videoPanelBean);
		videoPanel.revalidate();
		videoPanel.repaint();
	}

	private void showInteraccionInfo() {
		/* limpiar acciones */
		editandoAcciones = true;
		int rowCount = accionesTableModel.getRowCount();
		for (int i=0; i<rowCount; i++){
			accionesTableModel.setValueAt(Boolean.FALSE, i, 1);
		}

		/* Info Interacción */
		if (interaccionSeleccionada!=null){
			interaccionHora.setValue(interaccionSeleccionada.getHorario());
			interaccionFrameInicio.setValue(interaccionSeleccionada.getFrameInicio());
			interaccionFrameFin.setValue(interaccionSeleccionada.getFrameFin());

			VideoPanel videoPanelBean = PostProcessorApplication.getContext().getBean(VideoPanel.class);
			videoPanelBean.setXY(interaccionSeleccionada.getX(), interaccionSeleccionada.getX());
			videoPanelBean.setFrameNum(interaccionSeleccionada.getFrameInicio());
			videoPanelBean.setLastFrameToPlay(interaccionSeleccionada.getFrameFin());
			// Actualizar las acciones realizadas en la interacción con la góndola
			interaccionSeleccionada = interaccionRepository.loadAcciones(interaccionSeleccionada);
			List<Accion> acciones = interaccionSeleccionada.getAcciones();

			for (int i=0; i<rowCount; i++){
				String nombreAccion = (String) accionesTableModel.getValueAt(i, 0);
				for (Accion accion: acciones){
					if (accion.getNombre().equals(nombreAccion)){
						accionesTableModel.setValueAt(Boolean.TRUE, i, 1);
						break;
					}
				}
			}
		}
		editandoAcciones = false;

	}

	protected void initDataBindings() {
	}
}
