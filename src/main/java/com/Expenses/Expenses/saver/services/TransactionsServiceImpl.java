package com.Expenses.Expenses.saver.services;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Expenses.Expenses.saver.model.TransactionsEntity;
import com.Expenses.Expenses.saver.model.Users;

@Service
@Transactional

public class TransactionsServiceImpl implements TransactionsService {
	@Autowired
  private JdbcTemplate jdbctemplate;
	
	@Override
	public String addTransaction(TransactionsEntity transactionsEntity) {
		final String insertQuery = "INSERT INTO TRANSACTIONS (USERID, CATID, TRANSACTIONAMOUNT, DATEADDED, DATEMODIFIED) VALUES (?,?,?,?,?)";
		System.out.println("Insert Query : " + insertQuery);
	     KeyHolder keyHolder = new GeneratedKeyHolder();
	     jdbctemplate.update(
	             connection -> {
	                 PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{"TID"});
	                 ps.setString(1, transactionsEntity.getUserId());
	                 ps.setString(2, transactionsEntity.getCatId());
	                 ps.setDouble(3, transactionsEntity.getTransactionAmount());
	                 ps.setString(4, transactionsEntity.getDataAdded());
	                 ps.setString(5, transactionsEntity.getDateModified());
	                 return ps;
	             }, keyHolder);
	     return "Transaction added sucessfully";
	}

	@Override
	public String updateTransactionDetails(TransactionsEntity transactionsEntity) {
		final String updateQuery = "UPDATE TRANSACTIONS SET TRANSACTIONAMOUNT  = ?, DATEMODIFIED = ? WHERE TID = ? AND CATID = ? AND USERID = ? ";
		System.out.println("Update Query : " + updateQuery);
	     KeyHolder keyHolder = new GeneratedKeyHolder();
	     jdbctemplate.update(
	             connection -> {
	                 PreparedStatement ps = connection.prepareStatement(updateQuery, new String[]{String.valueOf(transactionsEntity.getTransId())});
	                 ps.setDouble(1, transactionsEntity.getTransactionAmount());
	                 ps.setString(2, transactionsEntity.getDateModified());
	                 ps.setLong(3, transactionsEntity.getTransId());
	                 ps.setString(4, transactionsEntity.getCatId());
	                 ps.setString(5, transactionsEntity.getUserId());
	               
	         System.out.println("Update Query : "+ps.toString());      
	         return ps;
	             }, keyHolder);
	     return "Transaction modified sucessfully";
	}
	
