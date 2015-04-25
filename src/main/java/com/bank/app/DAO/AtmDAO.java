package com.bank.app.DAO;

import com.bank.app.model.Atm;

public interface AtmDAO {

	void createAtm(Atm atm);

	Atm getAtm(Long atmNumber);

}
