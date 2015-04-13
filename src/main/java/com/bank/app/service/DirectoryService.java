package com.bank.app.service;

import java.util.Collection;

import com.bank.app.model.Account;
import com.bank.app.model.Transactions;
import com.bank.app.model.User;

public interface DirectoryService {

	void saveAccount(Account account);

	Collection<Account> accountList();

	Account getAccount(Long id);

	User getUser(String username);

	Account getAccountFromUsername(String username);

	void saveUser(User user);

	Collection<Transactions> getAllTx();

	void saveTx(Transactions tx);


}
