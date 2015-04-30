/**
 * 
 */

var app= app|| {};

app.userRouter= Backbone.Router.extend({
	
	routes:{
		'':'user',
		'tx':'tx'	
	},
	user:function(){
		console.log("user route");
		window.txs= new app.Transactions();
		$("#workspace").empty();
		txs.fetch({
			success:function(model,response,options){
				console.log(JSON.stringify(response));
				this.trasactionsView= new app.TrasactionsView({collection:txs});
				setTimeout(function(){
					$('#header').html(_.template($('#clientHeaderTemplate').html()));
					$("#listTable").html(this.trasactionsView.render().el);
					this.currentBalanceView= new app.CurrentBalanceView({collection:txs});
				},50);
			},
			error:function(model,response,options){
				console.log('Error-user');
			}
		});
	},
	tx:function(){
		console.log("tx form");
		$('#workspace').html(_.template($('#txFormTemplate').html()));
		app.addTxView= new app.AddTxView({model:new app.Tx()});
	}
	
	
});

(function(){
	window.userRouter= new app.userRouter();
	Backbone.history.start();
})();