package com.hbp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbp.model.Account;

public interface AccountDao extends JpaRepository<Account, Integer>{

}
