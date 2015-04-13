package com.bank.app.controller;

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
import com.bank.app.model.User;
import com.bank.app.service.DirectoryService;

@Controller
public class LoginController {

	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private ObjectMapper objectMapper;
	
	@Autowired
	DirectoryService directoryService;
	

	@RequestMapping(value="login",method=RequestMethod.POST)
	public @ResponseBody String validateLogin(@RequestBody String loginInfo){
		try {
			System.out.println("Login: "+loginInfo);
			objectMapper= new ObjectMapper();
			User loginUser= objectMapper.readValue(loginInfo, User.class);
			
			if(loginUser.getUsername().equalsIgnoreCase("admin")){
				if(loginUser.getPassword().equalsIgnoreCase("admin")){
					User.currentUserType="admin";
							
					return "{\"userType\":\"admin\",\"targetUrl\":\"admin\"}";
				}return "{\"error\":\"Password Invalid\"}";
			}
			User validateUser= directoryService.getUser(loginUser.getUsername());
			Account accountInfo= directoryService.getAccountFromUsername(loginUser.getUsername());
			if(validateUser !=null){
				if(loginUser.getUsername().equalsIgnoreCase(validateUser.getUsername())){
					if(loginUser.getPassword().equalsIgnoreCase(validateUser.getPassword())){
						if(!accountInfo.isActive()){
							return "\"error\":{\"Username Inactive\"}";
						}
						User.currentUser=validateUser.getUsername();
						User.currentUserType="client";
						jsonObject= new JSONObject();
						jsonArray= new JSONArray();
						jsonObject.put("userType", "client");
						jsonArray.put(jsonObject);
						return "{\"userType\":\"client\",\"targetUrl\":\"client\"}";
					}else{
						return "\"error\":{\"Password Invalid\"}";
					}
				}
			}
			User.currentUserType=null;
			return "\"error\":{\"Username Invalid\"}";
		} catch (Exception e) {
			e.printStackTrace();
			User.currentUserType=null;
			return "\"error\":\"Username Exception\"";
		}finally{
			jsonArray=null;
			jsonObject=null;
		}
	}
	
	@RequestMapping(value="admin", method= RequestMethod.GET)
	public String adminView(){
		return "admin";
	}
	@RequestMapping(value="client", method= RequestMethod.GET)
	public String clientView(){
		return "client";
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public @ResponseBody String logout(){
		User.currentUserType=null;
		System.out.println("Logout");
		return "\"targetUrl\":\"BankBackbone\"";
	}
}
