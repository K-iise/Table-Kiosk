package entity;

import java.io.Serializable;

public class Account implements Serializable {
	private String User_ID;
	private String User_PW;
	private String User_Branch;
	private String User_Address;
	
	
	public String getUser_ID() {
		return User_ID;
	}
	public void setUser_ID(String user_ID) {
		User_ID = user_ID;
	}
	public String getUser_PW() {
		return User_PW;
	}
	public void setUser_PW(String user_PW) {
		User_PW = user_PW;
	}
	public String getUser_Branch() {
		return User_Branch;
	}
	public void setUser_Branch(String user_Branch) {
		User_Branch = user_Branch;
	}
	public String getUser_Address() {
		return User_Address;
	}
	public void setUser_Address(String user_Address) {
		User_Address = user_Address;
	}
	
	
	
}
