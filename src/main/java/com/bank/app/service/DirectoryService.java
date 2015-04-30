package com.bank.app.service;

import java.util.Collection;
import java.util.List;

import com.bank.app.model.Account;
import com.bank.app.model.Atm;
import com.bank.app.model.Transactions;
import com.bank.app.model.User;

public interface DirectoryService {

	void saveAccount(Account account);

	List<Account> accountList();

	Account getAccount(Long id);

	User getUser(String username);

	Account getAccountFromUsername(String username);

	void saveUser(User user);

	List<Transactions> getAllTx();

	void saveTx(Transactions tx);

	void createAtm(Atm atm);

	Atm getAtm(Long atmNumber);

	void deactivateAccount(Account account);

	List<Transactions> getInitTxList();


}
