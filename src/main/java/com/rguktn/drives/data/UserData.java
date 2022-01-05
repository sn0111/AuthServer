package com.rguktn.drives.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class UserData implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String username;
	private String roll;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return roll;
	}
	public void setUserid(String roll) {
		this.roll = roll;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String useremail;
	private String password;
//	private String oldPassword;
	
	
}
