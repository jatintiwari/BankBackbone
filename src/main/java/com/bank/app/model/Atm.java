package com.bank.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown= true)
public class Atm {

	@Id
	private Long atmNumber;
	private Integer atmPin;
	
	@OneToOne
	@JoinColumn(name="User")
	private User user; 	


	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getAtmNumber() {
		return atmNumber;
	}
	public void setAtmNumber(Long atmNumber) {
		this.atmNumber = atmNumber;
	}
	public Integer getAtmPin() {
		return atmPin;
	}
	public void setAtmPin(Integer atmPin) {
		this.atmPin = atmPin;
	}
	
	
}
