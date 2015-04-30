package com.bank.app.DAO;

import java.util.Collection;
import java.util.List;

import com.bank.app.model.Transactions;
import com.bank.app.model.User;

public interface UserDAO {

	User getUser(String username);
	
	void addUser(User user);

	List<Transactions> getAllTx();

	void saveTx(Transactions tx);

	List<Transactions> getInitTxList();


}
