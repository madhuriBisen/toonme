package com.app.toonme.filter.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "app_user_details")
public class App_User_Details {

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getCreated_datetime() {
		return created_datetime;
	}

	public void setCreated_datetime(String created_datetime) {
		this.created_datetime = created_datetime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long user_id;

	@Column(name = "username", nullable = true, unique = true, length = 20)
	private String username;

	@Column(name = "password", nullable = true, length = 20)
	private String password;

	@Column(name = "full_name", nullable = true, length = 50)
	private String full_name;

	@Column(name = "email_id", nullable = true, length = 50)
	private String email_id;

	@Column(name = "created_datetime", nullable = true, length = 20)
	private String created_datetime;

}
