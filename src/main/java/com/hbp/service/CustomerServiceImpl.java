package com.hbp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbp.dao.CustomerDao;
import com.hbp.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerDao customerDao;

	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerDao.findAll();
	}

	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerDao.save(customer);
		
	}

	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerDao.save(customer);
	}

	public Customer getCustomerById(int custId) {
		// TODO Auto-generated method stub
		return customerDao.findOne(custId);
	}

	public void deleteCustomer(int custId) {
		// TODO Auto-generated method stub
		customerDao.delete(custId);
	}

	public boolean isUserExist(Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.exists(customer.getCustId());
	}

	public void deleteAllCustomers() {
		// TODO Auto-generated method stub
		customerDao.deleteAllInBatch();
	}

}
