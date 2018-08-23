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
	default:
		if(menu=='Home') break;
		$('#listProducts').addClass('active');
		$('#a_' + menu).addClass('active');
		break;
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
						if(row.quantity<1)
						{
							str += '<a href="javascript: void(0)"><i class="fas fa-cart-plus" style="color: grey; cursor: not-allowed"></i></a>';
						}
						else
						{
							str += '<a href="' + window.contextRoot + '/cart/add/' + data + '/product"><i class="fas fa-cart-plus"></i></a>';							
						}
						return str;
					}
				}
			]
		});
	}
});