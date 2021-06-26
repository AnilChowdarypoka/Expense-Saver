package com.Expenses.Expenses.saver.model;

public class Users {
	
	private long id;
    private String name;
    private  String userid;
    private String password;
    private String email;
    private long mobile;
	public long getId() {
		return id;
	}
	
	public static Users create(long id, String name, String userid , String email,  long phone) {
		Users user = new Users();
        user.setId(id);
        user.setName(name);
        user.setUserid(userid);
        user.setEmail(email);
        user.setMobile(phone);
        return user;
    }
	
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobile() {
		return mobile;
	}
	@Override
	public String toString() {
		return "users [id=" + id + ", name=" + name + ", userid=" + userid + ", password=" + password + ", email="
				+ email + ", mobile=" + mobile + "]";
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public Users(long id, String name, String userid, String password, String email, long mobile) {
		super();
		this.id = id;
		this.name = name;
		this.userid = userid;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
	}
	public Users() {
		super();
	}
}
