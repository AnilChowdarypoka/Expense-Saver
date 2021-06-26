package com.Expenses.Expenses.saver.services;
import java.util.List;

import com.Expenses.Expenses.saver.model.CategoryDetails;

public interface Categories {
	
	public String addDefaultCategories(String user_sno);
	public String addNewCategory(CategoryDetails  Categorydetails);
	public CategoryDetails getCategoryDetailsByCategoryName(String CategoryName, String userSno);  
	public String deleteCategoryDetails(CategoryDetails  Categorydetails);
	public String updateCategoryDetails(CategoryDetails  Categorydetails);
	public String deleteallCategoryDetails(String  userId);
	public List<CategoryDetails> getUserCategories(String userSno);

}
