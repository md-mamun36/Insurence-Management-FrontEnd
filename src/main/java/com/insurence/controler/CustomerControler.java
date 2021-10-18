package com.insurence.controler;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.insurence.model.AgentModel;
import com.insurence.model.CustomerModel;
import com.insurence.service.ServiceImpl;

@Controller
@RequestMapping("/customer")
public class CustomerControler {

//changeble variable in different condition	
	String terget="";
	String	terget2="";
	
	
	
	
//Service imple
	@Autowired(required = true)
	private ServiceImpl servimple;
	
	
	

	
//to Create Common Attribute for all Method
	@ModelAttribute
	public void addCommonData(Model m,Principal princpl) {
		String name = princpl.getName();
		CustomerModel byEmail = this.servimple.customerByEmail(name);
		m.addAttribute("id", byEmail.getCustid());
	}
	
	
	
	
//To Customer Login	
	@RequestMapping("/customerlogin")
	public String customerlogin() {
		
		return "Customer/userPanel";
	}
	
	
	
	
	
//getting own all Data
	@RequestMapping("/manage/{id}")
	public String customerAccount(@PathVariable("id") int id,Model m,Principal princpl) {
	CustomerModel customerModel = this.servimple.getCustomerModelByCustid(id);
	if(customerModel.getPosition().equals("pending")) {
		terget="Sorry";
		m.addAttribute("terget",terget);	
	}
	else{
		terget2=customerModel.getCustname();
		m.addAttribute("mycustomer",customerModel);
		m.addAttribute("terget2",terget2);
	}	
		return "Customer/userPanel";
	}

	
	
	
	
//customer edit Form
	@RequestMapping("/edit/{id}")
	public String editCustomer(@PathVariable("id") int id, Model m,Principal princpl) {
		CustomerModel customer = this.servimple.getCustomerModelByCustid(id);
		String position = customer.getPosition();	
		if(position.equals("running")) {			
			m.addAttribute("customer",customer);
			return "Customer/CustUpdateForm";
		}	
		else
			m.addAttribute("none","Sorry! You are not a running Customer. So You can't Edit any Details.");
			return "Customer/userPanel";
	}
	
	
	
	
	
//customer update controler
	@PostMapping("/update")
	public String onUpdate(@ModelAttribute("customer") CustomerModel customer,Model m,Principal princpl) {
			this.servimple.updateCustomer(customer, customer.getCustid());
			terget2=customer.getCustname();
			m.addAttribute("mycustomer",customer);
			m.addAttribute("terget2",terget2);
		return "Customer/userPanel";
		
	}
	
	
	
	
	
//return from update form
	@RequestMapping("/return/{id}")
	public String reback(@PathVariable("id") int id, Model m,Principal princpl) {
		return"redirect:/customer/manage/{id}";
	}
	
	
	
	
//Add primium form
	@RequestMapping("/addprim/{id}")
	public String addPrimium(@PathVariable("id") int id,Model m,Principal princpl) {
		terget="addPrim";
		CustomerModel ByCustid = this.servimple.getCustomerModelByCustid(id);
		String position = ByCustid.getPosition();
		if(position.equals("pending")) {
			terget="Sorry";
			m.addAttribute("terget",terget);
		}
		
		else if(position.equals("DethClaim")) {
			m.addAttribute("DethClaim","Sorry! You already request for DethClaime. So you can't add Primium");
		}
		
		else if(position.equals("Claim")) {
			m.addAttribute("Claim","Sorry! You already request for Claim Policy. So you can't add Primium");
		}
		
		else if(position.equals("disclosed")) {
			m.addAttribute("disclosed","Sorry! Your Account is already Disclosed. So you can't add Primium");
		}
		else {
		m.addAttribute("cid",ByCustid.getCustid());
		m.addAttribute("cname",ByCustid.getCustname());
		m.addAttribute("policy",ByCustid.getPolicytype());
		m.addAttribute("agid",ByCustid.getAgent().getId());
		m.addAttribute("terget",terget);
		}
		return"Customer/userPanel";
	}

	
	
	
	
//primium Submitted Form
	@PostMapping("/payedprimium")
	public String payedprimium(@RequestParam("custid") int cid,@RequestParam("agid") int agid,
			@RequestParam("payedtk") int primium,Model m,Principal princpl) {
		
		terget="addPrim";
		m.addAttribute("terget",terget);
		//get Customer
		CustomerModel customer = this.servimple.getCustomerModelByCustid(cid);
		
		//get agent
		AgentModel agent = this.servimple.getAgentModelById(agid);
		
		//find Customer Policy Account 
		int policyAmount = customer.getPolicyAmount();
		
		//find Folicy Duration
		int duration = customer.getDuration();
		String position = customer.getPosition();
		
		//customer Account
		double account = customer.getAccount();
		
		//payment Method
		String prim_method = customer.getPrim_method();
		int tk;
		
		//get agent Bonus
		double primium_bonus = agent.getPrimium_bonus();
		double bonus= (primium/100)*3;
		if(account>=policyAmount ) {
			m.addAttribute("msg1","Your Policy was Complieted! Please  Claim it");
		}
		
		else {
			//if policy type Half yearly
		if(prim_method.equals("Half Yearly")) {
		 tk=(policyAmount/(duration*2))	;
		if(primium==tk) {
			customer.setAccount(account+tk);
			customer.setPayed_prim(customer.getPayed_prim()+1);
			agent.setPrimium_bonus(primium_bonus+bonus);
			this.servimple.saveCustomer(customer);
			this.servimple.saveAgent(agent);
			m.addAttribute("msg1","Your Primium is Successfully Receved!");
				}
		else {
			m.addAttribute("msg1","You must be Paid "+tk+" tk. only");
			m.addAttribute("cid",cid);
			m.addAttribute("cname",customer.getCustname());
			m.addAttribute("policy",customer.getPolicytype());
			m.addAttribute("agid",agid);
				}
			}
		
		//if policy type is yearly
		else if(prim_method.equals("Yearly")) {
			tk=(policyAmount/duration);
			
			if(primium==tk) {
				customer.setAccount(account+tk);
				customer.setPayed_prim(customer.getPayed_prim()+1);
				agent.setPrimium_bonus(primium_bonus+bonus);
				this.servimple.saveCustomer(customer);
				this.servimple.saveAgent(agent);	
				m.addAttribute("msg1","Your Primium is Successfully Receved!");
					}
			else {
				m.addAttribute("msg2","You must be Paid "+tk+" tk. only");
				m.addAttribute("cid",cid);
				m.addAttribute("cname",customer.getCustname());
				m.addAttribute("policy",customer.getPolicytype());
				m.addAttribute("agid",agid);
				}
			}
		}
		return"Customer/userPanel";
	}
	
	
	

	
	
	
//Policy Finished Claime
	@RequestMapping("/claim/{id}")
	public String claim(@PathVariable("id") int id, Model m,Principal princpl) {
		CustomerModel customer = this.servimple.getCustomerModelByCustid(id);
		double account =(int) customer.getAccount();
		int policyAmount = customer.getPolicyAmount();
		String position = customer.getPosition();
		if(position.equals("disclosed")) {
			m.addAttribute("claim","no");
			m.addAttribute("claim2"," Sorry! Your Policy was already discolesed! ");
		}
		else if(position.equals("DethClaim")) {
			m.addAttribute("claim","no");
			m.addAttribute("claim2"," Sorry! You already Request for DethClaim! ");
		}
		else if(account>=policyAmount) {
			customer.setPosition("Claim");
			this.servimple.saveCustomer(customer);
			m.addAttribute("claim","yes");
			m.addAttribute("claim1","Your Claime Request was sent. Plz Contact to admin ");
		}
		else {
			m.addAttribute("claim","no");
			m.addAttribute("claim2"," Sorry! Your Policy couldn't finished yet. So You Can't claime it right now.");
		}	
		return"Customer/userPanel";
	}
	
	
	
	
	
//Policy Deth Claime
	@RequestMapping("/dethclaim/{id}")
	public String dethclaim(@PathVariable("id") int id, Model m,Principal princpl) {
		CustomerModel customer = this.servimple.getCustomerModelByCustid(id);
		double account =(int) customer.getAccount();
		int policyAmount = customer.getPolicyAmount();
		String position = customer.getPosition();
		int payed_prim = customer.getPayed_prim();
		
		if(position.equals("disclosed")) {
			m.addAttribute("claim","no");
			m.addAttribute("claim2"," Sorry! Your Policy was already discolesed! ");
		}
		
		else if(position.equals("Claim")) {
			m.addAttribute("claim","no");
			m.addAttribute("claim2"," Sorry! You already Request for Claim Policy! ");
		}
		else if(payed_prim>=2) {
			customer.setPosition("DethClaim");
			this.servimple.saveCustomer(customer);
			m.addAttribute("claim","yes");
			m.addAttribute("claim1","Your Request was sent. Plz Contact to admin along with Proper Document");
		}
		else {
			m.addAttribute("claim","no");
			m.addAttribute("claim2"," Sorry! Your Policy hasn't proper value. So You Can't claime it");
		}
		return"Customer/userPanel";
	}
	
	
	
	
	
//to back prom any path
	@RequestMapping("/back")
	public String back(Model m,Principal princpl){
		
		return"Customer/userPanel";
	}
	
}
