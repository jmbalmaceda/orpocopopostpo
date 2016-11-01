package com.criterya.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="log")
public class Log {
	@Id
	@Column(name="idlog")
	private Integer idLog;
	
	private Date fecha;
	private String texto;
	
	private Integer last_pickup_id;
	@Column(name="video_rgb")
	private String videoRgb;
	private String video_depth;
	public Integer getIdLog() {
		return idLog;
	}
	public void setIdLog(Integer id) {
		this.idLog = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Integer getLast_pickup_id() {
		return last_pickup_id;
	}
	public void setLast_pickup_id(Integer last_pickup_id) {
		this.last_pickup_id = last_pickup_id;
	}
	public String getVideoRgb() {
		return videoRgb;
	}
	public void setVideoRgb(String video_rgb) {
		this.videoRgb = video_rgb;
	}
	public String getVideo_depth() {
		return video_depth;
	}
	public void setVideo_depth(String video_depth) {
		this.video_depth = video_depth;
	}
}
