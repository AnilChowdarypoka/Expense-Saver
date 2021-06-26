package com.Expenses.Expenses.saver.model;

public class CategoryDetails {

	private long cId;
	private String categoryType;
	private String categoryDescription;
	private String dateCreated;
	private String userSno;
	public CategoryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryDetails( String categoryType, String categoryDescription, String dateCreated,
			String userSno) {
		super();
		this.categoryType = categoryType;
		this.categoryDescription = categoryDescription;
		this.dateCreated = dateCreated;
		this.userSno = userSno;
	}
	public long getcId() {
		return cId;
	}
	public void setcId(long cId) {
		this.cId = cId;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getUserSno() {
		return String.valueOf(userSno);
	}
	public void setUserSno(String userSno) {
		this.userSno = userSno;
	}
	@Override
	public String toString() {
		return "CategoryDetails [cId=" + cId + ", categoryType=" + categoryType + ", categoryDescription="
				+ categoryDescription + ", dateCreated=" + dateCreated + ", userSno=" + userSno + "]";
	}
	public static CategoryDetails create(long cId, String categoryType, String categoryDescription, String dateCreated , String userSno) {
		CategoryDetails categorydetails = new CategoryDetails();
		categorydetails.setcId(cId);
		categorydetails.setCategoryType(categoryType);
		categorydetails.setCategoryDescription(categoryDescription);
		categorydetails.setDateCreated(dateCreated);
		categorydetails.setUserSno(userSno);

		return categorydetails;
	}
	
}
