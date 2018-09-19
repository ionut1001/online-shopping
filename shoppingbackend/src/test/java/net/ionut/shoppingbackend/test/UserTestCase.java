package net.ionut.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.ionut.shoppingbackend.dao.UserDAO;
import net.ionut.shoppingbackend.dto.Address;
import net.ionut.shoppingbackend.dto.Cart;
import net.ionut.shoppingbackend.dto.User;

public class UserTestCase 
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
	
/*	@Test
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
			cart.setUser(user);
			
			assertEquals("Failed to add cart", true, userDAO.addCart(cart));
			
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
	}*/
	
	
	@Test
	public void testAdd_2()
	{
		user = new User();
		user.setFirstName("ionut");
		user.setLastName("liv");
		user.setEmail("ionut@amd.com");
		user.setContactNumber("1234512345");
		user.setRole("USER");
		user.setPassword("123456");
		
		if(user.getRole().equals("USER"))
		{
			//create cart for this user
			cart = new Cart();
			cart.setUser(user);
			
			//attach cart with the user
			user.setCart(cart);
		}

		//add the user
		assertEquals("Failed to add user", true, userDAO.addUser(user));
	}
	
	@Test
	public void testUpdateCart()
	{
		//fetch the user by email
		user = userDAO.getByEmail("ionut@amd.com");
		
		//get cart of the user
		cart = user.getCart();
		
		cart.setGrandTotal(5355);
		cart.setCartLines(2);
		
		assertEquals("Failed to update the cart", true, userDAO.updateCart(cart));
		
	}
	
	
/*	@Test
	public void testAddAddress()
	{
		//add user, then add billing address, then add shipping address
		
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
		
		address.setUser(user);
		
		assertEquals("Failed to add billing address", true, userDAO.addAddress(address));
		
		address = new Address();
		address.setAddressLineOne("alfeiou");
		address.setAddressLineTwo("12A");
		address.setCity("Limassol");
		address.setState("Limassol state");
		address.setCountry("Cyprus");
		address.setPostalCode("3060");
		address.setShipping(true);
		address.setUser(user);
		assertEquals("Failed to add shipping address", true, userDAO.addAddress(address));
	}*/
	
	@Test
	public void testAddAddress_2()
	{
		user = userDAO.getByEmail("ionut@amd.com");
		
		address = new Address();
		address.setAddressLineOne("paun2");
		address.setAddressLineTwo("barnova2");
		address.setCity("iasi2");
		address.setState("iasi state2");
		address.setCountry("Romania2");
		address.setPostalCode("756542");
		address.setShipping(true);

		//address.setUser(user);
		address.setUserId(user.getId());

		assertEquals("Failed to add shipping address", true, userDAO.addAddress(address));

	}
	
	@Test
	public void testGetAddresses()
	{
		user = userDAO.getByEmail("ionut@amd.com");
		
		assertEquals("Failed to fetch list of addresses and size dont match", 3, userDAO.listShippingAddresses(user.getId()).size());
		
		assertEquals("Failed to fetch billing addresses and size dont match", "3030", userDAO.getBillingAddress(user.getId()).getPostalCode());
	}
	
}
