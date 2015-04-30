/**
 * 
 */


var app= app|| {};

app.AtmRouter= Backbone.Router.extend({
	routes:{
		'':'atm'
	},
	
	atm:function(){
		console.log('atm');
		$('#amount').val('');
		$('#bankName').html("Lex Nimble Bank-ATM");
		var txList= new app.Transactions();
		var atmFormView= new app.AtmFormView({model:new app.Tx()});
		var changePin = new app.ChangePin({model:new app.PinChange()});
		txList.fetch({
			success:function(model,response,options){
				app.atmTxListView= new app.AtmTxListView({collection:txList});
			},
			error:function(){
				
			}
		});
		
	}
	
});

(function(){
	app.atmRouter= new app.AtmRouter();
	Backbone.history.start();
})();
