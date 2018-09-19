package net.ionut.onlineshopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.ionut.shoppingbackend.dao.ProductDAO;
import net.ionut.shoppingbackend.dto.Product;

//@Controller
@Service
@RequestMapping("/json/data")
public class JsonDataController 
{
	@Autowired
	private ProductDAO productDAOImplX;
	
	@RequestMapping("/all/products")
	@ResponseBody	//receive response as JSON
	public List<Product> getAllProducts()
	{
		System.out.println("Get all products.....");
		return productDAOImplX.listActiveProducts();
	}
	
	
	@RequestMapping("/admin/all/products")
	@ResponseBody	//receive response as JSON
	public List<Product> getAllProductsForAdmin()
	{
		return productDAOImplX.list();
	}
	
	
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getAllProductsBtCategory(@PathVariable("id") int id)
	{
		return productDAOImplX.listActiveProductsByCategory(id);
	}
	
}
