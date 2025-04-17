package control;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import entity.Account;
import entity.Menu;
import entity.Order;
import entity.Order_detail;
import entity.Protocol;
import entity.DataType;
import entity.Guest;

public class Server {
	public static final int PORT = 8003;
	public ServerSocket server;
	public Vector<Client> clients;
	
	public AccountMgr accMgr;
	public CategoryMgr ctgMgr;
	public GuestMgr guestMgr;
	public MenuMgr menuMgr;
	public Order_detailMgr detailMgr;
	public OrderMgr orderMgr;
	

	public Server() {
		try {
			server = new ServerSocket(PORT);
			clients = new Vector<Client>();
			accMgr=new AccountMgr();
			ctgMgr=new CategoryMgr();
			guestMgr=new GuestMgr();
			menuMgr=new MenuMgr();
			detailMgr=new Order_detailMgr();
			orderMgr=new OrderMgr();
			
		} catch (Exception e) {
			System.err.println("Err in Server");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("server is open..");
		
		try {
			while(true) {
				Socket sock= server.accept();
				Client client = new Client(sock);
				client.start();
				clients.addElement(client);
			}
		}catch(Exception e) {
			System.out.println("err2");
			e.printStackTrace();
		}
	}//--생성자 끝
	
	//접속이 끊어진 client를 벡터에서 제거
	public void removeClient(Client c) {
		clients.remove(c);
	}
	
	//내부클래스 client
	class Client extends Thread{
		Socket sock;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		
		public Client(Socket sock) {
			this.sock=sock;
			try {
				oos = new ObjectOutputStream(sock.getOutputStream());
				ois = new ObjectInputStream(sock.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//--생성자 끝
		
		@Override
		public void run() {
			try {
				while(true) {
					DataType data=(DataType)ois.readObject();

					switch(data.protocol) {
					case Protocol.loginGuest:
						loginGuest(data);
						break;
					case Protocol.menulist:
						menulist(data);
						break;
					case Protocol.order:
						order(data);
						break;
					case Protocol.order_detail:
						order_detail(data);
						break;
					
					}
				}//--while
			}catch(Exception e){
				removeClient(this);
			}
		}//--run
		
		// 게스트 로그인.
		public void loginGuest(DataType data) {
			
			Guest guest = (Guest) data.obj;
			try {
				if(guestMgr.selectGuest(guest)) {
					oos.writeObject(data);
					System.out.println("게스트 로그인 성공");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 메뉴 리스트 추가.
		public void menulist(DataType data) {
			Vector<Menu> v=menuMgr.selectAllMenu();
			data.obj=v;
			try {
				oos.writeObject(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 주문하기.
		public void order(DataType data) {
			Order order = (Order) data.obj;
			try {
				if(orderMgr.insertOrder(order)) {
					oos.writeObject(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void order_detail(DataType data) {
			Vector <Order_detail> detail = (Vector <Order_detail>) data.obj;
			try {
				if(detailMgr.insertOrder_detail(detail)) {
					oos.writeObject(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}//--client
	
	public static void main(String[] args) {
		new Server();
	}

}


