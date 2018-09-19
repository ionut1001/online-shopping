package net.ionut.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.ionut.shoppingbackend.dao.UserDAO;
import net.ionut.shoppingbackend.dto.Address;
import net.ionut.shoppingbackend.dto.Cart;
import net.ionut.shoppingbackend.dto.User;

public class UserTestCase_NoRelations 
{
	private static AnnotationConfigApplicationContext context;
	private static UserDAO userDAO;
	private User user;
	private Address address;
	private Cart cart;
	
	
	@BeforeClass
	public static void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("net.ionut.shoppingbackend");
		context.refresh();
		
		userDAO = (UserDAO) context.getBean("userDAO");
	}
	
	@Test
	public void testAdd()
	{
		user = new User();
		user.setFirstName("ionut");
		user.setLastName("liv");
		user.setEmail("ionut@amd.com");
		user.setContactNumber("1234512345");
		user.setRole("USER");
		user.setPassword("123456");
		
		assertEquals("Failed to add user", true, userDAO.addUser(user));
		
		address = new Address();
		address.setAddressLineOne("maritime center");
		address.setAddressLineTwo("block A");
		address.setCity("Limassol");
		address.setState("Limassol state");
		address.setCountry("Cyprus");
		address.setPostalCode("3030");
		address.setBilling(true);
		address.setUserId(user.getId());
		
		assertEquals("Failed to add address", true, userDAO.addAddress(address));
		
		if(user.getRole().equals("USER"))
		{
			//create cart and add a shipping address
			cart = new Cart();
			//cart.setUserId(user.getId());
			
			//assertEquals("Failed to add cart", true, userDAO.addCart(cart));
			
			address = new Address();
			address.setAddressLineOne("alfeiou");
			address.setAddressLineTwo("12A");
			address.setCity("Limassol");
			address.setState("Limassol state");
			address.setCountry("Cyprus");
			address.setPostalCode("3060");
			address.setShipping(true);
			address.setUserId(user.getId());
			
			assertEquals("Failed to add shipping address", true, userDAO.addAddress(address));
			
		}
	}
	
	
}
