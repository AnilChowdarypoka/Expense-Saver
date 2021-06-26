package com.Expenses.Expenses.saver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.Expenses.Expenses.saver.model.CategoryDetails;
import com.Expenses.Expenses.saver.model.TransactionsEntity;
import com.Expenses.Expenses.saver.model.Users;
import com.Expenses.Expenses.saver.services.Categories;
import com.Expenses.Expenses.saver.services.TransactionsService;
import com.Expenses.Expenses.saver.services.UserDetails;
import com.Expenses.Expenses.saver.services.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;

@RestController

public class MyController {
	
	@Autowired 
	 private UserDetails userDetails;
	@Autowired
	private Categories categoryDetails;
	@Autowired
	private TransactionsService transactionsService;
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	/*  
	 * Users
	 */
	
	@PostMapping(path = "/register" , consumes = "application/json")
	public String addUser(@RequestBody  Users user) {
	  return this.userDetails.addUser(user);
	}

	@PostMapping(path = "/addUserWithDefaultCategories" , consumes = "application/json")
	public String addUserWithDefaultCategories(@RequestBody  Users user) {
	  return this.userDetails.addUserWithDefaultCategories(user);
	}
	@GetMapping("/login")
	public String  validateLogin(@RequestBody  Users user){
		return this.userDetails.validateUserDetails(user.getUserid() , user.getPassword() );
	}
	
	@GetMapping("/getUserDetails")
	public Users  getUsersByEmail(@RequestBody  Users user){
		return this.userDetails.getUsersByEmail(user.getEmail());
	}
	
	

	@PutMapping(path = "/updateUserDetails" , consumes = "application/json")
	public String updateUserDetails(@RequestBody  Users user) {
		return this.userDetails.updateUserDetails(user);
	}
	/*  
	 * Categories
	 */
	
	@GetMapping(path = "/categories/{userId}" )
	public List<CategoryDetails>  getCategories(@PathVariable String userId){
		return this.categoryDetails.getUserCategories(userId);
	}
	@PostMapping(path = "/addCategory" , consumes = "application/json")
	public String addCategory(@RequestBody  CategoryDetails Categorydetails) {
	  return this.categoryDetails.addNewCategory(Categorydetails);}
	
	@PostMapping(path = "/addDefaultCategory" , consumes = "application/json")
	public String addDefaultCategories(@RequestBody  CategoryDetails Categorydetails) {
	  return this.categoryDetails.addDefaultCategories(Categorydetails.getUserSno());}
	  
	@PutMapping(path = "/updateCategory" , consumes = "application/json")
		public String updateCategory(@RequestBody  CategoryDetails Categorydetails) {
		  return this.categoryDetails.updateCategoryDetails(Categorydetails);  
	}
	@DeleteMapping(path = "/deleteCategory" , consumes = "application/json")
	public String deleteCategory(@RequestBody  CategoryDetails Categorydetails) {
	  return this.categoryDetails.deleteCategoryDetails(Categorydetails); 
      }
	@DeleteMapping(path = "/deleteAllCategories" , consumes = "application/json")
	public String deleteAllCategories(@RequestBody  CategoryDetails Categorydetails) {
	  return this.categoryDetails.deleteallCategoryDetails(Categorydetails.getUserSno()); 
      }
	
	/*  
	 * Transactions
	 */
	
	@GetMapping("/viewtransactions/{userId}")
	public List<TransactionsEntity>  getTransactions(@PathVariable String userId){
		return this.transactionsService.viewTransactions(userId);
	}   
	@GetMapping("/viewTransactionsByCategory/user/{userId}/cat-id/{catId}")
	public List<TransactionsEntity>  viewTransactionsByCategory(@PathVariable String userId , @PathVariable String catId){
		return this.transactionsService.viewTransactionsByCategory(userId ,catId);
	} 
	@GetMapping("/viewTransactionsByTransId/user/{userId}/cat-id/{catId}/transaction/{transId}")
	public TransactionsEntity viewTransactionsByTransId(@PathVariable String userId , @PathVariable String catId , @PathVariable String transId ){
		return this.transactionsService.viewTransactionsByTransId(transId , userId ,catId);
	}
	@PostMapping(path = "/addTransactionsInCategory/user/{userId}" , consumes = "application/json")
	public String addTransactions(@PathVariable String userId , @RequestBody  TransactionsEntity transactionsEntity) {
	  return this.transactionsService.addTransaction(transactionsEntity);}
	  
	@PutMapping(path = "/updateTransactionsInCategory" , consumes = "application/json")
		public String updateTransactions(@RequestBody TransactionsEntity transactionsEntity) {
		  return this.transactionsService.updateTransactionDetails(transactionsEntity);  
	}
	@DeleteMapping(path = "/deleteTransaction" , consumes = "application/json")
	public String deleteTransactions(@RequestBody  TransactionsEntity transactionsEntity) {
	  return this.transactionsService.deleteTransaction(transactionsEntity); 
      }
	@DeleteMapping(path = "/deleteAllTransactions/user/{userId}" )
	public String deleteTransactions(@PathVariable  String userId ) {
	  return this.transactionsService.deleteAllTransactions( userId); 
      }
	@DeleteMapping(path = "/deleteAllTransactionsInCategory/user/{userId}/cat-id/{catId}")
	public String deleteTransactions(@PathVariable  String userId , @PathVariable String catId) {
	  return this.transactionsService.deleteAllTransactionsInCategory( catId ,  userId); 
      }
	
	}
	