@Override
	public String deleteTransaction(TransactionsEntity transactionsEntity) {
		TransactionsEntity tempTrans = viewTransactionsByTransId(String.valueOf(transactionsEntity.getTransId()) , transactionsEntity.getUserId() , transactionsEntity.getCatId());
		if(tempTrans == null) {
			System.out.println("No transactions to delete");
			return "No transactions to delete";
		}
		else {
		final String deleteQuery = "DELETE FROM TRANSACTIONS  WHERE TID = ? AND CATID = ? AND USERID = ? ";
		System.out.println("Delete Query : " + deleteQuery);
	     KeyHolder keyHolder = new GeneratedKeyHolder();
	     jdbctemplate.update(
	             connection -> {
	                 PreparedStatement ps = connection.prepareStatement(deleteQuery, new String[]{String.valueOf(tempTrans.getTransId())});
	                 ps.setLong(1, tempTrans.getTransId());
	                 ps.setString(2, transactionsEntity.getCatId());
	                 ps.setString(3, transactionsEntity.getUserId());
	               
	         System.out.println("Delete Query : "+ps.toString());      
	         return ps;
	             }, keyHolder);
	     return "Transaction Deleted sucessfully";}	}

	@Override
	public String deleteAllTransactions(String userId ) {
		List<TransactionsEntity> tempTrans = viewTransactions(userId);
		if(tempTrans == null) {
			System.out.println("No transactions to delete");
			return "No transactions to delete";
		}
		else {
		final String deleteQuery = "DELETE FROM TRANSACTIONS  WHERE  USERID = ? ";
		System.out.println("Delete Query : " + deleteQuery);
	     KeyHolder keyHolder = new GeneratedKeyHolder();
	     jdbctemplate.update(
	             connection -> {
	                 PreparedStatement ps = connection.prepareStatement(deleteQuery, new String[]{userId});
	                 ps.setString(1,userId);	               
	         System.out.println("Delete Query : "+ps.toString());      
	         return ps;
	             }, keyHolder);
	     return "All Transactions Deleted sucessfully";}	}
	
	@Override
	public String deleteAllTransactionsInCategory(String catId , String userId ) {
		List<TransactionsEntity> tempTrans = viewTransactionsByCategory(userId , catId);
		if(tempTrans == null) {
			System.out.println("No transactions to delete");
			return "No transactions to delete";
		}
		else {
		final String deleteQuery = "DELETE FROM TRANSACTIONS  WHERE  USERID = ? AND CATID = ?";
		System.out.println("Delete Query : " + deleteQuery);
	     KeyHolder keyHolder = new GeneratedKeyHolder();
	     jdbctemplate.update(
	             connection -> {
	                 PreparedStatement ps = connection.prepareStatement(deleteQuery, new String[]{userId});
	                 ps.setString(1,userId);	
	                 ps.setString(2,catId);	               

	         System.out.println("Delete Query : "+ps.toString());      
	         return ps;
	             }, keyHolder);
	     return "All Transactions Deleted sucessfully";}	}
	
	@Override
	public List<TransactionsEntity> viewTransactions(String userId) {
		final String selQuery = "SELECT * FROM TRANSACTIONS WHERE USERID ='"+userId+"'";
		System.out.println("Transactiona Sel Query : " + selQuery );
		  List<TransactionsEntity> transactionsEntity = jdbctemplate.query(selQuery, new Object[]{}, (resultSet, i) -> {
              return TransactionsEntity.create(
                      resultSet.getLong("TID"),
                      resultSet.getString("CATID"),
                      resultSet.getString("USERID"),
                      resultSet.getDouble("TRANSACTIONAMOUNT"),
                      resultSet.getString("DATEADDED"),
                      resultSet.getString("DATEMODIFIED"));
          }
  );
System.out.println("Transactions : "+ transactionsEntity);
  if (transactionsEntity.size() > 0) {
      return transactionsEntity;
  }
  else {
  return null;}
	}
	
	public  List<TransactionsEntity> viewTransactionsByCategory(String userId , String catId) {
		System.out.println("userid : " +userId + " Cat id : " + catId);
		final String selQuery  = "SELECT * FROM TRANSACTIONS WHERE USERID = '"+userId+"' AND CATID = '"+catId+"'";
		System.out.println("select Query : " + selQuery );
		  List<TransactionsEntity> transactionsEntity = jdbctemplate.query(selQuery, new Object[]{}, (resultSet, i) -> {
            return TransactionsEntity.create(
                    resultSet.getLong("TID"),
                    resultSet.getString("CATID"),
                    resultSet.getString("USERID"),
                    resultSet.getDouble("TRANSACTIONAMOUNT"),
                    resultSet.getString("DATEADDED"),
                    resultSet.getString("DATEMODIFIED"));
        }
);
		  System.out.println("Transaction Data : " + transactionsEntity.toString());

if (transactionsEntity.size() > 0) {
    return  transactionsEntity;
}
else {
return null;}
	}

	public TransactionsEntity viewTransactionsByTransId(String transId , String userId , String catId) {
		final String selQuery = "SELECT * FROM TRANSACTIONS WHERE TID = '"+transId+"' AND USERID = '"+userId+"' AND CATID = '"+catId+"'";
		System.out.println("Select Query : " + selQuery);
		  List<TransactionsEntity> transactionsEntity = jdbctemplate.query(selQuery, new Object[]{}, (resultSet, i) -> {
          return TransactionsEntity.create(
                  resultSet.getLong("TID"),
                  resultSet.getString("CATID"),
                  resultSet.getString("USERID"),
                  resultSet.getDouble("TRANSACTIONAMOUNT"),
                  resultSet.getString("DATEADDED"),
                  resultSet.getString("DATEMODIFIED"));
      }
);
		  System.out.println("Transaction Data : " + transactionsEntity.toString());

if (transactionsEntity.size() > 0) {
  return (TransactionsEntity) transactionsEntity.get(0);
}
else {
return null;}
	}
}
