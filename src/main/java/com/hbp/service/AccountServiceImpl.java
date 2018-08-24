
package com.hbp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbp.dao.AccountDao;
import com.hbp.model.Account;
@Service
public class AccountServiceImpl implements AccountService {
@Autowired
 private AccountDao accountDao; 
	public List<Account> getAllAccounts() {
		return null;
	}

	public void addAccount(Account account) {
		accountDao.save(account);
		
	}

	public void updateAccount(Account account) {
		accountDao.save(account);
		
	}

	public Account getAccountById(int acctId) {
		return  accountDao.findOne(acctId);
	}

	public void deleteAccount(int acctId) {
		accountDao.delete(acctId);
		
	}

	public void deleteAllAccounts() {
		accountDao.deleteAllInBatch();			
	}

	public boolean isUserExist(Account account) {
		return accountDao.exists(account.getAcctId());
	}

}
