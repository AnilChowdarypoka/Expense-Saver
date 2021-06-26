package com.Expenses.Expenses.saver.model;

public class TransactionsEntity {
	
	private long transId;
	private String userId;
	private String catId;
	private double transactionAmount;
	private String dataAdded;
	private String dateModified;

	@Override
	public String toString() {
		return "Transactions [transId=" + transId + ", userId=" + userId + ", catId=" + catId + ", transactionAmount="
				+ transactionAmount + ", dataAdded=" + dataAdded + ", dateModified=" + dateModified + "]";
	}
	public long getTransId() {
		return transId;
	}
	public void setTransId(long transId) {
		this.transId = transId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getDataAdded() {
		return dataAdded;
	}
	public void setDataAdded(String dataAdded) {
		this.dataAdded = dataAdded;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public TransactionsEntity(long transId, String userId, String catId, double transactionAmount, String dataAdded,
			String dateModified) {
		super();
		this.transId = transId;
		this.userId = userId;
		this.catId = catId;
		this.transactionAmount = transactionAmount;
		this.dataAdded = dataAdded;
		this.dateModified = dateModified;
	}
	public TransactionsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static TransactionsEntity create(long transId,  String catId, String userId, double transactionAmount, String dataAdded,
			String dateModified) {
		TransactionsEntity trans = new TransactionsEntity();
		trans.setTransId(transId);
		trans.setCatId(catId);
		trans.setUserId(userId);
		trans.setTransactionAmount(transactionAmount);
		trans.setDataAdded(dataAdded);
		trans.setDateModified(dateModified);
				
		return trans;
	}

}
