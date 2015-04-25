package com.bank.app.controller;

import java.util.Collection;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.app.model.Account;
import com.bank.app.model.Transactions;
import com.bank.app.model.User;
import com.bank.app.service.DirectoryService;


@Controller
public class UserController {

	@Autowired
	DirectoryService directoryService;

	private ObjectMapper objectMapper;
	private JSONObject jsonsObject;
	private JSONArray jsonArray;
//user has one to many relation with tx so we can get it by getting the main the tx in user class.
	@RequestMapping(value="txList", method=RequestMethod.GET)
	public @ResponseBody String getAllTx(){
		if(User.currentUser!=null){
			try{
				jsonArray= new JSONArray();
				System.out.println("List for "+User.currentUser);
				Collection<Transactions> allTx= directoryService.getAllTx();
				if(allTx.isEmpty()){
					Account account = directoryService.getAccountFromUsername(User.currentUser);
					jsonsObject= new JSONObject();
					jsonsObject.put("currentBalance", account.getCurrentBalance());
					jsonsObject.put("listEmpty", true);
					jsonArray.put(jsonsObject);
					return jsonArray.toString(); 
				}
				for(Transactions tx:allTx){
					jsonsObject= new JSONObject();
					jsonsObject.put("id", tx.getId());
					jsonsObject.put("txType", tx.getTxType());
					jsonsObject.put("date", tx.getDate());
					jsonsObject.put("amount", tx.getAmount());
					jsonsObject.put("currentBalance", tx.getCurrentBalance());
					jsonsObject.put("user", tx.getUser().getUsername());
					jsonArray.put(jsonsObject);
				}
				return jsonArray.toString();
			}catch(Exception e){
				e.printStackTrace();
				return "{\"error\":\"list-null\"}"; 
			}
		}
		else{
			return null;
		}
	}

	@RequestMapping(value="tx",method=RequestMethod.POST)
	public @ResponseBody String tx(@RequestBody String txInfo){
		if(User.currentUser!=null){
			System.out.println("tx: "+txInfo);
			try {
				objectMapper= new ObjectMapper();
				Transactions tx= objectMapper.readValue(txInfo,Transactions.class);
				Account account= directoryService.getAccountFromUsername(User.currentUser);
				User user= directoryService.getUser(User.currentUser);
				if(tx.getTxType().equalsIgnoreCase("deposit")){
				
					Double currentBal=account.getCurrentBalance()+tx.getAmount();
					System.out.println(currentBal);
					account.setCurrentBalance(currentBal);
					tx.setCurrentBalance(currentBal);
				}
				else if (tx.getTxType().equalsIgnoreCase("withdraw")){
					Double currentBal=account.getCurrentBalance()-tx.getAmount();
					if(currentBal<account.getMinimumBalance()){
						return "{\"error\":\"Please maintain minimun balance of "+account.getMinimumBalance()+"\"}";
					}
					account.setCurrentBalance(currentBal);
					tx.setCurrentBalance(currentBal);
				}
				tx.setDate(new Date());
				tx.setUser(account.getUser());
				user.getTx().add(tx);
				directoryService.saveAccount(account);
				directoryService.saveUser(user);
				directoryService.saveTx(tx);
				return "{\"success\":\"tx-added\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{\"error\":\"tx-exception\"}";
			}



		}else{
			return null;
		}

	}

}
