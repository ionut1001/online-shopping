package net.ionut.shoppingbackend.dao;

import java.util.List;

import net.ionut.shoppingbackend.dto.Address;
import net.ionut.shoppingbackend.dto.Cart;
import net.ionut.shoppingbackend.dto.User;

public interface UserDAO 
{
	boolean addUser(User user);
	
	User getByEmail(String email);
	
	boolean addAddress(Address address);
	
	
	//ALTERNATIVE!!!, in order not to generate so many queries to get the User. Fetching an address it also fetching the User
	Address getBillingAddress(int userId);
	List<Address> listShippingAddresses(int userId);
	
	/*Address getBillingAddress(User user);
	List<Address> listShippingAddresses(User user);*/
	
	//boolean addCart(Cart cart); // removed the addCart, because cart is added automatically when user is created. Instead, i want only to update it
	boolean updateCart(Cart cart);
	
	
}
