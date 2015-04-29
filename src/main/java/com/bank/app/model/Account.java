package com.bank.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql="UPDATE Account SET active=0 WHERE id= ?;")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Account implements Comparable<Account> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String accountType;
	private String name;
	private Double minimumBalance;
	private Double currentBalance;
	private boolean active;
	private boolean atmRequired;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="User")
	private User user;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="Atm")
	private Atm atm;

	public Atm getAtm() {
		return this.atm;
	}
	public void setAtm(Atm atm) {
		this.atm = atm;
	}
	public boolean isAtmRequired() {
		return atmRequired;
	}
	public void setAtmRequired(boolean atmRequired) {
		this.atmRequired = atmRequired;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getMinimumBalance() {
		return minimumBalance;
	}
	public void setMinimumBalance(Double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}
	public Double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	//let's sort the account based on id in descending order
    //returns a negative integer, zero, or a positive integer as this account id
    //is less than, equal to, or greater than the specified object.
	public int compareTo(Account account) {
		return(int)( account.id-this.id );
	}

}
