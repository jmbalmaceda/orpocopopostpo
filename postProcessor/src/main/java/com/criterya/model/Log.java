package com.criterya.model;

import java.io.File;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="log")
public class Log {
	@Id
	@Column(name="idlog")
	private Integer idLog;

	private Date fecha;
	private String texto;
	@Column(name="last_pickup_id")
	private Integer ultimoBlobIdLogAnterior;
	@Column(name="video_rgb")
	private String videoRgb;
	private String video_depth;
	@Transient
	private File videoFile;

	public String getUbicacionVideo(){
		String ubicacion = null;
		if(videoRgb!=null){
			if (videoFile==null)
				videoFile = new File(videoRgb);
			if (videoFile!=null)
				ubicacion = videoFile.getParent();
		}
		return ubicacion;
	}
	
	public String getNombreVideo(){
		String nombreVideo = null;
		if(videoRgb!=null){
			if (videoFile==null)
				videoFile = new File(videoRgb);
			if (videoFile!=null)
				nombreVideo = videoFile.getName();
		}
		return nombreVideo;
	}

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

	public Integer getUltimoBlobIdLogAnterior() {
		return ultimoBlobIdLogAnterior;
	}

	public void setUltimoBlobIdLogAnterior(Integer ultimoBlobIdLogAnterior) {
		this.ultimoBlobIdLogAnterior = ultimoBlobIdLogAnterior;
	}
}
