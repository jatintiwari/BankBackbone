package com.bank.app.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.app.model.Account;
import com.bank.app.model.Atm;
import com.bank.app.model.Transactions;
import com.bank.app.model.User;
import com.bank.app.service.DirectoryService;

@Controller
public class AtmController {
	@Autowired
	DirectoryService directoryService;

	private ObjectMapper objectMapper;

	@RequestMapping(value="atmTx",method=RequestMethod.POST)
	public @ResponseBody String atmTx(@RequestBody String txInfo){
		objectMapper= new ObjectMapper();
		if(User.currentUser!=null){
			try{
				System.out.println("ATM tx has started");
				Transactions atmTx= objectMapper.readValue(txInfo, Transactions.class);
				Account account= directoryService.getAccountFromUsername(User.currentUser);
				User user= directoryService.getUser(User.currentUser);
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	@RequestMapping(value="changePin", method=RequestMethod.POST)
	public @ResponseBody String changePin(@RequestBody String newPin){
		objectMapper= new ObjectMapper();
		if(User.currentUser!=null){
			try{
				Atm atmLoginInfo= objectMapper.readValue(newPin, Atm.class);
				Account account= directoryService.getAccountFromUsername(User.currentUser);
				Atm atm= account.getAtm();
				if(atmLoginInfo.getAtmPin().SIZE<4){
					return "{\"error\":\"Pin should contain 4 digits\"}";
				}
				atm.setAtmPin(atmLoginInfo.getAtmPin());
				directoryService.createAtm(atm);
				return "{\"success\":\"Pin changed\"}";
			}catch(Exception e){
				e.printStackTrace();
				return "{\"error\":\"Invalid change pin request\"}";
			}
		}
		return "{\"error\":\"User not defined\"}";

	}

}
