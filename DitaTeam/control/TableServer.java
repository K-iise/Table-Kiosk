package control;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import control.Server.Client;
import entity.Account;
import entity.DataType;
import entity.Menu;
import entity.Order_detail;
import entity.Protocol;

public class TableServer {
    private static final int PORT = 8003;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(100);

	public static AccountMgr accMgr;
	public static CategoryMgr ctgMgr;
	public static GuestMgr guestMgr;
	public static MenuMgr menuMgr;
	public static Order_detailMgr detailMgr;
	public static OrderMgr orderMgr;
	
	public TableServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 시작되었습니다. 포트: " + PORT);
			accMgr=new AccountMgr();
			ctgMgr=new CategoryMgr();
			guestMgr=new GuestMgr();
			menuMgr=new MenuMgr();
			detailMgr=new Order_detailMgr();
			orderMgr=new OrderMgr();
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
	}	
    
    public static void main(String[] args) {
    	new TableServer();
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                this.in = new ObjectInputStream(socket.getInputStream());
                this.out = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                DataType dt = (DataType) in.readObject();

				switch(dt.protocol) {
				case Protocol.loginGuest:
					break;
				case Protocol.menulist:
					break;
				case Protocol.order:
					break;
				}
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            	
            }
        }
        
        public void loginAcc(DataType data) {
			boolean bool=accMgr.selectAccount((Account)data.obj);
			if(bool) {//-로그인 성공
				try {
					out.writeObject((Account)data.obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {//-로그인 실패
				
			}
		}
		
		public void loginGuest(DataType data) {
			
		}
		
		public void menulist(DataType data) {
			Vector<Menu> v=menuMgr.selectAllMenu();
			data.obj=v;
			try {
				out.writeObject(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void order(DataType data) {
			
		}


    }
}
