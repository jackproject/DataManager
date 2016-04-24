package com.soho.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="t_user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer user_id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
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

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username
				+ ", password=" + password + "]";
	}

}
