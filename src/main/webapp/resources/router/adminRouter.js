
app.Router= Backbone.Router.extend({
	routes:{
		'':'admin',
		'add':'add',
		'accountsList/:id(/:action)':'accountActions',
	},
	initialize:function(){
		window.accounts = new app.Accounts();
	},

	admin:function(){
		console.log(app.userType);
		$('#workspace').html('');
		$('#header').html(_.template($('#adminHeaderTemplate').html()));
		var _this=this;
			accounts.fetch({
				success:function(){
					this.accountsView= new app.AccountsView({collection:accounts});
					setTimeout(function(){
						$('#listName').html('Accounts:All');
						$('#listTable').html(this.accountsView.render().el);
						this.accountCountView= new app.AccountCountView({collection:accounts});

					},100);
				}
			});
		
	},
	add:function(){
		console.log('this is an add function');
		$('#header').html(_.template($('#adminHeaderTemplate').html()));
		var _this=this;
		this.account= new app.Account();
		$('#workspace').html(_.template($('#addAccountFormTemplate').html(),{account:this.account,action:'add'}));
		if(accounts.length==0){
			accounts.fetch();
		}
		setTimeout(function(){
			this.accountsView= new app.AccountsView({collection:accounts});
			$('#listTable').html(this.accountsView.render().el);
			this.accountCountView= new app.AccountCountView({collection:accounts});
		},20);
		this.account= new app.Account();
		this.addAccountView= new app.AddAccountView({model:this.account});

	},
	accountActions:function(id,action){
		console.log('accountActions');
		this.account= window.accounts.get(id);
		$('#workspace').html(_.template($('#addAccountFormTemplate').html(),{account:this.account.toJSON(),
			action:action}));
		this.addAccountView= new app.AddAccountView({model:this.account});
		console.log(this.account.toJSON());
	}
});
