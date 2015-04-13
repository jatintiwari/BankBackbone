package com.bank.app.DAO;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bank.app.model.Transactions;
import com.bank.app.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	
	@Autowired
	SessionFactory sessionFactory;
	
	public User getUser(String username) {
		return (User)sessionFactory.getCurrentSession().get(User.class, username);
	}

	public void addUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	public Collection<Transactions> getAllTx() {
		Query query= sessionFactory.getCurrentSession().createQuery("from Transactions where user =:user");
		query.setString("user", User.currentUser);
		return query.list();
	}

	public void saveTx(Transactions tx) {
		sessionFactory.getCurrentSession().save(tx);
	}

	
}
