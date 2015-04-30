/**
 * 
 */

var app= app || {};

app.TxView= Backbone.View.extend({
	tagName:'tr',
	template:_.template($('#txTemplate').html()),
	events:{
		
	},
	initialize:function(){
		this.model.on('change',this.render,this);
	},
	render:function(){
		this.$el.html(this.template(this.model.toJSON()));
		return this;
	}
});

app.TrasactionsView= Backbone.View.extend({
	tagName:'td',
	initialize:function(){
	},
	render:function(){
		var _this= this;
		this.collection.each(function(tx){
			this.txView= new app.TxView({model:tx});
			_this.$el.append(this.txView.render().el);
		},this);
		return this;
	}
});

app.AddTxView= Backbone.View.extend({
	el:'#txForm',
	initialize:function(){
		app.bindValidation(this);
	},
	events:{
		'submit':'submit'
	},
	submit:function(e){
		e.preventDefault();
		$(".alert").html('').addClass('hidden');
		console.log("Tx Submit");
		var txForm=$('#txForm').serializeObject();
		
		this.model.set('txType',txForm.txType);
		this.model.set('amount',txForm.amount);
		this.model.save(null,{
			success:function(model, response){
				console.log("success");
				console.log(model);
				console.log(response);
				if(response.error){
					$(".alert").html(response.error).removeClass('hidden');
				}else{
					setTimeout(function(){
						console.log("tx-added");
						userRouter.navigate('',{trigger:true});
					},100);
				}
			},
			error:function(model, response){
				console.log("error");
				console.log(model);
				console.log(response);
				$(".alert").html(response.error).removeClass('hidden');
			}
		});
		
	}
});

app.CurrentBalanceView= Backbone.View.extend({
	el:'#currentBal',
	template:_.template($('#currentBalanceTemplate').html()),
	initialize:function(){
		this.render();
	},
	render:function(){
	var lastTx=this.collection.last();
	console.log(lastTx.toJSON());
	this.$el.html(this.template(lastTx.toJSON()));
		return this;
	}
});


