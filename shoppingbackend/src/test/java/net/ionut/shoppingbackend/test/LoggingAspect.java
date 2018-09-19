package net.ionut.shoppingbackend.test;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect 
{
	@org.aspectj.lang.annotation.Before("execution(*.testGetCategory())")
	public void getAllAdvice()
	{
		System.out.println("logging method....");
	}
}
