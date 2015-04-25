package com.bank.app.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bank.app.model.Account;
import com.bank.app.model.Atm;
import com.bank.app.model.Transactions;
import com.bank.app.model.User;
import com.bank.app.service.DirectoryService;


@Controller
@RequestMapping(value="accounts")
public class AccountsController {

	@Autowired
	DirectoryService directoryService;


	private ObjectMapper objectMapper;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private Atm atm= null;

	@RequestMapping(method=RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody String accountList(){
		try {
			if(User.currentUserType.equalsIgnoreCase("admin")){
				Collection<Account> accountsList= directoryService.accountList();
				jsonArray= new JSONArray();
				for(Account account: accountsList){
					if(account.isActive()){
						jsonObject= new JSONObject();
						jsonObject.put("id", account.getId());
						jsonObject.put("accountType", account.getAccountType());
						jsonObject.put("name", account.getName());
						jsonObject.put("username", account.getUser().getUsername());
						jsonObject.put("userType", account.getUser().getUserType());
						jsonObject.put("password", account.getUser().getPassword());
						jsonObject.put("minimumBalance", account.getMinimumBalance());
						jsonObject.put("currentBalance", account.getCurrentBalance());
						if(account.isAtmRequired()){
							jsonObject.put("atmRequired", account.isAtmRequired());
						}
						jsonArray.put(jsonObject);
					}
				}
				return jsonArray.toString();
			}
			return "{\"error\":\"userType-undefined\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\"null\"}";
		}finally{
			objectMapper=null;
			jsonArray=null;
			jsonObject=null;
		}
	}

	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody String addAccount(@RequestBody String accountInfo){
		try {
			if(User.currentUserType.equalsIgnoreCase("admin")){
				objectMapper= new ObjectMapper();
				User user= objectMapper.readValue(accountInfo, User.class);
				Account account= objectMapper.readValue(accountInfo,Account.class);
				User checkUser= directoryService.getUser(user.getUsername());
				if(checkUser!=null && (account.getId()==null)){
					return "{\"error\":\"username exists\"}";
				}
				user.setUserType("client");
				account.setActive(true);
				account.setUser(user);
				directoryService.saveUser(user);
				if(account.isAtmRequired()){
					long atmNumber = (long)(Math.random()*Long.MAX_VALUE);
					int atmPin = (int)(Math.random()*9000)+1000;
					atm= new Atm();
					System.out.println(atmNumber);
					atm.setAtmNumber(atmNumber);
					atm.setAtmPin(atmPin);
					atm.setUser(user);
					directoryService.createAtm(atm);
					account.setAtm(atm);

				}
				directoryService.saveAccount(account);
				return "{\"success\":\"account add\"}";
			}
			return "{\"error\":\"userType-undefined\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"Exception\":\"Add Account-Exception\"}";
		}
		finally{
			objectMapper=null;
			jsonArray=null;
			jsonObject=null;
		}
	}

	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody String updateAccount(@RequestBody String accountInfo,
			@PathVariable("id") Long id){

		try{
			if(User.currentUserType.equalsIgnoreCase("admin")){
				System.out.println(accountInfo);
				System.out.println(id);
				objectMapper= new ObjectMapper();
				User readUser=objectMapper.readValue(accountInfo, User.class);
				Account readAccount= objectMapper.readValue(accountInfo, Account.class);
				Account account= directoryService.getAccount(id);
				User user = account.getUser();
				user.setPassword(readUser.getPassword());
				readAccount.setActive(true);
				System.out.println(readAccount.getCurrentBalance()+";;;;;;;"+account.getCurrentBalance());
				System.out.println(readAccount.getCurrentBalance().equals(account.getCurrentBalance()));
				if(!readAccount.getCurrentBalance().equals(account.getCurrentBalance())){
					Transactions tx = new Transactions();
					if(readAccount.getCurrentBalance()>account.getCurrentBalance()){
						tx.setTxType("desposit");
						tx.setAmount(readAccount.getCurrentBalance()-account.getCurrentBalance());
					}else if(readAccount.getCurrentBalance()<account.getCurrentBalance()){
						tx.setTxType("withdraw");
						tx.setAmount(account.getCurrentBalance()-readAccount.getCurrentBalance());
					}
					tx.setUser(account.getUser());
					tx.setDate(new Date());
					tx.setCurrentBalance(readAccount.getCurrentBalance());
					user.getTx().add(tx);
					directoryService.saveTx(tx);
				}
				readAccount.setUser(user);
				directoryService.saveUser(user);
				directoryService.saveAccount(readAccount);
				return "{\"success\":\"updated\"}";	
			}
			return "{\"error\":\"userType-undefined\"}";
		}catch( Exception e){
			e.printStackTrace();
			return "{\"error\":\"Add Account-Exception\"}";	
		}
		finally{
			objectMapper=null;
			jsonArray=null;
			jsonObject=null;
		}
	}
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public @ResponseBody String deleteAccount(@PathVariable("id") Long id){
		if(User.currentUserType.equalsIgnoreCase("admin")){
			Account account= directoryService.getAccount(id);
			account.setActive(false);
			directoryService.saveAccount(account);
			return "{\"success\":\"updated\"}";
		}
		return "{\"error\":\"userType-undefined\"}";
	}



}
