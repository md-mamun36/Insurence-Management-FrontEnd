package com.insurence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.insurence.model.AgentModel;
import com.insurence.model.CustomerModel;



@Service
public class ServiceImpl implements DataService{
	
	@Autowired
    private RestTemplate restTemplate;
	
	private final String agent_URL="http://localhost:1003/api/agent" ;
	private final String cust_URL="http://localhost:1003/api/customer" ;

	@Override
	public AgentModel[] getAllAgent() {
		AgentModel[] allagent = this.restTemplate.getForObject(agent_URL, AgentModel[].class);
		return allagent;
	}
	
	

	@Override
	public void saveAgent(AgentModel agent) {
		this.restTemplate.postForObject(agent_URL, agent, AgentModel.class);
		
	}

	
	@Override
	public void deleteAgentModeById(int id) {
		String url=agent_URL+"/{id}";
		this.restTemplate.delete(url,id);
		
	}

	@Override
	public void updateAgent(AgentModel agent, int id) {
		String url=agent_URL+"/{id}";
		this.restTemplate.put(url, agent,id);
		
	}


	@Override
	public AgentModel getAgentModelById(int id) {
		String url=agent_URL+"/{id}";
		AgentModel forObject = this.restTemplate.getForObject(url, AgentModel.class,id);
		return forObject;
	}


	

	//Customer service
	@Override
	public CustomerModel[] getAllCustomer() {
		 CustomerModel[] forObject = this.restTemplate.getForObject(cust_URL, CustomerModel[].class);
		return forObject;
	}



	@Override
	public void saveCustomer(CustomerModel cust) {
		 this.restTemplate.postForObject(cust_URL, cust, CustomerModel.class);
		
	}



	@Override
	public CustomerModel getCustomerModelByCustid(int id) {
		String url=cust_URL +"/{id}";
		CustomerModel getcustomr = this.restTemplate.getForObject(url, CustomerModel.class,id);
		return getcustomr;
	}



	@Override
	public void deleteCustomerModelByCustid(int id) {
		String url=cust_URL +"/{id}";
		this.restTemplate.delete(url,id);
		
	}



	@Override
	public void updateCustomer(CustomerModel cust, int id) {
		String url=cust_URL +"/{id}";
		this.restTemplate.put(url, cust,id);
		
	}

	


}
