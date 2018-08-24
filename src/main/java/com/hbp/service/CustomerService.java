package com.hbp.service;

import java.util.List;

import com.hbp.model.Customer;

public interface CustomerService {

	public List<Customer> getAllCustomers();
	public void addCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public Customer getCustomerById(int custId);
	public void deleteCustomer(int custId);
	public boolean isUserExist(Customer customer);
	public void deleteAllCustomers();
	
}
