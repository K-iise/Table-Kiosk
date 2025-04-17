package admin;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import control.AccountMgr;
import control.CategoryMgr;
import control.GuestMgr;
import control.MenuMgr;
import control.OrderMgr;
import control.Order_detailMgr;
import entity.Account;
import entity.AppData;
import entity.Call;
import entity.Menu;
import entity.Order;
import entity.Order_detail;
import entity.Protocol;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	
	}//--생성자 끝
	
    public void startServer() {
        try {
            while (true) {
                Socket sock = server.accept();
                Client client = new Client(sock);
                client.start();
                clients.addElement(client);
            }
        } catch (Exception e) {
            System.out.println("Error in server loop");
            e.printStackTrace();
        }
    }
	
	//접속이 끊어진 client를 벡터에서 제거
	public void removeClient(Client c) {
		clients.remove(c);
	}
	
	//내부클래스 client
	class Client extends Thread {
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
					case Protocol.call:
						call(data);
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
			System.out.println("게스트 로그인 요청 받음");
			((Guest) data.obj).setUser_ID(AppData.account.getUser_ID());
			try {
				if(guestMgr.selectGuest(guest)) {
					//((Guest) data.obj).setUser_ID(AppData.account.getUser_ID());
					
					oos.writeObject(data);
					System.out.println("게스트 로그인 성공");
				}
				else {
					((Guest) data.obj).setUser_ID(null);
					oos.writeObject(data);
					System.out.println("게스트 로그인 실패");
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
					AppData.orderq.add(order);
                    // JavaFX UI 작업을 애플리케이션 스레드에서 수행하도록 수정
                    Platform.runLater(() -> createNewWindow(order));
                    System.out.println("주문이 들어왔습니다.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		
		// 요청 사항 받기.
		public void call(DataType data) {
			Call call = (Call) data.obj;
			try {
				Order ord = new Order();
				ord.setGuest_ID(call.getGuestID());
				processOrder(ord, call);
				
				AppData.orderq.add(ord);
				
				// JavaFX UI 작업을 애플리케이션 스레드에서 수행하도록 수정
                Platform.runLater(() -> createNewWindow(ord));
                
                System.out.println("요청사항이 들어왔습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@FXML
		   private void createNewWindow(Order ord) {
		      try {
		         // fxml 파일 로드
		    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("MOrder_detail.fxml"));
		         Parent MDetailRoot = loader.load();

		         WindowController request = loader.getController();
		         
		         request.updateOrderList(ord);
		         
		         // 새로운 장면(Scene) 생성
		         Scene MDetailScene = new Scene(MDetailRoot);

		         // 새로운 스테이지 생성
		         Stage stage = new Stage();
		         Stage currentStage =(Stage)MTable.me;
		         stage.initModality(Modality.APPLICATION_MODAL);
	             stage.initOwner(MTable.me);
	             
	             stage.setOnShown(e -> {
	                 double stageWidth = stage.getWidth();
	                 double stageHeight = stage.getHeight();
	                 double currentStageX = currentStage.getX();
	                 double currentStageY = currentStage.getY();
	                 double currentStageWidth = currentStage.getWidth();
	                 double currentStageHeight = currentStage.getHeight();
	                 
	                 // 중앙 위치 계산
	                 double centerXPosition = currentStageX + (currentStageWidth - stageWidth) / 2;
	                 double centerYPosition = currentStageY + (currentStageHeight - stageHeight) / 2;

	                 stage.setX(centerXPosition);
	                 stage.setY(centerYPosition);
	             });
		         
		         // 새로운 장면으로 설정
		         stage.setScene(MDetailScene);
		         stage.show();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }
		
		public void processOrder(Order ord, Call call) {
	        // 메소드 내부에 itemMenuMap을 정의하고 초기화합니다.
	        Map<String, String> itemMenuMap = new HashMap<>();
	        itemMenuMap.put("spoon", "숟가락");
	        itemMenuMap.put("chopstick", "젓가락");
	        itemMenuMap.put("tableware", "유아 식기");
	        itemMenuMap.put("water", "물");
	        itemMenuMap.put("ice", "얼음");
	        itemMenuMap.put("tissue", "물티슈");
	        itemMenuMap.put("others", "기타");
	        
	        // 항목 및 수량을 매핑합니다.
	        Map<String, Integer> itemCountMap = new HashMap<>();
	        itemCountMap.put("spoon", call.getSpoon());
	        itemCountMap.put("chopstick", call.getChopstick());
	        itemCountMap.put("tableware", call.getTableware());
	        itemCountMap.put("water", call.getWater());
	        itemCountMap.put("ice", call.getIce());
	        itemCountMap.put("tissue", call.getTissue());
	        itemCountMap.put("others", call.getOthers());
	        
	        // 항목과 수량을 순회하며 처리합니다.
	        for (Map.Entry<String, Integer> entry : itemCountMap.entrySet()) {
	            String itemType = entry.getKey();
	            int itemCount = entry.getValue();
	            
	            if (itemCount > 0) {
	                Order_detail ordetail = new Order_detail();
	                ordetail.setMenu_Name(itemMenuMap.get(itemType));
	                ordetail.setOrder_Num(itemCount);
	                ord.setOrder_detail(ordetail);
	            }
	        }
	    }

		
	}//--client
	
	public static void main(String[] args) {
		new Server().startServer();
	}

}


