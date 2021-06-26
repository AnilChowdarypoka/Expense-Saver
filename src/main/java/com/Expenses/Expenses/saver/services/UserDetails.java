package com.Expenses.Expenses.saver.services;
import java.util.ArrayList;
import com.Expenses.Expenses.saver.model.Users;
public interface UserDetails {
	public String addUser(Users user);
	public String addUserWithDefaultCategories(Users user);
	public Users getUsersByEmail(String email);
	public String updateUserDetails(Users user);
	public String validateUserDetails(String userId, String password);


	
}
