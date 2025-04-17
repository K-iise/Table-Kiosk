package entity;

import java.sql.Date;

public class Total_order {
	
	private int Order_No;
	private String Order_Date;
	private String Menu_list;
	private int Price;
	
	public int getOrder_No() {
		return Order_No;
	}
	public void setOrder_No(int order_No) {
		Order_No = order_No;
	}

	public String getMenu_list() {
		return Menu_list;
	}
	public void setMenu_list(String menu_list) {
		Menu_list = menu_list;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public String getOrder_Date() {
		return Order_Date;
	}
	public void setOrder_Date(String order_Date) {
		Order_Date = order_Date;
	}
	
	
	
}
