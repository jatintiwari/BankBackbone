/**
 * 
 */

var app= app || {};

app.Tx= Backbone.Model.extend({
	defaults:{
		id:null,
		txType:null,
		date:null,
		amount:0,
		currentBalance:0,
		user:null
	},
	url:'tx',
	validation:{
		amount:{
			required:true,
			min:1
		},
		txType:{
			required:true,
			msg:'Select Tx',
			oneOf:['withdraw','deposit']
		},
		
		
		},
});

app.Transactions= Backbone.Collection.extend({
	model:app.Tx,
	url:'txList',
	comparator: function(tx) {
        return tx.get('date');
    }
})