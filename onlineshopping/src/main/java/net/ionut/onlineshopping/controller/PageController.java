package net.ionut.onlineshopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController 
{
	@RequestMapping(value = {"/", "/home", "/index"})
	public ModelAndView index()
	{
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("greeting","welcome to Spring Web MVC");
		return mv;
	}
	
	
	@RequestMapping(value = "/testX")
	public ModelAndView test1(@RequestParam(value = "greeting", required=false) String greeting)
	{
		if(greeting == null)
		{
			greeting = "default: Hello There!!! from method test1";
		}
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("greeting",greeting);
		return mv;
	}
	
	@RequestMapping(value = "/test/{greeting}")
	public ModelAndView test(@PathVariable("greeting") String greeting)
	{
		if(greeting == null)
		{
			greeting = "default: Hello There!!!";
		}
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("greeting",greeting);
		return mv;
	}
	
}