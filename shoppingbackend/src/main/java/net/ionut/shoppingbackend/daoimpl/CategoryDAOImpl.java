package net.ionut.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.ionut.shoppingbackend.dao.CategoryDAO;
import net.ionut.shoppingbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

/*	private static List<Category> categories = new ArrayList<Category>();
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
	}*/
	
	@Override
	public List<Category> list() 
	{
		String selectActiveCategory = "FROM Category where active = :active";
		//String selectActiveCategory = "FROM Category";
		Query q = sessionFactory.getCurrentSession().createQuery(selectActiveCategory);//.setParameter("active", true);//.getResultList();
		q.setBoolean("active", true);
		return q.getResultList();
		
		//return sessionFactory.getCurrentSession().createQuery(selectActiveCategory).getResultList();
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
		
		//Category category =	categories.stream().filter(p -> p.getId()==id).findFirst().get();
		//System.out.println("Return category id from lambda:" + category.getId());
		//return category;

		return sessionFactory.getCurrentSession().get(Category.class, Integer.valueOf(id));
		
	}

	@Override
	@Transactional
	public boolean add(Category category) 
	{
		try
		{
			//add category to db table
			sessionFactory.getCurrentSession().persist(category);
			
			return true;
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Category category) 
	{
		try
		{
			//update single category to db table
			sessionFactory.getCurrentSession().update(category);
			
			return true;
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Category category) 
	{
		category.setActive(false);
		try
		{
			//update single category to db table
			sessionFactory.getCurrentSession().update(category);
			
			return true;
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	

}
