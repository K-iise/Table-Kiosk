package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import entity.AppData;
import entity.Call;
import entity.DataType;
import entity.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StaffCall {

	
    @FXML
    private Button closeButton;

    @FXML
    private Button requestButton;

    @FXML
    private AnchorPane itemPane;  // 선택된 항목들이 추가될 패널

    @FXML
    private ScrollPane scrollPane;  // 스크롤 기능을 위한 패널

    private Stage previousStage;
    private int currentItemYPosition = 0; // 초기 Y 위치 조정
    
    // 각 버튼에 대해 생성된 항목을 추적하기 위한 Map
    private Map<String, Node[]> selectedItemsMap = new HashMap<>();
    
    private DataType dt;
    
    private Call call;
    
    boolean flag = false;
    
    // 이전 Stage를 설정하는 메서드
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    
    @FXML
    private void initialize() {
        // ScrollPane의 배경색을 검정색으로 설정
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: black; -fx-control-inner-background: black;");
        
        // ScrollPane 내부의 Viewport 배경을 검정색으로 설정
        itemPane.setStyle("-fx-background-color: black;");
        
        // ScrollBar 자체 스타일 조정 (원할 경우)
        Node verticalScrollBar = scrollPane.lookup(".scroll-bar:vertical");
        Node horizontalScrollBar = scrollPane.lookup(".scroll-bar:horizontal");

        if (verticalScrollBar != null) {
            verticalScrollBar.setStyle("-fx-background-color: transparent;");
        }

        if (horizontalScrollBar != null) {
            horizontalScrollBar.setStyle("-fx-background-color: transparent;");
        }
    }
    
    // 닫기 버튼
    @FXML
    private void handlecloseButtonAction(ActionEvent event) {
        try {
            // 현재 창(Stage) 닫기
            Stage currentStage = (Stage) closeButton.getScene().getWindow();
            currentStage.close();

            // 이전 Stage가 존재하는 경우 다시 보여주기
            if (previousStage != null) {
                previousStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 요청하기 버튼
    @FXML
    private void handlerequestButtonAction(ActionEvent event) {
        try {
        	
        	if(requestUpdate(selectedItemsMap)) {

	        Parent requestRoot = FXMLLoader.load(getClass().getResource("CallComplete.fxml"));
	        Scene requestScene = new Scene(requestRoot);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(requestScene);
	        stage.show();
	        
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 각 항목 선택 처리
    @FXML
    private void handleItemSelection(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        String selectedItem = selectedButton.getText();

        if (selectedItemsMap.containsKey(selectedItem)) {
            // 이미 선택된 항목이 있으면 삭제
            Node[] nodes = selectedItemsMap.get(selectedItem);
            itemPane.getChildren().removeAll(nodes);
            selectedItemsMap.remove(selectedItem);

            // 현재 아이템의 위치를 조정하기 위해 다른 아이템을 아래로 이동
            for (Node[] itemNodes : selectedItemsMap.values()) {
                if (itemNodes[0].getLayoutY() > nodes[0].getLayoutY()) {
                    for (Node node : itemNodes) {
                        node.setLayoutY(node.getLayoutY() - 80);
                    }
                }
            }
            currentItemYPosition -= 80; // Y 위치도 줄임
            selectedButton.setStyle("-fx-background-color: white; -fx-background-radius: 10;"); // 선택 해제 시 원래 색상으로 복원
        } else {
            double paneWidth = scrollPane.getPrefWidth();
            double buttonWidth = 30.0; // 버튼의 가로 크기

            // 중앙에 위치하기 위한 X 좌표 계산
            double labelXPosition = (paneWidth - 80.0) / 2;
            double buttonXPosition = (paneWidth - (2 * buttonWidth + 50.0)) / 2;

            // 선택된 항목의 이름 라벨 생성
            Label itemLabel = new Label(selectedItem);
            itemLabel.setPrefWidth(paneWidth); // 라벨의 너비를 ScrollPane 너비로 설정
            itemLabel.setLayoutX(0); // X 위치를 0으로 설정하여 전체 영역 사용
            itemLabel.setLayoutY(currentItemYPosition + 5); // 위치를 더 위로 조정
            itemLabel.setTextFill(javafx.scene.paint.Color.WHITE);
            itemLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center; -fx-text-alignment: center;");
            itemLabel.setAlignment(javafx.geometry.Pos.CENTER); // 텍스트를 중앙 정렬

            // 수량 라벨 생성
            Label quantityLabel = new Label("1");
            quantityLabel.setLayoutX(buttonXPosition + buttonWidth + 20); // 버튼 다음 위치에 라벨 위치
            quantityLabel.setLayoutY(currentItemYPosition + 35); // 위치를 더 위로 조정
            quantityLabel.setTextFill(javafx.scene.paint.Color.WHITE);
            quantityLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            // 마이너스 버튼 생성
            Button minusButton = new Button("-");
            minusButton.setLayoutX(buttonXPosition);
            minusButton.setLayoutY(currentItemYPosition + 35); // 위치를 더 위로 조정
            minusButton.setPrefSize(buttonWidth, 30);
            minusButton.setStyle("-fx-background-color: gray;");
            minusButton.setOnAction(e -> handleMinusButtonAction(quantityLabel));

            // 플러스 버튼 생성
            Button plusButton = new Button("+");
            plusButton.setLayoutX(buttonXPosition + buttonWidth + 50);
            plusButton.setLayoutY(currentItemYPosition + 35); // 위치를 더 위로 조정
            plusButton.setPrefSize(buttonWidth, 30);
            plusButton.setStyle("-fx-background-color: gray;");
            plusButton.setOnAction(e -> handlePlusButtonAction(quantityLabel));

            // AnchorPane에 추가
            itemPane.getChildren().addAll(itemLabel, minusButton, quantityLabel, plusButton);

            // 선택된 항목을 Map에 추가
            selectedItemsMap.put(selectedItem, new Node[]{itemLabel, minusButton, quantityLabel, plusButton});

            // 다음 아이템의 Y 위치 조정
            currentItemYPosition += 80; // 각 항목의 높이만큼 추가 조정

            selectedButton.setStyle("-fx-background-color: gray; -fx-background-radius: 10;"); // 선택된 상태를 회색으로 표시
        }

        // 스크롤 영역 크기 조정
        adjustScrollPane();
    }

    // 스크롤 영역 크기 조정 메서드
    private void adjustScrollPane() {
        itemPane.setPrefHeight(currentItemYPosition + 10);
        itemPane.setPrefWidth(scrollPane.getPrefWidth()); // ScrollPane 너비에 맞추기
    }

    // 수량 감소 버튼
    private void handleMinusButtonAction(Label quantityLabel) {
        int quantity = Integer.parseInt(quantityLabel.getText());
        if (quantity > 1) {
            quantity--;
            quantityLabel.setText(String.valueOf(quantity));
        }
    }

    // 수량 증가 버튼
    private void handlePlusButtonAction(Label quantityLabel) {
        int quantity = Integer.parseInt(quantityLabel.getText());
        if (quantity < 10) {
            quantity++;
            quantityLabel.setText(String.valueOf(quantity));
        }
    }
    
    // 요청사항 전달.
    private boolean requestUpdate(Map<String, Node[]> selectedItemsMap) {
    	
    	try {
    		
    		Socket socket = new Socket(Main.SERVER_ADDRESS, Main.SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            dt = new DataType();
            dt.protocol = Protocol.call;
        	call = new Call();
        	call.setGuestID(AppData.guest.getGuest_ID());
        	
            // Map의 각 항목을 순회합니다.
            for (Map.Entry<String, Node[]> entry : selectedItemsMap.entrySet()) {
                // 항목 이름 (예: "숟가락", "젓가락" 등)
                String itemName = entry.getKey();
                
                // 해당 항목에 대한 Node 배열
                Node[] nodes = entry.getValue();
                
                // 배열에서 개별 Node에 접근할 수 있습니다.
                Label itemLabel = (Label) nodes[0];
                Button minusButton = (Button) nodes[1];
                Label quantityLabel = (Label) nodes[2];
                Button plusButton = (Button) nodes[3];
                
                switch (itemName) {
                	case "숟가락":
                		call.setSpoon(Integer.parseInt(quantityLabel.getText()));
                		break;
                	case "젓가락":
                		call.setChopstick(Integer.parseInt(quantityLabel.getText()));
                		break;
                	case "유아 식기":
                		call.setTableware(Integer.parseInt(quantityLabel.getText()));
                		break;
                	case "얼음":
                		call.setIce(Integer.parseInt(quantityLabel.getText()));
                		break;
                	case "물":
                		call.setWater(Integer.parseInt(quantityLabel.getText()));
                		break;
                	case "물티슈":
                		call.setTissue(Integer.parseInt(quantityLabel.getText()));
                		break;
                	case "기타":
                		call.setOthers(Integer.parseInt(quantityLabel.getText()));
                		break;
                }
                
            }
            dt.obj = call;
            out.writeObject(dt);
            out.flush();
            
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return true;

    }
}
