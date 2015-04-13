package com.bank.app.DAO;

import java.util.Collection;

import com.bank.app.model.Account;

public interface AccountDAO {

	void saveAccount(Account account);
	
	Collection<Account> accountList();

	Account getAccount(Long id);

	Account getAccountFromUsername(String username);
	
}
