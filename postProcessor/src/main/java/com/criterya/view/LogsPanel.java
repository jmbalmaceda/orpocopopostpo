package com.criterya.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.criterya.PostProcessorCommons;
import com.criterya.daos.LogRepository;
import com.criterya.model.Log;

@Component
public class LogsPanel extends JPanel {
	private static final long serialVersionUID = 5870801492344409880L;

	@Autowired
	LogRepository logRepository;

	private JList<Log> list;
	private JTextField tfId;
	private JTextField tfDia;
	private JTextField tfHora;
	private JTextField tfTexto;
	private JTextField tfPrimerBlobId;
	private JTextField tfUltimoBlobId;
	private Log selectedLog;
	private JTextField tfVideoRGB;
	private JTextField tfVideoDEPTH;

	/**
	 * Create the panel.
	 */
	public LogsPanel() {
		setLayout(new BorderLayout(2, 2));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.WEST);

		list = new JList<>();
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				selectedLog = list.getSelectedValue();
				if (selectedLog!=null){
					selectedLog.setSelected(Boolean.TRUE ^ selectedLog.getSelected());
					updateLogInformation();
				}
				list.revalidate();
				list.repaint();
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new CellRenderer());
		scrollPane.setViewportView(list);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		JLabel lblId = new JLabel("id:");

		JLabel lblDa = new JLabel("Día");

		JLabel lblHora = new JLabel("Hora:");

		JLabel lblTexto = new JLabel("Texto:");

		JLabel lblPrimerBlobid = new JLabel("Primer BlobId:");

		JLabel lblltimoBlobid = new JLabel("Último blobId:");

		tfId = new JTextField();
		tfId.setColumns(10);

		tfDia = new JTextField();
		tfDia.setColumns(10);

		tfHora = new JTextField();
		tfHora.setColumns(10);

		tfTexto = new JTextField();
		tfTexto.setColumns(10);

		tfPrimerBlobId = new JTextField();
		tfPrimerBlobId.setColumns(10);

		tfUltimoBlobId = new JTextField();
		tfUltimoBlobId.setColumns(10);

		JLabel lblVideoRgb = new JLabel("Video RGB:");

		JLabel lblVideoDepth = new JLabel("Video DEPTH.");

		tfVideoRGB = new JTextField();
		tfVideoRGB.setColumns(10);

		tfVideoDEPTH = new JTextField();
		tfVideoDEPTH.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addContainerGap()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblltimoBlobid, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(lblHora, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblDa, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblId, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(lblTexto, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(lblPrimerBlobid, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																		.addComponent(tfPrimerBlobId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(tfTexto, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
																		.addComponent(tfHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
																				.addComponent(tfDia, Alignment.LEADING)
																				.addComponent(tfId, Alignment.LEADING))))
																				.addGroup(gl_panel.createSequentialGroup()
																						.addContainerGap()
																						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																								.addComponent(lblVideoRgb)
																								.addComponent(lblVideoDepth))
																								.addGap(27)
																								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																										.addComponent(tfUltimoBlobId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																										.addComponent(tfVideoRGB, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
																										.addComponent(tfVideoDEPTH, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))))
																										.addContainerGap())
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblId)
								.addComponent(tfId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblDa)
										.addComponent(tfDia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblHora)
												.addComponent(tfHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblTexto)
														.addComponent(tfTexto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
																.addComponent(lblPrimerBlobid)
																.addComponent(tfPrimerBlobId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(ComponentPlacement.RELATED)
																.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
																		.addComponent(lblltimoBlobid)
																		.addComponent(tfUltimoBlobId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
																				.addComponent(lblVideoRgb)
																				.addComponent(tfVideoRGB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
																						.addComponent(lblVideoDepth)
																						.addComponent(tfVideoDEPTH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																						.addContainerGap(199, Short.MAX_VALUE))
				);
		panel.setLayout(gl_panel);
	}

	public void loadLogs(){
		DefaultComboBoxModel<Log> listModel = new DefaultComboBoxModel<>();
		List<Log> allLogs = logRepository.findAll();
		for (Log log : allLogs) {
			listModel.addElement(log);
		}
		list.setModel(listModel);
	}

	private void updateLogInformation() {
		try{
			this.tfId.setText(selectedLog.getIdLog().toString());
			this.tfDia.setText(PostProcessorCommons.fechaFormatter.format(selectedLog.getFecha()));
			this.tfHora.setText(PostProcessorCommons.horaFormatter.format(selectedLog.getFecha()));
			this.tfTexto.setText(selectedLog.getTexto());
			this.tfPrimerBlobId.setText(logRepository.getPrimerBlobId(selectedLog).toString());
			this.tfUltimoBlobId.setText(logRepository.getUltimoBlobId(selectedLog).toString());
			this.tfVideoRGB.setText(selectedLog.getVideoRgb());
			this.tfVideoDEPTH.setText(selectedLog.getVideo_depth());
		} catch(Exception e){
			e.printStackTrace();
		}

	}

	class CellRenderer implements ListCellRenderer<Log>{

		@Override
		public java.awt.Component getListCellRendererComponent(
				JList<? extends Log> list, Log value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JCheckBox checkbox = new JCheckBox();
			checkbox.setSelected(value.getSelected());
			checkbox.setText(value.toString());
			checkbox.setBackground(isSelected ?
					getSelectionBackground() : getBackground());
			checkbox.setForeground(isSelected ?
					getSelectionForeground() : getForeground());
			checkbox.setEnabled(isEnabled());
			checkbox.setFont(getFont());
			checkbox.setFocusPainted(false);
			checkbox.setBorderPainted(true);
			return checkbox;
		}

		private Color getSelectionForeground() {
			return Color.black;
		}

		private Color getSelectionBackground() {
			return Color.blue;
		}

	}
}
