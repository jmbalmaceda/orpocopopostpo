package com.criterya.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
import javax.swing.JTable;
import javax.swing.JTextField;
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
import com.criterya.daos.AccionRepository;
import com.criterya.daos.InteraccionRepository;
import com.criterya.daos.RecorridoRepository;
import com.criterya.model.Accion;
import com.criterya.model.Interaccion;
import com.criterya.model.Recorrido;
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
	private JTextField entradaSentido;
	private JTextField salidaSentido;
	private JSpinner salidaHora;
	private JSpinner salidaFrame;
	private JSpinner entradaX;
	private JSpinner entradaY;

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
			@SuppressWarnings({ "rawtypes", "unchecked" })
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
																		final JList<Recorrido> listRecorridos = new JList<>(modelRecorridos);
																		listRecorridos.setBorder(new TitledBorder(null, "Recorridos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
																		listRecorridos.addListSelectionListener(new ListSelectionListener() {
																			public void valueChanged(ListSelectionEvent e) {
																				if (!e.getValueIsAdjusting()){
																					recorridoSeleccionado = (Recorrido) listRecorridos.getSelectedValue();
																					if (recorridoSeleccionado!=null){
																						// Info del recorrido
																						//TODO: Info recorrido
																						entradaHora.setValue(recorridoSeleccionado.getHorarioEntrada());
																						entradaFrame.setValue(recorridoSeleccionado.getFrameEntrada());
																						entradaSentido.setText(recorridoSeleccionado.getSentidoEntrada());
																						entradaX.setValue(recorridoSeleccionado.getX());
																						entradaY.setValue(recorridoSeleccionado.getY());
																						salidaHora.setValue(recorridoSeleccionado.getHorarioSalida());
																						salidaFrame.setValue(recorridoSeleccionado.getFrameSalida());
																						salidaSentido.setText(recorridoSeleccionado.getSentidoSalida());

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
																
																JLabel lblEntrada = new JLabel("Entrada:");
																
																entradaHora = new JSpinner();
																entradaHora.setModel(new SpinnerDateModel(new Date(1481425200000L), null, null, Calendar.DAY_OF_YEAR));
																
																entradaFrame = new JSpinner();
																entradaFrame.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
																
																entradaSentido = new JTextField();
																
																JLabel lblSalida = new JLabel("Salida:");
																
																salidaHora = new JSpinner();
																salidaHora.setModel(new SpinnerDateModel(new Date(1481425200000L), null, null, Calendar.DAY_OF_YEAR));
																
																salidaFrame = new JSpinner();
																
																salidaSentido = new JTextField();
																salidaSentido.setColumns(10);
																
																JButton btnGuardarRecorrido = new JButton("Guardar");
																btnGuardarRecorrido.addActionListener(new ActionListener() {
																	public void actionPerformed(ActionEvent e) {
																		if (recorridoSeleccionado!=null){
																			recorridoSeleccionado.setHorarioEntrada((Date) entradaHora.getValue());
																			recorridoSeleccionado.setFrameEntrada(((Number)entradaFrame.getValue()).intValue());
																			recorridoSeleccionado.setSentidoEntrada(entradaSentido.getText());
																			recorridoSeleccionado.setX(((Number)entradaX.getValue()).intValue());
																			recorridoSeleccionado.setY(((Number)entradaY.getValue()).intValue());
																			recorridoSeleccionado.setHorarioSalida((Date) salidaHora.getValue());
																			recorridoSeleccionado.setFrameSalida(((Number)salidaFrame.getValue()).intValue());
																			recorridoSeleccionado.setSentidoSalida(salidaSentido.getText());
																			recorridoRepository.save(recorridoSeleccionado);
																		}
																	}
																});
																btnGuardarRecorrido.setHorizontalAlignment(SwingConstants.LEFT);
																btnGuardarRecorrido.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/save.png")));
																
																JLabel lblX = new JLabel("X:");
																
																entradaX = new JSpinner();
																
																JLabel lblY = new JLabel("Y:");
																
																entradaY = new JSpinner();
																GroupLayout gl_panel_5 = new GroupLayout(panel_5);
																gl_panel_5.setHorizontalGroup(
																	gl_panel_5.createParallelGroup(Alignment.TRAILING)
																		.addGroup(gl_panel_5.createSequentialGroup()
																			.addContainerGap()
																			.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
																				.addComponent(entradaHora, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addGroup(gl_panel_5.createSequentialGroup()
																					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
																					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
																					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
																				.addComponent(lblEntrada, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(entradaFrame, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(entradaSentido, 0, 107, Short.MAX_VALUE)
																				.addComponent(lblSalida, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(salidaHora, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(salidaFrame, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(salidaSentido, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(btnGuardarRecorrido, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addGroup(gl_panel_5.createSequentialGroup()
																					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
																						.addComponent(lblY, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(lblX, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																					.addPreferredGap(ComponentPlacement.RELATED)
																					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
																						.addComponent(entradaY, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
																						.addComponent(entradaX, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))))
																			.addContainerGap())
																);
																gl_panel_5.setVerticalGroup(
																	gl_panel_5.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_panel_5.createSequentialGroup()
																			.addContainerGap()
																			.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
																				.addComponent(btnNewButton)
																				.addComponent(btnNewButton_1))
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(lblEntrada)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(entradaHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(entradaFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(entradaSentido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
																				.addComponent(lblX)
																				.addComponent(entradaX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
																				.addComponent(lblY)
																				.addComponent(entradaY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																			.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
																			.addComponent(lblSalida)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(salidaHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(salidaFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(salidaSentido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(btnGuardarRecorrido)
																			.addContainerGap())
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
																button.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/add.png")));
																button.setVerticalAlignment(SwingConstants.TOP);
																
																JButton button_1 = new JButton("");
																button_1.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/delete.png")));
																
																final JSpinner interaccionHora = new JSpinner();
																interaccionHora.setModel(new SpinnerDateModel(new Date(1481425200000L), null, null, Calendar.DAY_OF_YEAR));
																
																final JSpinner interaccionFrame = new JSpinner();
																
																JButton btnGuardarInteraccion = new JButton("Guardar");
																btnGuardarInteraccion.setHorizontalAlignment(SwingConstants.LEFT);
																btnGuardarInteraccion.setIcon(new ImageIcon(RecorridosPanel.class.getResource("/icons/save.png")));
																GroupLayout gl_panel_6 = new GroupLayout(panel_6);
																gl_panel_6.setHorizontalGroup(
																	gl_panel_6.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_panel_6.createSequentialGroup()
																			.addContainerGap()
																			.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
																				.addGroup(gl_panel_6.createSequentialGroup()
																					.addComponent(button, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
																					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																					.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
																				.addComponent(interaccionHora, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(interaccionFrame, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
																				.addComponent(btnGuardarInteraccion, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
																			.addContainerGap())
																);
																gl_panel_6.setVerticalGroup(
																	gl_panel_6.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_panel_6.createSequentialGroup()
																			.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
																				.addComponent(button, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
																				.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(interaccionHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(interaccionFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addComponent(btnGuardarInteraccion)
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
																								/* Info Interacción */
																								interaccionHora.setValue(interaccionSeleccionada.getHorario());
																								interaccionFrame.setValue(interaccionSeleccionada.getFrameInicio());
																								
																								VideoPanel videoPanelBean = PostProcessorApplication.getContext().getBean(VideoPanel.class);
																								videoPanelBean.setXY(interaccionSeleccionada.getX(), interaccionSeleccionada.getX());
																								videoPanelBean.setFrameNum(interaccionSeleccionada.getFrameInicio());
																								videoPanelBean.setLastFrameToPlay(interaccionSeleccionada.getFrameFin());
																								// Actualizar las acciones realizadas en la interacción con la góndola
																								interaccionSeleccionada = interaccionRepository.loadAcciones(interaccionSeleccionada);
																								List<Accion> acciones = interaccionSeleccionada.getAcciones();
																								int rowCount = accionesTableModel.getRowCount();
																								editandoAcciones = true;
																								for (int i=0; i<rowCount; i++){
																									String nombreAccion = (String) accionesTableModel.getValueAt(i, 0);
																									boolean accionRealizada = false;
																									for (Accion accion: acciones){
																										if (accion.getNombre().equals(nombreAccion)){
																											accionesTableModel.setValueAt(Boolean.TRUE, i, 1);
																											accionRealizada = true;
																											break;
																										}
																									}
																									if (!accionRealizada)
																										accionesTableModel.setValueAt(Boolean.FALSE, i, 1);
																								}
																								editandoAcciones = false;
																								//accionesTableModel.fireTableDataChanged();
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
																				
																						JScrollPane scrollPane_2 = new JScrollPane();
																						panel_9.add(scrollPane_2);
																						
																								accionesTable = new JTable();
																								accionesTable.setModel(accionesTableModel);
																								scrollPane_2.setViewportView(accionesTable);
																				
																				JPanel panel_10 = new JPanel();
																				panel_8.add(panel_10, BorderLayout.EAST);
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
		modelRecorridos.removeAllElements();
		List<Recorrido> recorridos = recorridoRepository.getRecorridos(dateFrom, dateTo);
		for (Recorrido recorrido : recorridos) {
			modelRecorridos.addElement(recorrido);
		}
		this.revalidate();
	}
	protected void initDataBindings() {
	}
}
