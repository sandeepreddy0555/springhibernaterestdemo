package com.hbp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hbp.model.Account;
import com.hbp.service.AccountService;
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;  //Service which will do all data retrieval/manipulation work
 
     
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/accounts/", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> listAllUsers() {
        List<Account> customers = accountService.getAllAccounts();
        if(customers.isEmpty()){
            return new ResponseEntity<List<Account>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Account>>(customers, HttpStatus.OK);
    }
 
 
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getUser(@PathVariable("id") int custId) {
        System.out.println("Fetching User with id " + custId);
        Account account = accountService.getAccountById(custId);
        if (account == null) {
            System.out.println("User with id " + custId + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/account/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Account account, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + account.getCustId() + " "+ account.getAcctType() );
 
        if (accountService.isUserExist(account)) {
            System.out.println("A User with name " + account.getCustId() + " "+ account.getAcctType()  + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        accountService.addAccount(account);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/account/{id}").buildAndExpand(account.getAcctId()).toUri());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a User --------------------------------------------------------
     
    @RequestMapping(value = "/account/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateUser(@PathVariable("id") int custId, @RequestBody Account account) {
        System.out.println("Updating User " + custId);
         
        Account currentAccount= accountService.getAccountById(custId);
         
        if (currentAccount==null) {
            System.out.println("User with id " + custId + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
 
        ((Account) accountService).setCustId(account.getCustId());
        ((Account) accountService).setAcctType(account.getAcctType());
         
        accountService.updateAccount(currentAccount);
        return new ResponseEntity<Account>(currentAccount, HttpStatus.OK);
    }
 
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Account> deleteUser(@PathVariable("id") int acctId) {
        System.out.println("Fetching & Deleting User with id " + acctId);
 
        accountService.getAccountById(acctId);
        if ( accountService==null) {
            System.out.println("Unable to delete. User with id " + acctId + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
 
        accountService.deleteAccount(acctId);
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }
 
     
    //------------------- Delete All Users --------------------------------------------------------
     
    @RequestMapping(value = "/account/", method = RequestMethod.DELETE)
    public ResponseEntity<Account> deleteAllUsers() {
        System.out.println("Deleting All Users");
 
        accountService.deleteAllAccounts();
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }
 


}
