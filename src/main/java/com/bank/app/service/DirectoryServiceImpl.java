package com.bank.app.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.app.DAO.AccountDAO;
import com.bank.app.DAO.AtmDAO;
import com.bank.app.DAO.UserDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Atm;
import com.bank.app.model.Transactions;
import com.bank.app.model.User;

@Service
public class DirectoryServiceImpl implements DirectoryService {

	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	AtmDAO atmDAO;
	
	@Transactional
	public void saveAccount(Account account) {
		accountDAO.saveAccount(account);
	}
	
	@Transactional
	public List<Account> accountList() {
		return accountDAO.accountList();
	}
	
	@Transactional
	public Account getAccount(Long id) {
		return accountDAO.getAccount(id);
	}
	
	@Transactional
	public User getUser(String username) {
		return userDAO.getUser(username);
	}
	
	@Transactional
	public Account getAccountFromUsername(String username) {
		return accountDAO.getAccountFromUsername(username);
	}
	
	@Transactional
	public void saveUser(User user) {
		userDAO.addUser(user);
		
	}
	
	@Transactional
	public Collection<Transactions> getAllTx() {
		return userDAO.getAllTx();
	}

	@Transactional
	public void saveTx(Transactions tx) {
		userDAO.saveTx(tx);
	}
	
	@Transactional
	public void createAtm(Atm atm) {
		atmDAO.createAtm(atm);
	}
	@Transactional
	public Atm getAtm(Long atmNumber) {
		return atmDAO.getAtm(atmNumber);
	}
	
	@Transactional
	public void deactivateAccount(Account account) {
		accountDAO.deactivateAccount(account);
		
	}
	@Transactional
	public List<Transactions> getInitTxList() {
		return userDAO.getInitTxList();
	}

}
