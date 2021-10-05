package com.insurence.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.insurence.model.AgentModel;
import com.insurence.model.CustomerModel;
import com.insurence.service.ServiceImpl;

@Controller
public class AdminControler {

	String terget="";
	
	@Autowired(required = true)
	private ServiceImpl servimple;
	
	
	//Show  Agents registration form
	@RequestMapping("/addagent")
	public String addagent() {
		
		return "AgentForm";
	}
	
	//back from agents registration form
	@RequestMapping("/back")
	public String backt() {
		terget="";
		return "adminpanel";
	}
	
	//Add a new Agents
	@PostMapping("/saveAgent")
	public String saveAgent(@ModelAttribute("agent") AgentModel agent ) {
		
		this.servimple.saveAgent(agent);
		System.out.println(agent);
		
		return "adminpanel";
	}
	
	
	//get All agents info
	@GetMapping("/agentdetails")
	public String showAgent(Model m) {
		terget="Agent Details";
		
		AgentModel[] allAgent = this.servimple.getAllAgent();
		m.addAttribute("allagent", allAgent);
		m.addAttribute("terget", terget);
		
		return "adminpanel";
	}
	
	
	
	//for active agent
	@GetMapping("/setactive/{id}")
	public String Active(@PathVariable("id") int id, Model m) {
	
		AgentModel agentModelById = this.servimple.getAgentModelById(id);
		agentModelById.setPosition("Active");
		this.servimple.saveAgent(agentModelById);
		m.addAttribute(agentModelById);
		return "redirect:/agentdetails";
	}
	
	//for deactive agent
	@GetMapping("/deactive/{id}")
	public String Dective(@PathVariable("id") int id, Model m) {
	
		AgentModel agentModelById = this.servimple.getAgentModelById(id);
		agentModelById.setPosition("DeActive");
		this.servimple.saveAgent(agentModelById);
		m.addAttribute(agentModelById);
		return "redirect:/agentdetails";
	}
	
	//getting pending account
	@GetMapping("/pending")
	public String pendingCustomer(Model m) {
		terget="Pending Customers";
		m.addAttribute("terget", terget);
 CustomerModel[] allCustomer = this.servimple.getAllCustomer();
		m.addAttribute("allcustomer",allCustomer);
	
		return "adminpanel";
	}
	
	//to get running customers 
	@GetMapping("/custinfo")
	public String customerdetails(Model m) {
		terget="Customer Details";
		m.addAttribute("terget", terget);
 CustomerModel[] allCustomer = this.servimple.getAllCustomer();
		m.addAttribute("allcustomer",allCustomer);
	
		return "adminpanel";
	}
	
	
	//to accept pending custome
	@GetMapping("/accept/{id}")
	public String Accept(@PathVariable("id") int id, Model m) {
	
		CustomerModel Custid = this.servimple.getCustomerModelByCustid(id);
		Custid.setPosition("running");
		this.servimple.saveCustomer(Custid);
		m.addAttribute(Custid);
		return "redirect:/pending";
	}
	
	//to cencel pending customer
		@GetMapping("/cencel/{id}")
		public String Cencel(@PathVariable("id") int id) {
			this.servimple.deleteCustomerModelByCustid(id);
			return "redirect:/pending";
		}
		
		//agent search
		@GetMapping("/search")
		public String SearchAgents(@RequestParam("sarc") String id,Model m) {
			int nid=0;
			if(id=="") {
				terget="Bad Request! Due to Empty value";
				m.addAttribute("terget",terget);
				return "adminpanel";
			}
			else
				nid=Integer.parseInt(id);
		
			
			terget="Searching Result";
			m.addAttribute("terget",terget);
			AgentModel sr = this.servimple.getAgentModelById(nid);
			m.addAttribute("sr",sr);
			return "adminpanel";

		}
		
		
		//customer search
		@GetMapping("/searchCustomer")
		public String SearchCustomer(@RequestParam("sarc") String id,Model m) {
			int nid=0;
			if(id=="") {
				terget="Bad Request! Due to Empty value";
				m.addAttribute("terget",terget);
				return "adminpanel";
			}
			else
				nid=Integer.parseInt(id);
		
			
			terget="Searching Customer Result";
			m.addAttribute("terget",terget);
			 CustomerModel Custid = this.servimple.getCustomerModelByCustid(nid);
			m.addAttribute("csr",Custid);
			return "adminpanel";

		}
	
}

