package com.criterya.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.criterya.daos.RecorridoRepository;
import com.criterya.model.Recorrido;
@Component
@Scope("prototype")
public class RecorridoPanel extends JPanel {

	@Autowired
	private RecorridoRepository recorridoRepository;
	private BindingGroup m_bindingGroup;
	private com.criterya.model.Recorrido recorrido = new com.criterya.model.Recorrido();
	private JTextField alturaJTextField;
	private JTextField duracionJTextField;
	private JTextField edadJTextField;
	private JTextField frameEntradaJTextField;
	private JTextField frameSalidaJTextField;
	private JLabel idJLabel;
	private JLabel idPersonJLabel;
	private JTextField sentidoEntradaJTextField;
	private JTextField sentidoSalidaJTextField;
	private JTextField sexoJTextField;
	private JPanel panel;
	private JButton btnGuardar;
	private JButton btnCancelar;

	public RecorridoPanel(com.criterya.model.Recorrido newRecorrido) {
		this();
		setRecorrido(newRecorrido);
	}

	public RecorridoPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4 };
		setLayout(gridBagLayout);

		JLabel alturaLabel = new JLabel("Altura:");
		GridBagConstraints labelGbc_0 = new GridBagConstraints();
		labelGbc_0.insets = new Insets(5, 5, 5, 5);
		labelGbc_0.gridx = 0;
		labelGbc_0.gridy = 0;
		add(alturaLabel, labelGbc_0);

		alturaJTextField = new JTextField();
		GridBagConstraints componentGbc_0 = new GridBagConstraints();
		componentGbc_0.insets = new Insets(5, 0, 5, 0);
		componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_0.gridx = 1;
		componentGbc_0.gridy = 0;
		add(alturaJTextField, componentGbc_0);

		JLabel duracionLabel = new JLabel("Duracion:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 1;
		add(duracionLabel, labelGbc_1);

		duracionJTextField = new JTextField();
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 0);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 1;
		add(duracionJTextField, componentGbc_1);

		JLabel edadLabel = new JLabel("Edad:");
		GridBagConstraints labelGbc_2 = new GridBagConstraints();
		labelGbc_2.insets = new Insets(5, 5, 5, 5);
		labelGbc_2.gridx = 0;
		labelGbc_2.gridy = 2;
		add(edadLabel, labelGbc_2);

		edadJTextField = new JTextField();
		GridBagConstraints componentGbc_2 = new GridBagConstraints();
		componentGbc_2.insets = new Insets(5, 0, 5, 0);
		componentGbc_2.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_2.gridx = 1;
		componentGbc_2.gridy = 2;
		add(edadJTextField, componentGbc_2);

		JLabel frameEntradaLabel = new JLabel("FrameEntrada:");
		GridBagConstraints labelGbc_3 = new GridBagConstraints();
		labelGbc_3.insets = new Insets(5, 5, 5, 5);
		labelGbc_3.gridx = 0;
		labelGbc_3.gridy = 3;
		add(frameEntradaLabel, labelGbc_3);

		frameEntradaJTextField = new JTextField();
		GridBagConstraints componentGbc_3 = new GridBagConstraints();
		componentGbc_3.insets = new Insets(5, 0, 5, 0);
		componentGbc_3.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_3.gridx = 1;
		componentGbc_3.gridy = 3;
		add(frameEntradaJTextField, componentGbc_3);

		JLabel frameSalidaLabel = new JLabel("FrameSalida:");
		GridBagConstraints labelGbc_4 = new GridBagConstraints();
		labelGbc_4.insets = new Insets(5, 5, 5, 5);
		labelGbc_4.gridx = 0;
		labelGbc_4.gridy = 4;
		add(frameSalidaLabel, labelGbc_4);

		frameSalidaJTextField = new JTextField();
		GridBagConstraints componentGbc_4 = new GridBagConstraints();
		componentGbc_4.insets = new Insets(5, 0, 5, 0);
		componentGbc_4.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_4.gridx = 1;
		componentGbc_4.gridy = 4;
		add(frameSalidaJTextField, componentGbc_4);

		JLabel idLabel = new JLabel("Id:");
		GridBagConstraints labelGbc_5 = new GridBagConstraints();
		labelGbc_5.insets = new Insets(5, 5, 5, 5);
		labelGbc_5.gridx = 0;
		labelGbc_5.gridy = 5;
		add(idLabel, labelGbc_5);

		idJLabel = new JLabel();
		GridBagConstraints componentGbc_5 = new GridBagConstraints();
		componentGbc_5.insets = new Insets(5, 0, 5, 0);
		componentGbc_5.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_5.gridx = 1;
		componentGbc_5.gridy = 5;
		add(idJLabel, componentGbc_5);

		JLabel idPersonLabel = new JLabel("IdPerson:");
		GridBagConstraints labelGbc_6 = new GridBagConstraints();
		labelGbc_6.insets = new Insets(5, 5, 5, 5);
		labelGbc_6.gridx = 0;
		labelGbc_6.gridy = 6;
		add(idPersonLabel, labelGbc_6);

		idPersonJLabel = new JLabel();
		GridBagConstraints componentGbc_6 = new GridBagConstraints();
		componentGbc_6.insets = new Insets(5, 0, 5, 0);
		componentGbc_6.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_6.gridx = 1;
		componentGbc_6.gridy = 6;
		add(idPersonJLabel, componentGbc_6);

		JLabel sentidoEntradaLabel = new JLabel("SentidoEntrada:");
		GridBagConstraints labelGbc_7 = new GridBagConstraints();
		labelGbc_7.insets = new Insets(5, 5, 5, 5);
		labelGbc_7.gridx = 0;
		labelGbc_7.gridy = 7;
		add(sentidoEntradaLabel, labelGbc_7);

		sentidoEntradaJTextField = new JTextField();
		GridBagConstraints componentGbc_7 = new GridBagConstraints();
		componentGbc_7.insets = new Insets(5, 0, 5, 0);
		componentGbc_7.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_7.gridx = 1;
		componentGbc_7.gridy = 7;
		add(sentidoEntradaJTextField, componentGbc_7);

		JLabel sentidoSalidaLabel = new JLabel("SentidoSalida:");
		GridBagConstraints labelGbc_8 = new GridBagConstraints();
		labelGbc_8.insets = new Insets(5, 5, 5, 5);
		labelGbc_8.gridx = 0;
		labelGbc_8.gridy = 8;
		add(sentidoSalidaLabel, labelGbc_8);

		sentidoSalidaJTextField = new JTextField();
		GridBagConstraints componentGbc_8 = new GridBagConstraints();
		componentGbc_8.insets = new Insets(5, 0, 5, 0);
		componentGbc_8.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_8.gridx = 1;
		componentGbc_8.gridy = 8;
		add(sentidoSalidaJTextField, componentGbc_8);

		JLabel sexoLabel = new JLabel("Sexo:");
		GridBagConstraints labelGbc_9 = new GridBagConstraints();
		labelGbc_9.insets = new Insets(5, 5, 5, 5);
		labelGbc_9.gridx = 0;
		labelGbc_9.gridy = 9;
		add(sexoLabel, labelGbc_9);

		sexoJTextField = new JTextField();
		GridBagConstraints componentGbc_9 = new GridBagConstraints();
		componentGbc_9.insets = new Insets(5, 0, 5, 0);
		componentGbc_9.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_9.gridx = 1;
		componentGbc_9.gridy = 9;
		add(sexoJTextField, componentGbc_9);
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 10;
		add(panel, gbc_panel);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recorrido = recorridoRepository.save(recorrido);
				setRecorrido(recorrido);
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(195, Short.MAX_VALUE)
					.addComponent(btnCancelar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnGuardar)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGuardar)
						.addComponent(btnCancelar))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

		if (recorrido != null) {
			m_bindingGroup = initDataBindings();
		}
	}

	public com.criterya.model.Recorrido getRecorrido() {
		return recorrido;
	}

	public void setRecorrido(com.criterya.model.Recorrido newRecorrido) {
		setRecorrido(newRecorrido, true);
	}

	public void setRecorrido(com.criterya.model.Recorrido newRecorrido,
			boolean update) {
		recorrido = newRecorrido;
		if (update) {
			if (m_bindingGroup != null) {
				m_bindingGroup.unbind();
				m_bindingGroup = null;
			}
			if (recorrido != null) {
				m_bindingGroup = initDataBindings();
			}
		}
	}
	protected BindingGroup initDataBindings() {
		BeanProperty<Recorrido, Integer> alturaProperty = BeanProperty.create("altura");
		BeanProperty<JTextField, String> textProperty = BeanProperty.create("text");
		AutoBinding<Recorrido, Integer, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, recorrido, alturaProperty, alturaJTextField, textProperty);
		autoBinding.bind();
		//
		BeanProperty<Recorrido, Integer> duracionProperty = BeanProperty.create("duracion");
		BeanProperty<JTextField, String> textProperty_1 = BeanProperty.create("text");
		AutoBinding<Recorrido, Integer, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, recorrido, duracionProperty, duracionJTextField, textProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<Recorrido, Integer> edadProperty = BeanProperty.create("edad");
		BeanProperty<JTextField, String> textProperty_2 = BeanProperty.create("text");
		AutoBinding<Recorrido, Integer, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, recorrido, edadProperty, edadJTextField, textProperty_2);
		autoBinding_2.bind();
		//
		BeanProperty<Recorrido, Integer> frameEntradaProperty = BeanProperty.create("frameEntrada");
		BeanProperty<JTextField, String> textProperty_3 = BeanProperty.create("text");
		AutoBinding<Recorrido, Integer, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, recorrido, frameEntradaProperty, frameEntradaJTextField, textProperty_3);
		autoBinding_3.bind();
		//
		BeanProperty<Recorrido, Integer> frameSalidaProperty = BeanProperty.create("frameSalida");
		BeanProperty<JTextField, String> textProperty_4 = BeanProperty.create("text");
		AutoBinding<Recorrido, Integer, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ, recorrido, frameSalidaProperty, frameSalidaJTextField, textProperty_4);
		autoBinding_4.bind();
		//
		BeanProperty<Recorrido, Integer> idProperty = BeanProperty.create("id");
		BeanProperty<JLabel, String> textProperty_5 = BeanProperty.create("text");
		AutoBinding<Recorrido, Integer, JLabel, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, recorrido, idProperty, idJLabel, textProperty_5);
		autoBinding_5.bind();
		//
		BeanProperty<Recorrido, Integer> idPersonProperty = BeanProperty.create("idPerson");
		BeanProperty<JLabel, String> textProperty_6 = BeanProperty.create("text");
		AutoBinding<Recorrido, Integer, JLabel, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ, recorrido, idPersonProperty, idPersonJLabel, textProperty_6);
		autoBinding_6.bind();
		//
		BeanProperty<Recorrido, String> sentidoEntradaProperty = BeanProperty.create("sentidoEntrada");
		BeanProperty<JTextField, String> textProperty_7 = BeanProperty.create("text");
		AutoBinding<Recorrido, String, JTextField, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ, recorrido, sentidoEntradaProperty, sentidoEntradaJTextField, textProperty_7);
		autoBinding_7.bind();
		//
		BeanProperty<Recorrido, String> sentidoSalidaProperty = BeanProperty.create("sentidoSalida");
		BeanProperty<JTextField, String> textProperty_8 = BeanProperty.create("text");
		AutoBinding<Recorrido, String, JTextField, String> autoBinding_8 = Bindings.createAutoBinding(UpdateStrategy.READ, recorrido, sentidoSalidaProperty, sentidoSalidaJTextField, textProperty_8);
		autoBinding_8.bind();
		//
		BeanProperty<Recorrido, String> sexoProperty = BeanProperty.create("sexo");
		BeanProperty<JTextField, String> textProperty_9 = BeanProperty.create("text");
		AutoBinding<Recorrido, String, JTextField, String> autoBinding_9 = Bindings.createAutoBinding(UpdateStrategy.READ, recorrido, sexoProperty, sexoJTextField, textProperty_9);
		autoBinding_9.bind();
		//
		BindingGroup bindingGroup = new BindingGroup();
		//
		bindingGroup.addBinding(autoBinding);
		bindingGroup.addBinding(autoBinding_1);
		bindingGroup.addBinding(autoBinding_2);
		bindingGroup.addBinding(autoBinding_3);
		bindingGroup.addBinding(autoBinding_4);
		bindingGroup.addBinding(autoBinding_5);
		bindingGroup.addBinding(autoBinding_6);
		bindingGroup.addBinding(autoBinding_7);
		bindingGroup.addBinding(autoBinding_8);
		bindingGroup.addBinding(autoBinding_9);
		return bindingGroup;
	}
}
