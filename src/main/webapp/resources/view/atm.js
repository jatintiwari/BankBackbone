/**
 * 
 */

var app= app||{};

app.AtmLoginView = Backbone.View.extend({
	el : '#atmLoginForm',
	initialize : function() {
		app.bindValidation(this);
	},
	
	events : {
		'submit' : 'submit'
	},

	submit : function(e) {
		console.log('check in');
		e.preventDefault();
		var atmLogin= $('#atmLoginForm').serializeObject();
		console.log(JSON.stringify(atmLogin));
		this.model.set('atmNumber',atmLogin.atmNumber);
		this.model.set('atmPin',atmLogin.atmPin);
		this.model.save(null,{
			success:function(model,response,option){
				if(response.success==='true'){
					window.location.href = response.targetUrl;
				}else if(response.error){
					console.log(response.error);
					$(".alert").html('<span >'+ response.error+ '</span>').removeClass('hidden').fadeIn(5000);
				}
			}
		});
	}

});

app.AtmFormView=Backbone.View.extend({
	el:'#atmTxForm',
	events:{
		'submit':'submit'
	},
	initialize:function(){
		app.bindValidation(this);
	},
	submit:function(e){
		console.log("transfer cash transfer");
		e.preventDefault();
		var amount=$('#amount').val();
		this.model.set('amount',amount);
		this.model.set('txType','withdraw');
		this.model.save({},{
			success:function(){
				console.log('success');
				setTimeout(function(){
					window.location.reload();
				},20);
			}
		});

	},
	render:function(){
		return this;
	}
});
app.AtmTxView= Backbone.View.extend({
	tagName:'tr',
	template:_.template($('#txTableBodyTemplate').html()),
	intiliaze:function(){
		
	},
	render:function(){
		this.$el.html(this.template(this.model.toJSON()));
		return this;
	}
})

app.AtmTxListView= Backbone.View.extend({
	el:'#txTableBody',
	initialize:function(){
		this.lastTx(this.collection.last());
		this.render();
	},
	render:function(){
		this.collection.each(this.addOne,this);
		return this;
	},
	addOne:function(tx){
		app.atmTxView= new app.AtmTxView({model:tx});
		this.$el.append(app.atmTxView.render().el);
	},
	lastTx:function(txInfo){
		var tx= txInfo.toJSON();
		$('#currentBalance').attr('value',tx.currentBalance);
	}
});

app.ChangePin=Backbone.View.extend({
	el:"#myModal",
	events:{
		'click #savePin':'submit',
		'keyup #newPin':'newPin'
	},
	initialize:function(){
		app.bindValidation(this);
		console.log('change pin');
	},
	submit:function(e){
		e.preventDefault();
		console.log("savePin");
		var newPin= $('#atmPin').val();
		this.model.set('atmPin',newPin);
		this.model.save(null,{
			success:function(){
				console.log('pin changed');
				$("[data-dismiss='modal']").click();
			}
		});
		
	},
	newPin:function(){
		console.log(' new pin has changed');
	}
})