/**
 * 
 */


var app = app || {};

app.AtmLogin = Backbone.Model.extend({
	url : 'atmLogin',
	defaults : {
		atmNumber : null,
		atmPin : null
	},

	validation : {
		atmNumber : {
			required : true,
			pattern:'digits'
		},
		atmPin : {
			required : true,
			length : 4,
			pattern:'digits'
		},
	}
});

app.PinChange=Backbone.Model.extend({
	url:'changePin',
	defaults:{
		atmPin:null
	},
	validation : {

		atmPin : {
			required : true,
			length : 4,
		},
	}
});