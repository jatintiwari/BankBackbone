package com.bank.app.DAO;

import java.util.Collection;
import java.util.List;

import com.bank.app.model.Account;

public interface AccountDAO {

	void saveAccount(Account account);
	
	List<Account> accountList();

	Account getAccount(Long id);

	Account getAccountFromUsername(String username);

	void deactivateAccount(Account account);
	
}
