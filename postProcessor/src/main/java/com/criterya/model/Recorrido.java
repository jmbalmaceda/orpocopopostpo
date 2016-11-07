package com.criterya.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="recorrido")
public class Recorrido {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="blob_id")
	private Integer idPerson;
	private String sentidoEntrada;
	private String sentidoSalida;
	@ManyToOne
	private Video video;
	private Date horarioEntrada;
	private Integer frameEntrada;
	private Date horarioSalida;
	private Integer frameSalida;
	private Integer duracion;
	@OneToMany
	private List<Interaccion> interacciones;
	
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
}
