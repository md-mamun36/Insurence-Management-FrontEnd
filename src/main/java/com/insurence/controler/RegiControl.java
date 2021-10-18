package com.insurence.controler;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.insurence.model.AdminModel;
import com.insurence.service.ServiceImpl;

@Controller
public class RegiControl {
	public boolean flag=true;
	String adminemail="mdmamun4372@gmail.com";
	String password="mamun2301";

	@Autowired(required = true)
	private ServiceImpl servimple;
	
	
	//for index page controler
	@RequestMapping("/")
	public String indexpage() {
		
		return "index";
	}
	
	
	
	//admin login form
	@RequestMapping("/adminlogin")
	public String loginform() {
		
		return "logform";
	}
	
		//admin login handler
	@PostMapping("/admin/adminloged")
	public String onlogin(@ModelAttribute("ma") AdminModel adm, Model m) {
		
		m.addAttribute("bol",flag);
		if((adminemail.equals( adm.getEmail())) && (password.equals( adm.getPassword()))) {

			return "adminpanel";
		}
		else
			
		return "logform";
	}
	
	
	
	
	//agent and Customer login form
	@RequestMapping("/signin")
	public String agentlogForm() {
		return "Agent/agentLogin";
	}
	

	
	//go to home
	@RequestMapping("/home")
	public String Home() {
		
		return "index";
	}
	
	
	//Calculate primium
	@PostMapping("/calculate")
	public String Calculate(@RequestParam("policytype")String pType,
			@RequestParam("Duration")int duration,
			@RequestParam("plan")String plan,
			@RequestParam("amount")int amount, Model m ){
		int prim;
		
		if(plan.equals("Yearly")) {
			 prim=(amount/duration);
			m.addAttribute("tk",prim);
		}
		else if(plan.equals("Half Yearly")) {
			 prim=(amount/(duration*2));
			 m.addAttribute("tk",prim);
		}
		else {
			m.addAttribute("tk","Pleaze Input Correcly");
		}
		
		return "calculator";
	}

}
