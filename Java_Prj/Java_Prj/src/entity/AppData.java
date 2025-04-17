package entity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class AppData {
	public static Guest guest= new Guest();
	public static Category ctg=new Category();
	public static Menu menu=new Menu();
	public static Order order=new Order();
	
	//관리자용
	public static Account account=new Account();
	public static LinkedList<Order> orderq=new LinkedList<Order>();
	public static Vector<Guest> guestids=new Vector<Guest>();
}