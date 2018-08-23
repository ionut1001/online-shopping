package net.ionut.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.ionut.shoppingbackend.dao.ProductDAO;
import net.ionut.shoppingbackend.dto.Product;

@Repository("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO
{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Product get(int productId) 
	{
		try {
			return sessionFactory.getCurrentSession().get(Product.class, Integer.valueOf(productId));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> list() 
	{
		return sessionFactory.getCurrentSession().createQuery("FROM Product", Product.class).getResultList();
	}

	@Override
	public boolean add(Product product) 
	{
		try
		{
			sessionFactory.getCurrentSession().persist(product);
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Product product) 
	{
		try
		{
			sessionFactory.getCurrentSession().update(product);
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Product product) 
	{
		product.setActive(false);
		return update(product);
	}

	@Override
	public List<Product> listActiveProducts() 
	{
		String selectActiveProducts = "FROM Product where active = :active";
		return sessionFactory.getCurrentSession().createQuery(selectActiveProducts, Product.class)
				.setParameter("active", true).getResultList();
	}

	@Override
	public List<Product> listActiveProductsByCategory(int categoryId) 
	{
		String selectActiveProductsByCategory = "FROM Product where active = :active and categoryId = :categoryID";
		return sessionFactory.getCurrentSession().createQuery(selectActiveProductsByCategory, Product.class)
				.setParameter("active", true)
				.setParameter("categoryID", categoryId)
				.getResultList();
	}

	@Override
	public List<Product> getLatestActiveProducts(int count) 
	{
		String selectlatestActive = "FROM Product where active = :active order by id";
		return sessionFactory.getCurrentSession().createQuery(selectlatestActive, Product.class)
				.setParameter("active", true)
				.setFirstResult(0)
				.setMaxResults(count)
				.getResultList();
	}

}
