package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "diaryTable")
public class DiaryClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int did;
	
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss",shape = JsonFormat.Shape.STRING)
	@Column(nullable = false)
	private String ddate;
	
	
	@Column(nullable = false)
	private String data;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
	@JsonIgnore
	private UserClass uid;


	public DiaryClass(int did, String ddate, String data, UserClass uid) {
		super();
		this.did = did;
		this.ddate = ddate;
		this.data = data;
		this.uid = uid;
	}

	public DiaryClass() {
		super();
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getDdate() {
		return ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public UserClass getUid() {
		return uid;
	}

	public void setUid(UserClass uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "DairyClass [did=" + did + ", ddate=" + ddate  + ", data=" + data + "]";
	}
	
	
}
