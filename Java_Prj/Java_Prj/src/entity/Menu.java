package entity;

import java.io.Serializable;

public class Menu implements Serializable {
	private String Ctg_Name;
	private String Menu_Name;
	private int Menu_Price;
	
	
	public String getCtg_Name() {
		return Ctg_Name;
	}
	public void setCtg_Name(String ctg_Name) {
		Ctg_Name = ctg_Name;
	}
	public String getMenu_Name() {
		return Menu_Name;
	}
	public void setMenu_Name(String menu_Name) {
		Menu_Name = menu_Name;
	}
	public int getMenu_Price() {
		return Menu_Price;
	}
	public void setMenu_Price(int menu_Price) {
		Menu_Price = menu_Price;
	}
	
	
}
