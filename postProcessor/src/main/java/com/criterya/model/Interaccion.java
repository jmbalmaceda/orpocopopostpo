package com.criterya.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import com.criterya.PostProcessorCommons;

@Entity
@Table(name="interaccion")
@NamedEntityGraph(name="path.Interaccion.acciones", attributeNodes = @NamedAttributeNode(value="acciones"))
public class Interaccion {
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	private Integer id;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Accion> acciones;
	private Integer x;
	private Integer y;
	private Integer z;
	@ManyToOne
	private Zona zona;
	private Date horario;
	private Integer frameInicio;
	private Integer frameFin;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Accion> getAcciones() {
		return acciones;
	}
	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getZ() {
		return z;
	}
	public void setZ(Integer z) {
		this.z = z;
	}
	public Zona getZona() {
		return zona;
	}
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public Date getHorario() {
		return horario;
	}
	public void setHorario(Date horario) {
		this.horario = horario;
	}
	public Integer getFrameInicio() {
		return frameInicio;
	}
	public void setFrameInicio(Integer frame) {
		this.frameInicio = frame;
	}
	@Override
	public String toString() {
		return PostProcessorCommons.horaFormatter.format(horario)+ " - "+frameInicio+"-"+frameFin;
	}
	public Integer getFrameFin() {
		return frameFin;
	}
	public void setFrameFin(Integer frameFin) {
		this.frameFin = frameFin;
	}
}
