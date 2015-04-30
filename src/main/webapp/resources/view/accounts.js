var app= app|| {};
var vent= _.extend

app.AccountView= Backbone.View.extend({
	tagName:'tr',
	template:_.template($('#accountsListTemplate').html()),
	events:{
		'dblclick #edit':'edit',
		'click #edit':'display'
	},
	edit:function(){
		router.navigate('accountsList/'+this.model.id+'/edit',true)
	},
	display:function(){
		router.navigate('accountsList/'+this.model.id,true)
	},
	initialize:function(){

	},
	render:function(){
		this.$el.html(this.template(this.model.toJSON()));
		return this;
	}
});


app.AccountsView= Backbone.View.extend({
	tagName:'td',
	initalize:function(){
		this.collection.on('add',this.addOne,this);
	},
	render:function(){
		this.collection.each(this.addOne,this);
		return this;
	},
	addOne:function(account){
		console.log(JSON.stringify(account));
		var accountView= new app.AccountView({model:account});
		this.$el.append(accountView.render().el);
	}
});


app.AddAccountView= Backbone.View.extend({
	el:'#addAccountForm',
	initialize:function(){
		app.bindValidation(this);
	},
	events:{
		'keyup #username':'checkUsername',
		'submit':'submit',
		'click #delete':'destroy'
	},
	checkUsername:function(event){
		var target= event.target;
		var usernameVal= $.trim(target.value);
		if(usernameVal){
			this.CheckUsername=Backbone.Model.extend({url:'accounts/checkUsername'});
			this.checkUsername= new this.CheckUsername({username:usernameVal});
			this.checkUsername.save(null,{
				success:function(model,response,option){
					if(response.success){
						$('#error-username').html('<section style="color:green">'+response.success+'</section>').removeClass('hidden');
					}else if(response.error){
						$('#error-username').html('<section style="color:red">'+response.error+'</section>').removeClass('hidden');
					}
				}
			});
		}else{
			$('#error-username').html('');
		}
	},
	submit:function(e){
		e.preventDefault();
		var addAccountForm=$('#addAccountForm').serializeObject();
		this.model.set('accountType',addAccountForm.accountType);
		this.model.set('name',addAccountForm.name);
		this.model.set('currentBalance',addAccountForm.currentBalance);
		this.model.set('username',addAccountForm.username);
		this.model.set('password',addAccountForm.password);	
		if(addAccountForm.atmRequired){
			this.model.set('atmRequired',true);
		}
		if(addAccountForm.accountType==='saving'){
			this.model.set('minimumBalance',500);
		}else{
			this.model.set('minimumBalance',1000);
		}
		console.log(JSON.stringify(addAccountForm));
		this.model.save(null,{
			success:function(model,response,option){
				setTimeout(function(){
					if(response.error){
						$('.alert').html(response.error).removeClass('hidden');
						return;
					}
					router.navigate('',true);
				},200);
			}
		});
	},
	destroy:function(){
		console.log('destory');
		this.model.destroy();
		var _this=this;
		setTimeout(function(){
			_this.$el.remove();
			router.navigate('',true);
			console.log(accounts.toJSON());
		},100);
	},
});

app.AccountCountView=Backbone.View.extend({
	el:'#accountCount',
	self:this,
	events:{
		'click #total':'total',
		'click #saving':'saving',
		'click #current':'current'
	},
	template:_.template($('#accountCountTemplate').html()),
	initialize:function(){
		self.total=this.collection.length;
		self.saving=this.collection.where({accountType:'saving'});
		self.current=this.collection.where({accountType:'current'});
		this.newCountModel= new app.AccountCount({
			total:self.total,
			saving:self.saving.length,
			current:self.current.length
		});
		console.log(this.newCountModel.toJSON());
		this.render(this.newCountModel);
	},
	render:function(newCountModel){
		this.$el.html(this.template(this.newCountModel.toJSON()));
		return this;
	}, 
	
	total:function(){
		this.accountsView= new app.AccountsView({collection:accounts});
		$('#listName').html('Accounts:All');
		$('#listTable').html(this.accountsView.render().el);
	},
	saving:function(){
		var _this= this;
		this.saving=new app.Accounts();
		_.each(self.saving,function(account){
			_this.saving.add(account);
		});
		this.accountsView= new app.AccountsView({collection:_this.saving});
		$('#listName').html('Accounts:Saving');
		$('#listTable').html(this.accountsView.render().el);
	},
	current:function(){
		var _this= this;
		this.current=new app.Accounts();
		_.each(self.current,function(account){
			_this.current.add(account);
		});
		this.accountsView= new app.AccountsView({collection:_this.current});
		$('#listName').html('Accounts:Current');
		$('#listTable').html(this.accountsView.render().el);
	}
});
