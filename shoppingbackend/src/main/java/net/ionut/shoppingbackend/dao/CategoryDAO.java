package net.ionut.shoppingbackend.dao;

import java.util.List;

import net.ionut.shoppingbackend.dto.Category;


public interface CategoryDAO {
	
	List<Category> list();
	Category get(int id);

}
