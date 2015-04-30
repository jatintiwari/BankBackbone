package com.bank.app.DAO;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

	public List<Transactions> getAllTx() {
		Query query= sessionFactory.getCurrentSession().createQuery("from Transactions where user =:user");
		query.setString("user", User.currentUser);
		return query.list();
	}

	public void saveTx(Transactions tx) {
		sessionFactory.getCurrentSession().save(tx);
	}

	public List<Transactions> getInitTxList() {
		Criteria criteria= sessionFactory.getCurrentSession().createCriteria(Transactions.class);
		return criteria.add(Restrictions.sizeLe("txType", 5)).list();
	}

	
}
