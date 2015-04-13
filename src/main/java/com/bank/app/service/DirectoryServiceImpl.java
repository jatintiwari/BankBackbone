package com.bank.app.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.app.DAO.AccountDAO;
import com.bank.app.DAO.UserDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Transactions;
import com.bank.app.model.User;

@Service
public class DirectoryServiceImpl implements DirectoryService {

	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Transactional
	public void saveAccount(Account account) {
		accountDAO.saveAccount(account);
	}
	
	@Transactional
	public Collection<Account> accountList() {
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

}
