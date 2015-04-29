package com.bank.app.DAO;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bank.app.model.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	SessionFactory sessionFactory;

	public List<Account> accountList() {
		return sessionFactory.getCurrentSession().createQuery("from Account").list();
	}

	public void saveAccount(Account account) {
		sessionFactory.getCurrentSession().saveOrUpdate(account);
	}


	public Account getAccount(Long id) {
		return (Account)sessionFactory.getCurrentSession().load(Account.class, id);
	}

	public Account getAccountFromUsername(String username) {
		Query query= sessionFactory.getCurrentSession().createQuery("from Account where user=:user");
		query.setString("user", username);
		try{
			Account account= (Account) query.list().get(0);
			return account;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public void deactivateAccount(Account account) {
		sessionFactory.getCurrentSession().delete(account);
		
	}
	
	

}
