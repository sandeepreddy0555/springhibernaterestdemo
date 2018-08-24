package com.hbp.service;
import java.util.List;

import com.hbp.model.Account;
import com.hbp.model.Customer;



public interface AccountService {
		public List<Account> getAllAccounts();
		public void addAccount(Account account);
		public void updateAccount(Account account);
		public Account getAccountById(int acctId);
		public void deleteAccount(int actstId);
		public boolean isUserExist(Account account);
		public void deleteAllAccounts();
		}
