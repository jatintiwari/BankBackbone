/**
 * 
 */


var app= app || {};

app.bindValidation= function(view){
	
	Backbone.Validation.bind(view,{
		  valid: function(view, attr, selector) {
			  var $el = view.$('[name=' + attr + ']'), 
	            $group = $el.closest('.form-group');
	        
	        $group.removeClass('has-error');
	        $group.find('.help-block').html('').addClass('hidden');
		      },
		      invalid: function(view, attr, error, selector) {
		    	  console.log(error);
		    	  var $el = view.$('[name=' + attr + ']'), 
		            $group = $el.closest('.form-group');
		        $group.addClass('has-error');
		        $group.find('.help-block').html(error).removeClass('hidden');
		      }
	});
	
};


function logout(){
	console.log("Logout");
	$.ajax({
		url:"logout",
		type:"GET",
		success:function(response){
			if(response)
			window.location.href=window.location.origin+"/BankBackbone";
		}
	});
};