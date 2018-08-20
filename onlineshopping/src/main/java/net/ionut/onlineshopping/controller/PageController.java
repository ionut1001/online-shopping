package net.ionut.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.ionut.shoppingbackend.dao.CategoryDAO;
import net.ionut.shoppingbackend.dto.Category;

@Controller
public class PageController 
{
	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping(value = {"/", "/home", "/index"})
	public ModelAndView index()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("greeting","welcome to Spring Web MVC");
		mv.addObject("title","Home");
		mv.addObject("userClickHome",true);
		
		//passing the list of categories from backend
		mv.addObject("categories", categoryDAO.list());
		return mv;
	}
	
	@RequestMapping(value = "/about")
	public ModelAndView about()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","About Us");
		mv.addObject("userClickAbout",true);
		return mv;
	}
	
	@RequestMapping(value = "/contact")
	public ModelAndView contact()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","Contact Us");
		mv.addObject("userClickContact",true);
		return mv;
	}
	
	/**
	 * methods to load all the products and based on categories
	 */
	@RequestMapping(value = "/show/all/products")
	public ModelAndView showAllProducts()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","All Products");
		mv.addObject("userClickAllProducts", true);
		
		//passing the list of categories from backend
		mv.addObject("categories", categoryDAO.list());
		System.out.println("All Products loaded");
		return mv;
	}
	
	@RequestMapping(value = "/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable(name="id") int id)
	{
		System.out.println("inside showCategoryProducts.... ID="+ id);
		ModelAndView mv = new ModelAndView("page");
		//categoryDAO to fetch a single category
		Category category = null;
		category = categoryDAO.get(id);
		mv.addObject("title", category.getName());
		mv.addObject("userClickCategoryProducts", true);
		mv.addObject("category", category);
		
		//passing the list of categories from backend
		mv.addObject("categories", categoryDAO.list());
		
		System.out.println("title=" + category.getName());
		return mv;
	}
	
	
	@RequestMapping(value = "/testX")
	public ModelAndView test1(@RequestParam(value = "greeting", required=false) String greeting)
	{
		if(greeting == null)
		{
			greeting = "default: Hello There!!! from method test1";
		}
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("greeting",greeting);
		return mv;
	}
	
	@RequestMapping(value = "/test/{greeting}")
	public ModelAndView test(@PathVariable("greeting") String greeting)
	{
		if(greeting == null)
		{
			greeting = "default: Hello There!!!";
		}
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("greeting",greeting);
		return mv;
	}
	
}
