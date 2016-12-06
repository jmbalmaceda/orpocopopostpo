package com.criterya.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="recorrido")
@NamedEntityGraph(name="path.Recorrido.interacciones", attributeNodes = @NamedAttributeNode(value="interacciones"))
public class Recorrido {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="blob_id")
	private Integer idPerson;
	private String sentidoEntrada;
	private String sentidoSalida;
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Video video;
	private Date horarioEntrada;
	private Integer frameEntrada;
	private Date horarioSalida;
	private Integer frameSalida;
	private Integer duracion;
	@OneToMany(cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Interaccion> interacciones;
	private String sexo;
	private Integer edad;
	private Integer altura;
	
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSentidoEntrada() {
		return sentidoEntrada;
	}
	public void setSentidoEntrada(String sentidoEntrada) {
		this.sentidoEntrada = sentidoEntrada;
	}
	public String getSentidoSalida() {
		return sentidoSalida;
	}
	public void setSentidoSalida(String sentidoSalida) {
		this.sentidoSalida = sentidoSalida;
	}
	public Date getHorarioEntrada() {
		return horarioEntrada;
	}
	public void setHorarioEntrada(Date horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}
	public Integer getFrameEntrada() {
		return frameEntrada;
	}
	public void setFrameEntrada(Integer frameEntrada) {
		this.frameEntrada = frameEntrada;
	}
	public Date getHorarioSalida() {
		return horarioSalida;
	}
	public void setHorarioSalida(Date horarioSalida) {
		this.horarioSalida = horarioSalida;
	}
	public Integer getFrameSalida() {
		return frameSalida;
	}
	public void setFrameSalida(Integer frameSalida) {
		this.frameSalida = frameSalida;
	}
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Integer getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}
	public List<Interaccion> getInteracciones() {
		return interacciones;
	}
	public void setInteracciones(List<Interaccion> interacciones) {
		this.interacciones = interacciones;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Integer getAltura() {
		return altura;
	}
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	@Override
	public String toString() {
		return horarioEntrada+" - "+duracion;
	}
}
