$(function() {
	//solving the active menu problem
	switch(menu) {
	case 'About Us':
		$('#about').addClass('active');
		break;
	case 'Contact Us':
		$('#contact').addClass('active');
		break;
	case 'All Products':
		$('#listProducts').addClass('active');
		break;
	case 'Manage Products':
		$('#manageProducts').addClass('active');
		break;
	default:
		if(menu=='Home') break;
		$('#listProducts').addClass('active');
		$('#a_' + menu).addClass('active');
		break;
	}
	
	//to tackle the csrf token
	var token = $('meta[name="_csrf"').attr('content');
	var header = $('meta[name="_csrf_header"').attr('content');
	if(token && header && token.length > 0 && header.length > 0)
	{
		//set the token header for the ajax request
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	}
	
	//code for jquery dataTable
	
	let $table = $('#productListTable');
	
	//execute the below code only where we have this table
	if($table.length)
	{
		//console.log('Inside the table');
		let jsonUrl = '';
		if(window.categoryId == '')
		{
			jsonUrl = window.contextRoot + '/json/data/all/products';
		}
		else
		{
			jsonUrl = window.contextRoot + '/json/data/category/' + window.categoryId + '/products';
		}
		
		$table.DataTable({
			lengthMenu: [[3,5,10,-1],['3 Records','5 Records', '10 Records','ALL']],
			pageLength: 5,
			//data: products
			ajax: {
				url: jsonUrl,
				dataSrc: ''	//collection of names without any name
			},
			columns: [
				{
					data: 'code',
					bSortable: false,
					mRender: (data, type, row) => {
						return '<img src="' + window.contextRoot+'/resources/images/' + data + '.jpg" class="dataTableImg" />';
					}
				},
				{
					data: 'name'
				},
				{
					data: 'brand'
				},
				{
					data: 'unitPrice',
					mRender: (data, type, row) => '&euro; ' + data
				},
				{
					data: 'quantity',
					mRender: (data,type,row) => {
						if(data<1)
						{
							return '<span style="color:red">Out of Stock!</span>';
						}
						return data;
					}
				},
				{
					data: 'id',
					bSortable: false,
					mRender: (data, type, row) => {
						var str = '';
						str += '<a href="' + window.contextRoot + '/show/' + data + '/product"><i class="far fa-eye"></i></a>&nbsp;&nbsp;&nbsp;';
						if(window.userRole == 'ADMIN')
						{
							str += '<a href="' + window.contextRoot + '/manage/' + data + '/product"><i class="fas fa-pencil-alt" style="color:red"></i></a>';
						}
						else
						{
							if(row.quantity<1)
							{
								str += '<a href="javascript: void(0)"><i class="fas fa-cart-plus" style="color: grey; cursor: not-allowed"></i></a>';
							}
							else
							{
								str += '<a href="' + window.contextRoot + '/cart/add/' + data + '/product"><i class="fas fa-cart-plus"></i></a>';
							}
						}
						return str;
					}
				}
			]
		});
	}
	
	//dismissing the alert after 3 seconds
	//var $alert = $('.alert');
	var $alert = $('#myDiv');
	if($alert.length)
	{
		setTimeout(function() {
			$alert.fadeOut('slow')
		},3000);
	}
	
	//-----------------------------------
	
	/*$('.switch input[type="checkbox"]').on('change', function() {
		var checkbox = $(this);
		var checked = checkbox.prop('checked');
		var dMsg = (checked) ? 'You want to activate the product? ': 'You want to deactivate the product?';
		var value = checkbox.prop('value');
		
		bootbox.confirm({
			size: 'medium',
			title: 'Product Activation & Deactivation',
			message: dMsg,
			callback: function (confirmed) {
				if(confirmed)
				{
					console.log("value is::", value);
					bootbox.alert({
						size: 'medium',
						title: 'Information',
						message: 'You are going to perform operation on product ' + value
						});
					}
					else
					{
						checkbox.prop('checked', !checked);
					}
			}
		});
	});*/
	
	
	//-------------------------------------
	
let $adminProductsTable = $('#adminProductsTable');
	
	//execute the below code only where we have this table
	if($adminProductsTable.length)
	{
		let jsonUrl = window.contextRoot + '/json/data/admin/all/products';
		
		$adminProductsTable.DataTable({
			lengthMenu: [[10, 30, 50, -1],['10 Records','30 Records', '50 Records','ALL']],
			pageLength: 30,
			//data: products
			ajax: {
				url: jsonUrl,
				dataSrc: ''	//collection of names without any name
			},
			columns: [
				{
					data: 'id'
				},
				{
					data: 'code',
					bSortable: false,
					mRender: (data, type, row) => {
						return '<img src="' + window.contextRoot+'/resources/images/' + data + '.jpg" class="adminDataTableImg" />';
					}
				},
				{
					data: 'name'
				},
				{
					data: 'brand'
				},
				{
					data: 'quantity',
					mRender: (data,type,row) => {
						if(data<1)
						{
							return '<span style="color:red">Out of Stock!</span>';
						}
						return data;
					}
				},
				{
					data: 'unitPrice',
					mRender: (data, type, row) => '&euro; ' + data
				},
				{
					data: 'active',
					bSortable: false,
					mRender: (data, type, row) => {
						var str = '';
						str += '<label class="switch">';
						if(data)
						{
							str += '<input type="checkbox" checked="checked" value="' + row.id + '"/>';
						}
						else
						{
							str += '<input type="checkbox" value="' + row.id + '"/>';
						}
						str += '<div class="slider"></div></label>';
						return str;
					}
				},
				{
					data: 'id',
					bSortable: false,
					mRender: (data, type, row) => '<a href="' + window.contextRoot + '/manage/' + data + '/product" class="btn btn-warning"><i class="fas fa-pencil-alt"></i></a>'
				}
			],
			initComplete: function() {
				var api = this.api();
				api.$
				('.switch input[type="checkbox"]').on('change', function() {
					var checkbox = $(this);
					var checked = checkbox.prop('checked');
					var dMsg = (checked) ? 'You want to activate the product? ': 'You want to deactivate the product?';
					var value = checkbox.prop('value');
					
					bootbox.confirm({
						size: 'medium',
						title: 'Product Activation & Deactivation',
						message: dMsg,
						callback: function (confirmed) {
							if(confirmed)
							{
								//console.log("value is::", value);
								var activationUrl = window.contextRoot + '/manage/product/' + value + '/activation';
								$.post(activationUrl, function(data){
									bootbox.alert({
										size: 'medium',
										title: 'Information',
										//message: 'You are going to perform operation on product ' + value
										message: data
										});
								});
								}
								else
								{
									checkbox.prop('checked', !checked);
								}
						}
					});
				});
			}
		});
	}
	
	//-----------------------------------------
	//validation code for category form
	var $categoryForm = $("#categoryForm");
	if($categoryForm.length)
	{
		$categoryForm.validate({
			rules: {
				name: {
					required: true,
					minlength: 2
				},
				description: {
					required: true
				}
			},
			messages: {
				name: {
					required: 'Please add the category name!',
					minlength: 'The category name should be at least 2 characters'
				},
				description: {
					required: 'Please add a description for this category!'
				}
			},
			errorElement: 'em',
			errorPlacement: function (error, element) {
				//add the class of help-block
				error.addClass('help-block');
				//add the error element after the input element
				error.insertAfter(element);
			}
		})
	}
	
	//-----------------------------------------------
	//validation code for login form
	var $categoryForm = $("#loginForm");
	if($categoryForm.length)
	{
		$categoryForm.validate({
			rules: {
				username: {
					required: true,
					email: true
				},
				password: {
					required: true
				}
			},
			messages: {
				username: {
					required: 'Please enter the user name!',
					email: 'Please enter valid email address!'
				},
				password: {
					required: 'Please enter the password!'
				}
			},
			errorElement: 'em',
			errorPlacement: function (error, element) {
				//add the class of help-block
				error.addClass('help-block');
				//add the error element after the input element
				error.insertAfter(element);
			}
		})
	}
	
});