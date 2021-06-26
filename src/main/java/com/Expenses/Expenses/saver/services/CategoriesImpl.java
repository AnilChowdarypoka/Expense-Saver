package com.Expenses.Expenses.saver.services;
import com.Expenses.Expenses.saver.model.CategoryDetails;
import com.Expenses.Expenses.saver.model.Users;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriesImpl implements Categories {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public  String addDefaultCategories(String user_sno) {
		System.out.println("Adding default categories");
		String retrunMsg = "Default Categories are : Travelling , Food , Gym ";
		ArrayList<CategoryDetails> tempArr = new ArrayList<CategoryDetails>();
		tempArr.add(new CategoryDetails("Travelling" , "Travel expenses", "124586543" , String.valueOf(user_sno)));
		tempArr.add(new CategoryDetails("Food" , "Food expenses", "124586543" , String.valueOf(user_sno)));
		tempArr.add(new CategoryDetails("Gym" , "Gym expenses", "124586543" , String.valueOf(user_sno)));	
		for(CategoryDetails cd : tempArr ) {
		String statusMsg  = addNewCategory(cd);
			retrunMsg = retrunMsg+" "+cd.getCategoryType()+" " +statusMsg+ ",";
		}
		return retrunMsg;
	}
	


	@Override
	public String addNewCategory(CategoryDetails  Categorydetails) {
		//System.out.println("Category data : "+Categorydetails.toString());
		System.out.println("Checking for category data before adding");
		CategoryDetails cdtemp = getCategoryDetailsByCategoryName(Categorydetails.getCategoryType() , Categorydetails.getUserSno());
		//System.out.println("Category is : "  +cdtemp);
		if(cdtemp == null ) {
     final String insertQuery  = "INSERT INTO CATEGORIES (CATEGORY_TYPE , CATEGORY_DESCRIPTION , DATECREATED, USER_SNO) VALUES (?,?,?,?)";
     System.out.println("Query : "+ insertQuery);
     KeyHolder keyHolder = new GeneratedKeyHolder();
     jdbcTemplate.update(
             connection -> {
                 PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{"CID"});
                 ps.setString(1, Categorydetails.getCategoryType());
                 ps.setString(2, Categorydetails.getCategoryDescription());
                 ps.setString(3,Categorydetails.getDateCreated());
                 ps.setString(4, Categorydetails.getUserSno());
                 System.out.println("insert category Query: "+ps.toString());
                 return ps;
             }, keyHolder);
     return "Category Sucessfully added";
	}
		else {
			updateCategoryDetails(Categorydetails);
			return "Category Sucessfully updated";
		}}

	@Override
	public CategoryDetails getCategoryDetailsByCategoryName(String CategoryName , String userSno) {
		System.out.println("Searching for Categories");
		final String insQuery = "SELECT * FROM CATEGORIES WHERE CATEGORY_TYPE ='"+CategoryName+"' AND USER_SNO = '"+userSno+"'";
		System.out.println("Insert query for category details : " +insQuery);
		   List<CategoryDetails> Categorydetails = jdbcTemplate.query(insQuery , new Object[]{},  (resultSet, i) -> {			   
               return CategoryDetails.create(
                       resultSet.getLong("CID"),
                       resultSet.getString("CATEGORY_TYPE"),
                       resultSet.getString("CATEGORY_DESCRIPTION"),
                       resultSet.getString("DATECREATED"),
                       resultSet.getString("USER_SNO"));           
           }
   );

		   System.out.println("Categorydetails by category name : "+ Categorydetails.toString());
   if (Categorydetails.size() > 0) {
       return (CategoryDetails) Categorydetails.get(0);
   }
   else {
   return null;}
	}
	
	@Override
	public List<CategoryDetails> getUserCategories(String userSno) {
		System.out.println("user sno : " + userSno);
	final String selQuery  = "SELECT * FROM CATEGORIES WHERE USER_SNO = '"+userSno+"'";
	System.out.println("select query : "+ selQuery);
		   List<CategoryDetails> Categorydetails = jdbcTemplate.query(selQuery, new Object[] {}, (resultSet, i) -> {
			   
               return CategoryDetails.create(
                       resultSet.getLong("CID"),
                       resultSet.getString("CATEGORY_TYPE"),
                       resultSet.getString("CATEGORY_DESCRIPTION"),
                       resultSet.getString("DATECREATED"),
                       resultSet.getString("USER_SNO"));
               
           }
   );

		   System.out.println("Categorydetails by category name : "+ Categorydetails.toString());
   if (Categorydetails.size() > 0) {
	   System.out.println("sending category");
       return Categorydetails;
   }
   else {
   return null;}
	}
	
	public String updateCategoryDetails(CategoryDetails  Categorydetails) {
		System.out.println("Checking for category details");
		CategoryDetails resultUser =  getCategoryDetailsByCategoryName(Categorydetails.getCategoryType() , Categorydetails.getUserSno());
        if(resultUser == null) {
        System.out.println("No such category is available..Create one");
        return "No such category is available";
        }
        else {
        	System.out.println("Updating Category details");
       String updateQuery = "UPDATE CATEGORIES  SET  CATEGORY_TYPE = ? , CATEGORY_DESCRIPTION = ? WHERE CID  = ? AND USER_SNO = ? ";
       System.out.println("Query : "+updateQuery);
       System.out.println("Category Details for updation : " + Categorydetails.toString());
       KeyHolder keyHolder = new GeneratedKeyHolder();
       jdbcTemplate.update(
               connection -> {
                   PreparedStatement ps = connection.prepareStatement(updateQuery, new String[] {Categorydetails.getUserSno()});
                   ps.setString(1, Categorydetails.getCategoryType());
                   ps.setString(2,Categorydetails.getCategoryDescription());
                   ps.setLong(3, resultUser.getcId());
                   ps.setString(4, Categorydetails.getUserSno());
                   System.out.println("Updating category Query: "+ps.toString());
                   return ps;
               }, keyHolder);
       
       return "Category Details Sucessfuly Updated";
       }
	}
	
	public String deleteCategoryDetails(CategoryDetails  Categorydetails) {
		System.out.println("Checking for category details");
		CategoryDetails resultUser =  getCategoryDetailsByCategoryName(Categorydetails.getCategoryType() , Categorydetails.getUserSno());
        if(resultUser == null) {
        System.out.println("No such category is available");
        return "No such category is available";
        }
        else {
        	System.out.println("Deleting Category details");
       String updateQuery = "DELETE FROM CATEGORIES WHERE CID  = ? AND USER_SNO = ? ";
       System.out.println("Query : "+updateQuery);
       System.out.println("Category Details for updation : " + Categorydetails.toString());
       KeyHolder keyHolder = new GeneratedKeyHolder();
       jdbcTemplate.update(
               connection -> {
                   PreparedStatement ps = connection.prepareStatement(updateQuery, new String[] {Categorydetails.getUserSno()});
                   ps.setLong(1, resultUser.getcId());
                   ps.setString(2, Categorydetails.getUserSno());
                   System.out.println("Delete category Query: "+ps.toString());
                   return ps;
               }, keyHolder);
       
       return "Category Details Sucessfuly Deleted";
       }
	}
	
	public String deleteallCategoryDetails(String  userId) {
		System.out.println("Checking for category details");
		List<CategoryDetails> resultUser =  getUserCategories(userId);
        if(resultUser == null) {
        System.out.println("No such category is available");
        return "No such category is available";
        }
        else {
        	System.out.println("Deleting Category details");
       String updateQuery = "DELETE FROM CATEGORIES WHERE USER_SNO = ? ";
       System.out.println("Query : "+updateQuery);
       KeyHolder keyHolder = new GeneratedKeyHolder();
       jdbcTemplate.update(
               connection -> {
                   PreparedStatement ps = connection.prepareStatement(updateQuery, new String[] {userId});
                   ps.setString(1,userId);
                   System.out.println("Delete category Query: "+ps.toString());
                   return ps;
               }, keyHolder);
       
       return "Category Details Sucessfuly Deleted";
       }
	}


	
}
