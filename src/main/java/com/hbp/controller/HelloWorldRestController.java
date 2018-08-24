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

import com.hbp.model.Customer;
import com.hbp.service.CustomerService;

@RestController
public class HelloWorldRestController {
 
    @Autowired
    CustomerService customerService;  //Service which will do all data retrieval/manipulation work
 
     
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/customers/", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> listAllUsers() {
        List<Customer> customers = customerService.getAllCustomers();
        if(customers.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }
 
 
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getUser(@PathVariable("id") int custId) {
        System.out.println("Fetching User with id " + custId);
        Customer customer = customerService.getCustomerById(custId);
        if (customer == null) {
            System.out.println("User with id " + custId + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/customer/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + customer.getFirstName() + " "+ customer.getLastName() );
 
        if (customerService.isUserExist(customer)) {
            System.out.println("A User with name " + customer.getFirstName() + " "+ customer.getLastName()  + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        customerService.addCustomer(customer);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customer/{id}").buildAndExpand(customer.getCustId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a User --------------------------------------------------------
     
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateUser(@PathVariable("id") int custId, @RequestBody Customer customer) {
        System.out.println("Updating User " + custId);
         
        Customer currentCustomer = customerService.getCustomerById(custId);
         
        if (currentCustomer==null) {
            System.out.println("User with id " + custId + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
 
        currentCustomer.setFirstName(customer.getFirstName());
        currentCustomer.setLastName(customer.getLastName());
        currentCustomer.setSalary(customer.getSalary());
         
        customerService.updateCustomer(currentCustomer);
        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
    }
 
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteUser(@PathVariable("id") int custId) {
        System.out.println("Fetching & Deleting User with id " + custId);
 
        Customer user = customerService.getCustomerById(custId);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + custId + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
 
        customerService.deleteCustomer(custId);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }
 
     
    //------------------- Delete All Users --------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteAllUsers() {
        System.out.println("Deleting All Users");
 
        customerService.deleteAllCustomers();
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }
 
}