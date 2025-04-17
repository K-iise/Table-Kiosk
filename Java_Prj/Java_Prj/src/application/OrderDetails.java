package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderDetails {

    @FXML
    private VBox orderList;
    
    @FXML
    private Label totalLabel;

    @FXML
    private Button closeButton;

    @FXML
    private void initialize() {
        // 나중에 데이터베이스 연동 시 여기에 DB에서 데이터를 불러오는 로직 추가
        // 예시 데이터 추가
        // addOrderItem("숙성삼겹살", "2024-08-09", "17:50", 5, 19500);
        
        // 합계 설정
        // totalLabel.setText("19,500원");
    }

    private void addOrderItem(String itemName, String date, String time, int quantity, int price) {
        HBox itemBox = new HBox(10);

        // 메뉴 이름과 주문 시간을 포함하는 VBox 생성
        VBox nameAndTimeBox = new VBox(5);  // 5px의 간격을 두고 수직으로 배치
        Label nameLabel = new Label(itemName); // 메뉴 이름
        nameLabel.setStyle("-fx-font-size: 16px;");
        
        Label dateTimeLabel = new Label(date + " " + time); // 주문 시간
        dateTimeLabel.setStyle("-fx-font-size: 12px;");
        
        nameAndTimeBox.getChildren().addAll(nameLabel, dateTimeLabel);

        // 수량과 가격 라벨 생성
        Label quantityLabel = new Label(quantity + "개"); // 수량
        quantityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label priceLabel = new Label(price + "원"); // 가격
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        itemBox.getChildren().addAll(nameAndTimeBox, quantityLabel, priceLabel);
        orderList.getChildren().add(itemBox);
    }

    @FXML
    private void handlecloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
