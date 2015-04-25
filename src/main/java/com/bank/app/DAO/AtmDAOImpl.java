package com.bank.app.DAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bank.app.model.Atm;


@Repository
public class AtmDAOImpl implements AtmDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	
	public void createAtm(Atm atm) {
		sessionFactory.getCurrentSession().saveOrUpdate(atm);
	}


	public Atm getAtm(Long atmNumber) {
		return (Atm)sessionFactory.getCurrentSession().get(Atm.class,atmNumber);
	}
	
	

}
