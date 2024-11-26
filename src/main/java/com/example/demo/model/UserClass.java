package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usertable")
public class UserClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	@Column(unique = true)
	private String username;
	private String userpassword;
	@OneToMany(mappedBy = "uid", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<DiaryClass> diaries;
	public UserClass(int uid, String username, String userpassword, List<DiaryClass> diaries) {
		super();
		this.uid = uid;
		this.username = username;
		this.userpassword = userpassword;
		this.diaries = diaries;
	}
	public UserClass() {
		super();
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public List<DiaryClass> getDiaries() {
		return diaries;
	}
	public void setDiaries(List<DiaryClass> diaries) {
		this.diaries = diaries;
	}
	@Override
	public String toString() {
		return "UserClass [uid=" + uid + ", username=" + username + ", userpassword=" + userpassword + "]";
	}



}
