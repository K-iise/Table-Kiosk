package entity;

import java.io.Serializable;

public class Call implements Serializable {
	private String guestID;

	private int spoon;
	private int chopstick;
	private int tableware;
	private int water;
	private int ice;
	private int tissue;
	private int others;
	
	public String getGuestID() {
		return guestID;
	}
	public void setGuestID(String guestID) {
		this.guestID = guestID;
	}
	
	public int getSpoon() {
		return spoon;
	}
	public void setSpoon(int spoon) {
		this.spoon = spoon;
	}
	public int getChopstick() {
		return chopstick;
	}
	public void setChopstick(int chopstick) {
		this.chopstick = chopstick;
	}
	public int getTableware() {
		return tableware;
	}
	public void setTableware(int tableware) {
		this.tableware = tableware;
	}
	public int getWater() {
		return water;
	}
	public void setWater(int water) {
		this.water = water;
	}
	public int getIce() {
		return ice;
	}
	public void setIce(int ice) {
		this.ice = ice;
	}
	public int getTissue() {
		return tissue;
	}
	public void setTissue(int tissue) {
		this.tissue = tissue;
	}
	public int getOthers() {
		return others;
	}
	public void setOthers(int others) {
		this.others = others;
	}
	
	
}
