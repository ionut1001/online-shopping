package net.ionut.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.ionut.shoppingbackend.dao.ProductDAO;
import net.ionut.shoppingbackend.dto.Product;

public class ProductTestCase 
{
	private static AnnotationConfigApplicationContext context;
	private static ProductDAO productDAO;
	private Product product;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("net.ionut.shoppingbackend");
		context.refresh();
		
		productDAO = (ProductDAO)context.getBean("productDAO");
		
	}

	
	@Test
	public void testCRUDProduct()
	{
		product = new Product();
		product.setName("oppo selfie xxx");
		product.setBrand("oppo");
		product.setDescription("desc for oopo mobile");
		product.setUnitPrice(1000.23);
		product.setActive(true);
		product.setCategoryId(3);
		product.setSupplierId(3);

		assertEquals("something went wrong when adding product in table", true, productDAO.add(product));
		
		//update
		product = productDAO.get(2);
		product.setName("Samsung Galaxy S7");
		assertEquals("something went wrong updating the product", true, productDAO.update(product));
		
		//delete
		product = productDAO.get(2);
		assertEquals("ssomething went wrong deleting the product", true, productDAO.delete(product));
		
		//fetching the list
		assertEquals("failed to fetch the products", 10, productDAO.list().size());
	}
	
	
	@Test
	public void testListActiveProducts()
	{
		assertEquals("failed to fetch the products", 9, productDAO.listActiveProducts().size());
	}
	
	@Test
	public void testListActiveProductsByCategory()
	{
		assertEquals("failed to fetch the products", 2, productDAO.listActiveProductsByCategory(1).size());
	}
	
	@Test
	public void testListLatestProducts()
	{
		assertEquals("failed to fetch the products", 3, productDAO.getLatestActiveProducts(3).size());
	}
}
