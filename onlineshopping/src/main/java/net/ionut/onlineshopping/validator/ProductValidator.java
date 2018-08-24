package net.ionut.onlineshopping.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import net.ionut.shoppingbackend.dto.Product;

public class ProductValidator implements Validator 
{

	@Override
	public boolean supports(Class<?> clazz) 
	{
		
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		Product product = (Product) target;
		
		//whether file has been selected or not
		if(product.getFile()==null || product.getFile().getOriginalFilename().equals(""))
		{
			errors.rejectValue("file", null, "Please select an image file to upload!");
			return;
		}
		
		String fileContentType = product.getFile().getContentType();	
		if(! ("image/jpeg".equals(fileContentType) || 
			  "image/png".equals(fileContentType)) ||
			  "image/gif".equals(fileContentType))
		{
			errors.rejectValue("file", null, "Please use only image file for upload!");
			return;
		}
	}

}
