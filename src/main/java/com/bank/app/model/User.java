package com.bank.app.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
	

	@Id
	private String username;
	private String password;
	private String userType;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	private Collection<Transactions> tx= new ArrayList<Transactions>();
	@Transient
	public static String currentUser;
	@Transient
	public static String currentUserType;
	
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
	public Collection<Transactions> getTx() {
		return tx;
	}
	public void setTx(Collection<Transactions> tx) {
		this.tx = tx;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
