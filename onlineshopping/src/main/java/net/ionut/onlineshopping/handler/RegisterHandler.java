package net.ionut.onlineshopping.handler;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import net.ionut.onlineshopping.model.RegisterModel;
import net.ionut.shoppingbackend.dao.UserDAO;
import net.ionut.shoppingbackend.dto.Address;
import net.ionut.shoppingbackend.dto.Cart;
import net.ionut.shoppingbackend.dto.User;

@Component
public class RegisterHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public RegisterModel init()
	{
		return new RegisterModel();
	}
	
	public void addUser(RegisterModel registerModel, User user)
	{
		registerModel.setUser(user);
	}
	
	public void addBilling(RegisterModel registerModel, Address billing)
	{
		registerModel.setBilling(billing);
	}
	
	public String saveAll(RegisterModel registerModel)
	{
		String transitionValue = "success";
		
		//fetch the user
		User user = registerModel.getUser();
		if("USER".equals(user.getRole()))
		{
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
			
		}
		
		//encode the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//save the user
		userDAO.addUser(user);
		
		//get the address
		Address billing = registerModel.getBilling();
		billing.setUserId(user.getId());
		billing.setBilling(true);
		
		//save the address
		userDAO.addAddress(billing);
		
		
		return transitionValue;
	}
	
	public String validateUser(User user, MessageContext error)
	{
		String transitionValue = "success";
		//check if passwords match
		
		if(!user.getPassword().equals(user.getConfirmPassword()))
		{
			error.addMessage(new MessageBuilder()
							.error()
							.source("confirmPassword")
							.defaultText("Password does not match a confirm password!")
							.build());
			transitionValue = "failure";
		}
		
		//check email uniqueness
		if(userDAO.getByEmail(user.getEmail())!=null)
		{
			error.addMessage(new MessageBuilder()
					.error()
					.source("email")
					.defaultText("Email address is already used!")
					.build());
			transitionValue = "failure";
		}
		
		return transitionValue;
	}
	
}
