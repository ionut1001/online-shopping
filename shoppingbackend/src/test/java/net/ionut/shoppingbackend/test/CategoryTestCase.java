package net.ionut.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.ionut.shoppingbackend.dao.CategoryDAO;
import net.ionut.shoppingbackend.dto.Category;

public class CategoryTestCase 
{
	private static AnnotationConfigApplicationContext context;
	private static CategoryDAO categoryDAO;
	private Category category;
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("net.ionut.shoppingbackend");
		context.refresh();
		
		categoryDAO = (CategoryDAO)context.getBean("categoryDAO");
		
	}
	
	@Test
	public void testAddCategory()
	{
		category = new Category();
		category.setName("Laptop");
		category.setDescription("desc for laptop");
		category.setImageURL("CAT2.png");

		assertEquals("successfully added category in table", true, categoryDAO.add(category));
	}
	
	@Test
	public void testGetCategory()
	{
		category = categoryDAO.get(1);

		assertEquals("successfully fetched a single category from table", "Laptop", category.getName());
	}
	
	@Test
	public void testUpdateCategory()
	{
		category = categoryDAO.get(1);
		category.setName("TV");

		assertEquals("successfully updated a single category from table", true, categoryDAO.update(category));
	}
	
	@Test
	public void testDeleteCategory()
	{
		category = categoryDAO.get(1);

		assertEquals("successfully updated a single category from table", true, categoryDAO.delete(category));
	}
	
	@Test
	public void testListCategory()
	{
		assertEquals("successfully fetched list of categories from table", 1, categoryDAO.list().size());
	}
	
	@Test
	public void testCRUDCategory()
	{
		category = new Category();
		category.setName("Television");
		category.setDescription("desc for TV");
		category.setImageURL("CAT1.png");

		assertEquals("successfully added category in table", true, categoryDAO.add(category));
		
		category = new Category();
		category.setName("Laptop");
		category.setDescription("desc for laptop");
		category.setImageURL("CAT2.png");

		assertEquals("successfully added category in table", true, categoryDAO.add(category));
		
		
		//update
		category = categoryDAO.get(2);
		category.setName("TV");
		assertEquals("successfully updated a single category from table", true, categoryDAO.update(category));
		
		//delete
		category = categoryDAO.get(2);
		assertEquals("successfully updated a single category from table", true, categoryDAO.delete(category));
		
		//fetching the list
		assertEquals("successfully fetched list of categories from table", 1, categoryDAO.list().size());
	}
}
