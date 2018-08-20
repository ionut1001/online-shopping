package net.ionut.shoppingbackend.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import net.ionut.shoppingbackend.dao.CategoryDAO;
import net.ionut.shoppingbackend.dto.Category;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

	private static List<Category> categories = new ArrayList<Category>();
	static {
		Category category = new Category();
		category.setId(1);
		category.setName("Television");
		category.setDescription("desc for TV");
		category.setImageURL("CAT1.png");
		categories.add(category);
		
		category = new Category();
		category.setId(2);
		category.setName("Mobile");
		category.setDescription("desc for mobile");
		category.setImageURL("CAT2.png");
		categories.add(category);
		
		category = new Category();
		category.setId(3);
		category.setName("Laptop");
		category.setDescription("desc for laptop");
		category.setImageURL("CAT3.png");
		categories.add(category);
	}
	
	@Override
	public List<Category> list() 
	{
		return categories;
	}

	@Override
	public Category get(int id) {
		/*for(Category category : categories)
		{
			if(category.getId()==id)
			{
				System.out.println("Return category id:" + category.getId());
				return category;
			}
		}
		System.out.println("Return category id: NULL!!!!");
		return null;*/
		
		Category category =	categories.stream().filter(p -> p.getId()==id).findFirst().get();
		System.out.println("Return category id from lambda:" + category.getId());
		return category;
	}
	
	

}
