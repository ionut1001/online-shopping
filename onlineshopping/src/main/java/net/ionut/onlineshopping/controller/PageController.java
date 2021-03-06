package net.ionut.onlineshopping.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.ionut.onlineshopping.exception.ProductNotFoundException;
import net.ionut.shoppingbackend.dao.CategoryDAO;
import net.ionut.shoppingbackend.dao.ProductDAO;
import net.ionut.shoppingbackend.dto.Category;
import net.ionut.shoppingbackend.dto.Product;


@Controller
public class PageController 
{
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value = {"/", "/home", "/index"})
	public ModelAndView index()
	{
		logger.info("Inside PageController index method - INFO");
		logger.debug("Inside PageController index method - DEBUG");

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
	
	
	/**
	 * Viewing a single product
	 */
	
	@RequestMapping("/show/{id}/product")
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException
	{
		ModelAndView mv = new ModelAndView("page");
		Product product = productDAO.get(id);
		if(product == null)
		{
			throw new ProductNotFoundException(id);
		}
		
		//update view count
		product.setViews(product.getViews()+1);
		productDAO.update(product);
		
		mv.addObject("title", product.getName());
		mv.addObject("product", product);
		mv.addObject("userClickShowProduct", true);
		
		
		return mv;
	}
	
	
	@RequestMapping(value = "/register")
	public ModelAndView register()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title","Register");
		return mv;
	}
	
	
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(name="error", required=false) String error, 
							  @RequestParam(name="logout", required=false) String logout)
	{
		ModelAndView mv = new ModelAndView("login");
		if(error!=null)
		{
			mv.addObject("message", "Invalid Username and Password!");
		}
		
		if(logout!=null)
		{
			mv.addObject("logoutMsg", "User has successfully logout!");
		}
		mv.addObject("title","Login");
		System.out.println("Login page....");
		return mv;
	}
	
	
	@RequestMapping(value = "/access-denied")
	public ModelAndView accessDenied()
	{
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("title","403 - Access Denied");
		mv.addObject("errorTitle","Aha! Cought you!");
		mv.addObject("errorDescription","You are not authorized to view this page!");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/perform-logout")
	public String logout(HttpServletRequest request, HttpServletResponse response)
	{
		//first fetch the authentication
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null)
		{
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		return "redirect:/login?logout";
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
