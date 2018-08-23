package net.ionut.onlineshopping.exception;

import java.io.Serializable;

public class ProductNotFoundException extends Exception implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private int id;
	
	public ProductNotFoundException()
	{
		this("Product is not available");
	}
	
	public ProductNotFoundException(String message)
	{
		this.message = System.currentTimeMillis() + ": " + message;
	}
	
	public ProductNotFoundException(int id)
	{
		this.id = id;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public int getProductId()
	{
		return id;
	}
}
