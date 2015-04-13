package com.bank.app.DAO;

import java.util.Collection;

import com.bank.app.model.Transactions;
import com.bank.app.model.User;

public interface UserDAO {

	User getUser(String username);
	
	void addUser(User user);

	Collection<Transactions> getAllTx();

	void saveTx(Transactions tx);


}
