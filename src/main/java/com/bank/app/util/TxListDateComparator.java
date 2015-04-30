package com.bank.app.util;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.bank.app.model.Account;
import com.bank.app.model.Transactions;


/*
 * Tx sorting based on date.
 * 
 * Can add sorting based in amount or any other prop by adding static methods and then 
 * referencing it in the sort mechanism.
 */

@Component
public class TxListDateComparator implements Comparator<Transactions> {

	public int compare(Transactions tx1, Transactions tx2) {
		return tx1.getId().compareTo(tx2.getId())*(-1);
		
	}

}
