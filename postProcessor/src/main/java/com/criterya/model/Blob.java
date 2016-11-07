package com.criterya.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pickup")
public class Blob {
	@Id
	private Integer id;
	
	private Integer frame;
	
	private Date current_time;
	private Integer count_blobs;
	
	@Column(name="blob_id")
	private Integer idPerson;
	private Integer blob_x;
	private Integer blob_y;
	private Integer blob_depth;
	
	@Column(name="blob_hand_id")
	private Integer idHand;
	private Integer blob_hand_x;
	private Integer blob_hand_y;
	private Integer blob_hand_depth;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFrame() {
		return frame;
	}
	public void setFrame(Integer frame) {
		this.frame = frame;
	}
	public Date getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(Date current_time) {
		this.current_time = current_time;
	}
	public Integer getCount_blobs() {
		return count_blobs;
	}
	public void setCount_blobs(Integer count_blobs) {
		this.count_blobs = count_blobs;
	}
	public Integer getBlob_x() {
		return blob_x;
	}
	public void setBlob_x(Integer blob_x) {
		this.blob_x = blob_x;
	}
	public Integer getBlob_y() {
		return blob_y;
	}
	public void setBlob_y(Integer blob_y) {
		this.blob_y = blob_y;
	}
	public Integer getBlob_depth() {
		return blob_depth;
	}
	public void setBlob_depth(Integer blob_depth) {
		this.blob_depth = blob_depth;
	}
	public Integer getBlob_hand_x() {
		return blob_hand_x;
	}
	public void setBlob_hand_x(Integer blob_hand_x) {
		this.blob_hand_x = blob_hand_x;
	}
	public Integer getBlob_hand_y() {
		return blob_hand_y;
	}
	public void setBlob_hand_y(Integer blob_hand_y) {
		this.blob_hand_y = blob_hand_y;
	}
	public Integer getBlob_hand_depth() {
		return blob_hand_depth;
	}
	public void setBlob_hand_depth(Integer blob_hand_depth) {
		this.blob_hand_depth = blob_hand_depth;
	}
	public Integer getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}
	public Integer getIdHand() {
		return idHand;
	}
	public void setIdHand(Integer idHand) {
		this.idHand = idHand;
	}
}
