package com.insurence.controler;





import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insurence.model.AdminModel;

@Controller
public class RegiControl {
	public boolean flag=true;
	String adminemail="mdmamun4372@gmail.com";
	String password="mamun2301";

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
	@PostMapping("/adminloged")
	public String onlogin(@ModelAttribute("ma") AdminModel adm, Model m) {
		
		m.addAttribute("bol",flag);
		if((adminemail.equals( adm.getEmail())) && (password.equals( adm.getPassword()))) {
			
			
			return "adminpanel";
			
			
		}
		else
			
		return "logform";
	}
	
	//agent login form
	@RequestMapping("/agentlogForm")
	public String agentlogForm() {
		return "agentLogin";
	}
	
		//agent Login Handler
	@PostMapping("/agentloged")
	public String agentLogin() {
		
		return "agentPanel";
	}
	
	//User login form
	@RequestMapping("/userlogForm")
	public String userlogForm() {
		
		return "userLogin";
	}
	
	//User Login Handler
	@PostMapping("/userLogin")
	public String userLogin() {
		
		return "userPanel";
	}
	
	//go to home
	@RequestMapping("/home")
	public String Home() {
		
		return "index";
	}
	
	
	//for logout
	@RequestMapping("/logou")
	public String logou(@ModelAttribute("ma") AdminModel adm,Model m) {

		adm.setEmail("");
		adm.setPassword("");
	
		return "index";
	}
}
