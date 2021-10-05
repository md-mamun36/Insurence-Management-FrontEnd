package com.insurence.service;

import com.insurence.model.AgentModel;
import com.insurence.model.CustomerModel;

public interface DataService {

	//agent jpa service
	AgentModel[] getAllAgent();
    void saveAgent(AgentModel agent);
    AgentModel getAgentModelById(int id);
    void deleteAgentModeById(int id);
    void updateAgent(AgentModel agent, int id);
    
    
    //Customer jpa service
    CustomerModel[] getAllCustomer();
    void saveCustomer(CustomerModel customer);
    CustomerModel getCustomerModelByCustid(int id);
    void deleteCustomerModelByCustid(int id);
    void updateCustomer(CustomerModel customer, int id);
}
