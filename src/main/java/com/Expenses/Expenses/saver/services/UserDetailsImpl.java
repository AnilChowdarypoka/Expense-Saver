package com.Expenses.Expenses.saver.services;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Expenses.Expenses.saver.model.Users;

@Service
@Transactional
public class UserDetailsImpl implements UserDetails {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private Categories categories;

    public String addUser(Users user) {
    	
    	 System.out.println("Checking for user details");
         Users resultUser =  getUsersByEmail(user.getEmail());
         if(resultUser != null) {
         System.out.println(resultUser.toString());
         return "User already registed";
         }
         else {
        String insertQuery = "INSERT INTO  USERS  (NAME , USERID, PASSWORD , EMAIL , MOBILE) values (?,?,?,?,?)";
        System.out.println("Query : "+ insertQuery);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{"ID"});
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getUserid());
                    ps.setString(3,user.getPassword());
                    ps.setString(4, user.getEmail());
                    ps.setLong(5, user.getMobile());
                    System.out.println("User Insert Query :  " + ps.toString());
                    return ps;
                }, keyHolder);
	  String defaultCategoryStatus= null;
        Users tempUser = getUsersByEmail(user.getEmail());
		 defaultCategoryStatus = categories.addDefaultCategories(String.valueOf(tempUser.getId()));
		System.out.println("defaultCategoryStatus : " + defaultCategoryStatus);
        return "User sucessfuly Created and default categories are added";}

    }

	public String addUserWithDefaultCategories(Users user) {
		String userStatus = null,  defaultCategoryStatus= null;
		userStatus = addUser(user);
		System.out.println("userStatus : " + userStatus);
		Users tempUser = getUsersByEmail(user.getEmail());
		System.out.println("TempUser for adding default cat : " + tempUser.toString());
		 defaultCategoryStatus = categories.addDefaultCategories(String.valueOf(tempUser.getId()));
		System.out.println("defaultCategoryStatus : " + defaultCategoryStatus);
		return "User Status : " + userStatus + " Default category Status : " + defaultCategoryStatus;
	}
	

    
    public Users getUsersByEmail(String email) {
    	System.out.println("Checking for user details via Email");
    	final String selQuery  = "SELECT * FROM USERS WHERE EMAIL ='"+email+"'";
    	System.out.println("get user sel query : " + selQuery);
        List<Users> users = jdbcTemplate.query(selQuery, new Object[]{}, (resultSet, i) -> {
                    return Users.create(
                            resultSet.getLong("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getString("USERID"),
                            resultSet.getString("EMAIL"),
                            resultSet.getLong("MOBILE"));
                }
        );

        if (users.size() > 0) {
            return (Users) users.get(0);
        }
        else {
        return null;}
    }

	@Override
	public String updateUserDetails(Users user) {
		System.out.println("Checking for user details");
        Users resultUser =  getUsersByEmail(user.getEmail());
        if(resultUser == null) {
        System.out.println("No user with such email");
        return "No user with such email";
        }
        else {
        	System.out.println("Updating user details");
       String updateQuery = "UPDATE USERS SET NAME = ? , PASSWORD = ?, EMAIL = ? , MOBILE = ? WHERE USERID  = ?";
       System.out.println("Query : "+updateQuery);
       KeyHolder keyHolder = new GeneratedKeyHolder();
       jdbcTemplate.update(
               connection -> {
                   PreparedStatement ps = connection.prepareStatement(updateQuery, new String[] {user.getEmail()});
                   ps.setString(1, user.getName());
                   ps.setString(2,user.getPassword());
                   ps.setString(3, user.getEmail());
                   ps.setLong(4, user.getMobile());
                   ps.setString(5, user.getUserid());
                   return ps;
               }, keyHolder);
       
       return "User Details Sucessfuly Updated";
       }
	}

	@Override
	public String validateUserDetails(String userId, String password) {
		 List<Users> users = jdbcTemplate.query("SELECT * FROM USERS WHERE USERID =? AND PASSWORD = ?", new Object[]{userId , password }, (resultSet, i) -> {
             return Users.create(
                     resultSet.getLong("ID"),
                     resultSet.getString("NAME"),
                     resultSet.getString("USERID"),
                     resultSet.getString("EMAIL"),
                     resultSet.getLong("MOBILE"));
         }
 );

 if (users.size() > 0) {
     return "User validated Sucessfully";
 }
 else {
 return "Invalid User Credentials , Please try Again";}
	}

}
