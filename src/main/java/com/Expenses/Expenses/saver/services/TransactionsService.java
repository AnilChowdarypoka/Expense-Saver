package com.Expenses.Expenses.saver.services;

import java.util.List;
import com.Expenses.Expenses.saver.model.TransactionsEntity;

public interface TransactionsService {
	
	public String addTransaction(TransactionsEntity transactionsEntity);
	public String updateTransactionDetails(TransactionsEntity transactionsEntity);
	public String deleteTransaction(TransactionsEntity transactionsEntity);
	public String deleteAllTransactions(String userId);
	public String deleteAllTransactionsInCategory(String catId , String userId);
	public List<TransactionsEntity> viewTransactions(String userId);
	public  List<TransactionsEntity> viewTransactionsByCategory(String userId , String catId);
	public TransactionsEntity viewTransactionsByTransId(String transId , String userId , String catId);

}
