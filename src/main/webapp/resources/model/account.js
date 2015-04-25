var app= app || {};

app.Account= Backbone.Model.extend({

	urlRoot:'accounts',

	defaults:{
		id:null,
		accountType:null,
		name:'',
		minimumBalance:null,
		currentBalance:0,
		username:null,
		password:null,
		active:false,
		atmRequired:false
	},
	
	validation:{
		name:{
			required:true
		},
		username:function(val, attr, computed) {
		      return Backbone.Validation.validators.rangeLength(val, attr,[4,12], this);
		},
		password:{
			required:true,
		},
		accountType:{
			required:true
		},
		currentBalance:{
			required:true,
			min:500,
			pattern:'number'
		},
	}
});

app.Accounts=Backbone.Collection.extend({
	model:app.Account,
	url:'accounts'
});

app.AccountCount=Backbone.Model.extend({
	defaults:{
		total:0,
		saving:0,
		current:0
	}
});

