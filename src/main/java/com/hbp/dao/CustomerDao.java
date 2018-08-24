package com.hbp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbp.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{

}
