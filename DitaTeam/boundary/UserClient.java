package boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Order_detailMgr;
import entity.DataType;
import entity.Guest;
import entity.Order_detail;
import entity.Protocol;

public class UserClient extends JFrame{
    private static final String SERVER_ADDRESS = "113.198.238.113";
    private static final int SERVER_PORT = 8003;
    private DataType dt;

    public UserClient() {
        JFrame frame = new JFrame("사용자 클라이언트");
        JPanel panel = new JPanel();
        
        JButton loginButton = new JButton("로그인");
        JButton orderDetailsButton = new JButton("주문 상세");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> GuestLogin()).start();
            }
        });

        orderDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> sendOrderDetails()).start();
            }
        });

        panel.add(loginButton);
        panel.add(orderDetailsButton);
        frame.add(panel);

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void GuestLogin() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
             
            dt = new DataType();
            Guest guest = new Guest();
            guest.setGuest_ID("guest1");
            guest.setUser_ID("user1");
            
            dt.obj = guest;
            dt.protocol = Protocol.loginGuest;
            
            out.writeObject(dt);
            out.flush();
            
            // 서버로부터 응답 대기 및 처리
            DataType response = (DataType) in.readObject();
            if (response.protocol == Protocol.loginGuest) {
                // 로그인 성공
                JOptionPane.showMessageDialog(null, "로그인 성공!");
                new AdminClient();
                this.dispose();
            } else {
                // 로그인 실패 또는 다른 응답
                JOptionPane.showMessageDialog(null, "로그인 실패 또는 다른 응답");
            }
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "서버와의 통신에 문제가 발생했습니다.");
        }
    }

    private void sendOrderDetails() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
             
            Order_detailMgr mgr = new Order_detailMgr();
            Vector<Order_detail> orderDetails = mgr.selectOrder_detail(3);

            dt = new DataType();
            dt.protocol = Protocol.order_detail;
            dt.obj = orderDetails;
            
            out.writeObject(dt);
            out.flush();
            
            // 서버로부터 응답 대기 및 처리
            DataType response = (DataType) in.readObject();
            if (response.protocol == Protocol.order_detail) {
                JOptionPane.showMessageDialog(null, "주문 상세 정보가 전송되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "주문 상세 정보 전송 실패 또는 다른 응답");
            }
            
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "서버와의 통신에 문제가 발생했습니다.");
        }
    }

    public static void main(String[] args) {
        new UserClient();
    }
}
