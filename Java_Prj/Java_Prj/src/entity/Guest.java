package entity;

import java.io.Serializable;

public class Guest implements Serializable {
	private String Guest_ID;
	private String User_ID;
	
	public String getGuest_ID() {
		return Guest_ID;
	}
	public void setGuest_ID(String guest_ID) {
		Guest_ID = guest_ID;
	}
	public String getUser_ID() {
		return User_ID;
	}
	public void setUser_ID(String user_ID) {
		User_ID = user_ID;
	}
	
	
}
