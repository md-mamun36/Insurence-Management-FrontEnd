package com.insurence.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexControler {

	String aboutus = "";
	String products="";

		//About us tabs
	@RequestMapping("/history")
	public String history(Model m) {
		aboutus = "History Panel";
		m.addAttribute("tergate", aboutus);
		return "aboutus/history";
	}
	@RequestMapping("/info")
	public String information(Model m) {
		aboutus = "Information Panel";
		m.addAttribute("tergate",aboutus);
		return "aboutus/history";
	}
	@RequestMapping("/mision")
	public String mission(Model m) {
		aboutus = "Mission And Vision";
		m.addAttribute("tergate",aboutus);
		return "aboutus/history";
	}
	
	//conatact us
		@RequestMapping("/contact")
		public String Home(Model m) {
			aboutus = "Contact us";
			m.addAttribute("tergate",aboutus);
			return "aboutus/history";
		}
	
		//products tabs
	@RequestMapping("/life")
	public String Life(Model m) {
		this.products="Life Insurance";
		m.addAttribute("product",products);
		return"products/Products";
	}
	
	@RequestMapping("/health")
	public String Health(Model m) {
		this.products="Health Insurance";
		m.addAttribute("product",products);
		return"products/Products";
	}
	
	@RequestMapping("/child")
	public String Child(Model m) {
		this.products="Child Insurance";
		m.addAttribute("product",products);
		return"products/Products";
	}
	
	@RequestMapping("/hazz")
	public String Hazz(Model m) {
		this.products="Hazz Insurance";
		m.addAttribute("product",products);
		return"products/Products";
	}
	
	@RequestMapping("/pention")
	public String Pention(Model m) {
		this.products="Pention Insurance";
		m.addAttribute("product",products);
		return"products/Products";
	}
	
	@RequestMapping("/education")
	public String Education(Model m) {
		this.products="Education Insurance";
		m.addAttribute("product",products);
		return"products/Products";
	}
	
	@RequestMapping("/mohar")
	public String FourPay(Model m) {
		this.products="Denmahar Insurance";
		m.addAttribute("product",products);
		return"products/Products";
	}
	

}