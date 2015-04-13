var app= app || {};

app.Login= Backbone.Model.extend({
	url:'login',
	defaults:{
		userType:null,
		username:'',
		password:''
	},
	
	validations:{
		
	}
});
