package boundary;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.util.Vector;

import javax.swing.*;

import entity.DataType;
import entity.Order_detail;
import entity.Protocol;

public class AdminClient extends JFrame {
    private static final String SERVER_ADDRESS = "113.198.238.113";
    private static final int SERVER_PORT = 8003;
    private JTextArea ta;
    private Socket socket;
    private ObjectInputStream in;

    public AdminClient() {
        // JFrame과 JTextArea 초기화
        setLayout(new BorderLayout()); // BorderLayout 설정
        setTitle("관리자 클라이언트");
        ta = new JTextArea();
        ta.setEditable(true); // 텍스트 편집 불가 설정

        JScrollPane scrollPane = new JScrollPane(ta);
        add(scrollPane, BorderLayout.CENTER); // JTextArea를 중앙에 배치

        setSize(400, 300); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        validate();

        // 소켓 연결 및 알림 수신을 별도의 스레드에서 처리
        new Thread(this::startListening).start();
    }

    private void startListening() {
        try {
            System.out.println("Attempting to connect to server...");
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                try {
                    DataType dt = (DataType) in.readObject();
                    System.out.println("Received data from server.");
                    SwingUtilities.invokeLater(() -> {
                        ta.append(dt.protocol + "\n");
                        if (dt.protocol == Protocol.menulist) {
                            Vector<Order_detail> sc = (Vector<Order_detail>) dt.obj;
                            for (Order_detail order_detail : sc) {
                                ta.append("새 주문: " + order_detail.getMenu_Name() + "\n");
                            }
                        } else {
                            ta.append("알 수 없는 데이터 수신\n");
                        }
                    });
                } catch (ClassNotFoundException | IOException e) {
                    System.err.println("Error reading data: " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminClient();
    }
}
