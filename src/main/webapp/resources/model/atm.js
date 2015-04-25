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
			required : true|false,
		},
		atmPin : {
			required : true,
			length : 4,
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