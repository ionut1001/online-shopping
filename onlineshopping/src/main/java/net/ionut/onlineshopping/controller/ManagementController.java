package net.ionut.onlineshopping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.ionut.onlineshopping.exception.ProductNotFoundException;
import net.ionut.onlineshopping.util.FileUploadUtility;
import net.ionut.onlineshopping.validator.ProductValidator;
import net.ionut.shoppingbackend.dao.CategoryDAO;
import net.ionut.shoppingbackend.dao.ProductDAO;
import net.ionut.shoppingbackend.dto.Category;
import net.ionut.shoppingbackend.dto.Product;

@Controller
@RequestMapping("/manage")
public class ManagementController 
{
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);
	private String lastProductAddedCode;
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name="operation", required=false) String operation)
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");
		Product nProduct = new Product();
		nProduct.setSupplierId(1);
		nProduct.setActive(true);
		mv.addObject("product", nProduct);
		
		if("product".equals(operation))
		{
			mv.addObject("messageSuccess", "Product with code '" + nProduct.getCode() + "' submitted successfully!");
		}
		else if("category".equals(operation))
		{
			mv.addObject("messageSuccess", "Category submitted successfully!");
		}
		
		return mv;
	}
	
	//handling product submission
	@RequestMapping(value="/products", method=RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mProduct,
											BindingResult results, Model model, HttpServletRequest request)
	{
		//handle image validation for new products only. And if image is selected i want to verify it is a real image-type
		if(mProduct.getId() == 0)
		{
			new ProductValidator().validate(mProduct, results);
		}
		else
		{
			if(!"".equals(mProduct.getFile().getOriginalFilename())) 
			{
				new ProductValidator().validate(mProduct, results);
			}
		}
		
		//check if there are any errors
		if(results.hasErrors())
		{
			model.addAttribute("userClickManageProducts", true);
			model.addAttribute("title", "Manage Products");
			model.addAttribute("messageFail", "Validation failed for Product submission!");
			
			//pass the data to the page, and do not redirect
			return "page";
		}
		
		if(mProduct.getId()==0)
		{
			productDAO.add(mProduct);
		}
		else
		{
			productDAO.update(mProduct);
		}
		
		//create a new product record
		productDAO.add(mProduct);
		lastProductAddedCode = mProduct.getCode();
		if(!"".equals(mProduct.getFile().getOriginalFilename()))
		{
			FileUploadUtility.uploadFile(request, mProduct.getFile(), mProduct.getCode());
		}
		
		logger.info(mProduct.toString() + " was added!");
		
		return "redirect:/manage/products?operation=product";
		//return "redirect:/show/all/products";
	}
	
	/** to handle category submission */
	@RequestMapping(value="/category", method=RequestMethod.POST)
	public String handleCategorySubmission(@ModelAttribute Category category)
	{
		categoryDAO.add(category);
		return "redirect:/manage/products?operation=category";
	}
	
	
	@RequestMapping(value="/product/{id}/activation", method=RequestMethod.POST)
	@ResponseBody
	public String handleProductActivation(@PathVariable  int id)
	{
		Product product = productDAO.get(id);
		boolean isActive = product.isActive();
		product.setActive(!isActive);
		productDAO.update(product);
		return isActive ? "You have successfully deactivated the product with ID: " + product.getId() :
						   "You have successfully activated the product with ID: " + product.getId();
	}
	
	
	
	
	@RequestMapping(value="/{id}/product", method=RequestMethod.GET)
	public ModelAndView showEditProducts(@PathVariable("id") int id) throws ProductNotFoundException
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");
		Product product = productDAO.get(id);
		if(product == null)
		{
			throw new ProductNotFoundException(id);
		}
		mv.addObject("product", product);	
		
		return mv;
	}
	
	
	@ModelAttribute("categories")
	public List<Category> getCategories()
	{
		return categoryDAO.list();
	}
	
	@ModelAttribute("category")
	public Category getCategory()
	{
		return new Category();
	}
}
