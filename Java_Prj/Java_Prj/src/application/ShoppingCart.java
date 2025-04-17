package application;

import java.util.ArrayList;
import java.util.List;

import entity.AppData;
import entity.Order_detail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShoppingCart {

    @FXML
    private Button closeButton; // 닫기 버튼

    @FXML
    private Button orderButton; // 주문하기 버튼

    @FXML
    private Label emptyLabel; // 장바구니 비었을 때 라벨
    
    @FXML
    private VBox cartItemsContainer; // 장바구니 항목을 담는 컨테이너
    
    @FXML
    private Label totalPriceLabel; // 총 금액을 표시하는 라벨
    
    // 장바구니 항목
    private static class CartItem {
    	
        private String menuName;
        private int price;
        private int count;
        private String ctgName;
        
        public CartItem(String menuName, int price, int count, String ctgName) {
            this.menuName = menuName;
            this.price = price;
            this.count = count;
            this.ctgName = ctgName;
        }

        public String getMenuName() {
            return menuName;
        }

        public int getPrice() {
            return price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

		public String getCtgName() {
			return ctgName;
		}

        
        
    }

    private static List<CartItem> cartItems = new ArrayList<>(); // 장바구니 항목을 관리하는 리스트
    
    // 장바구니 초기화
    public static void clearCart() {
        cartItems.clear();
    }

    // 기본
    @FXML
    private void initialize() {
        if (cartItems.isEmpty()) {
            emptyLabel.setVisible(true);
            cartItemsContainer.setVisible(false);
            totalPriceLabel.setText("총 금액: 0원");
        } else {
            emptyLabel.setVisible(false);
            displayCartItems();
        }
    }

    // 장바구니 항목을 UI에 표시
    private void displayCartItems() {
        cartItemsContainer.getChildren().clear();
        int totalPrice = 0;

        for (CartItem item : cartItems) {
            AnchorPane itemContainer = new AnchorPane();

            // Label 생성
            Label itemLabel = new Label(String.format("%s\n%,d원", item.getMenuName(), item.getPrice()));
            itemLabel.setLayoutX(10.0);
            itemLabel.setLayoutY(10.0);
            itemLabel.setStyle("-fx-font-size: 22px;");

            // 수량 Label 생성
            Label countLabel = new Label(String.valueOf(item.getCount()));
            countLabel.setLayoutX(235.0);
            countLabel.setLayoutY(10.0);
            countLabel.setStyle("-fx-font-size: 22px;");

            // 마이너스 버튼 생성
            Button minusButton = new Button("-");
            minusButton.setLayoutX(180.0);
            minusButton.setLayoutY(5.0);
            minusButton.setPrefSize(38, 38);
            minusButton.setStyle("-fx-background-radius: 50;");
            minusButton.setOnAction(event -> handleMinusButtonAction(event, item, countLabel));

            // 플러스 버튼 생성
            Button plusButton = new Button("+");
            plusButton.setLayoutX(262.0);
            plusButton.setLayoutY(5.0);
            plusButton.setPrefSize(38, 38);
            plusButton.setStyle("-fx-background-radius: 50;");
            plusButton.setOnAction(event -> handlePlusButtonAction(event, item, countLabel));

            // 삭제 버튼 생성
            Button deleteButton = new Button("삭제");
            deleteButton.setLayoutX(329.0);
            deleteButton.setLayoutY(8.0);
            deleteButton.setPrefSize(62, 29);
            deleteButton.setStyle("-fx-background-radius: 20;");
            deleteButton.setOnAction(event -> handleDeleteButtonAction(event, itemContainer, item));

            // AnchorPane에 요소 추가
            itemContainer.getChildren().addAll(itemLabel, minusButton, countLabel, plusButton, deleteButton);
            itemContainer.setPrefSize(400, 50);
            cartItemsContainer.getChildren().add(itemContainer);

            // 항목 금액을 총 금액에 더하기
            totalPrice += item.getPrice() * item.getCount();
        }

        cartItemsContainer.setVisible(true);
        totalPriceLabel.setText(String.format("총 금액: %,d원", totalPrice));
    }

    public void addMenuItem(String menuName, int price, int count, String CtgName) {
        for (CartItem item : cartItems) {
            if (item.getMenuName().equals(menuName)) {
                item.setCount(item.getCount() + count);
                displayCartItems();
                return;
            }
        }
        cartItems.add(new CartItem(menuName, price, count, CtgName));
        displayCartItems();
    }

    // 마이너스 버튼 이벤트
    private void handleMinusButtonAction(ActionEvent event, CartItem item, Label countLabel) {
        if (item.getCount() > 1) {
            item.setCount(item.getCount() - 1);
            countLabel.setText(String.valueOf(item.getCount()));
            displayCartItems();
        }
    }

    // 플러스 버튼 이벤트
    private void handlePlusButtonAction(ActionEvent event, CartItem item, Label countLabel) {
        if (item.getCount() < 10) {
            item.setCount(item.getCount() + 1);
            countLabel.setText(String.valueOf(item.getCount()));
            displayCartItems();
        }
    }

    // 삭제 버튼 이벤트
    private void handleDeleteButtonAction(ActionEvent event, AnchorPane itemContainer, CartItem item) {
        cartItems.remove(item);
        cartItemsContainer.getChildren().remove(itemContainer);
        displayCartItems();
    }

    // 닫기 버튼 이벤트
    @FXML
    private void handlecloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // 주문하기 버튼 이벤트
    @FXML
    private void handleorderButtonAction(ActionEvent event) {
        if (cartItems.isEmpty()) {
            showAlert("장바구니 확인", "장바구니가 비었습니다.");
        } else {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderCheck.fxml"));
                Parent root = loader.load();

                OrderCheck orderCheckController = loader.getController();

                // 장바구니에서 모든 항목을 전달
                for (CartItem item : cartItems) {
                    int itemTotalPrice = item.getCount() * item.getPrice();
                    // 각각의 메뉴를 OrderCheck에 전달
                    orderCheckController.setOrderDetails(
                        item.getMenuName(),
                        item.getCount(),
                        itemTotalPrice
                    );
                }
                
                insertOrder_detail();
                
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED); // 타이틀 바 제거
                stage.setScene(new Scene(root));

                // 모달 윈도우로 설정하여 메인 창의 앞에 뜨도록 함
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(currentStage);

                Stage mainStage = Main.getPrimaryStage();
                double mainStageX = mainStage.getX();
                double mainStageY = mainStage.getY();
                double mainStageWidth = mainStage.getWidth();
                double mainStageHeight = mainStage.getHeight();

                stage.setOnShown(e -> {
                    double stageWidth = stage.getWidth();
                    double stageHeight = stage.getHeight();
                    stage.setX(mainStageX + (mainStageWidth - stageWidth) / 2);
                    stage.setY(mainStageY + (mainStageHeight - stageHeight) / 2);
                });

                stage.toFront();
                stage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void insertOrder_detail() {
    	// 장바구니의 품목들을 상세 메뉴에 옮기기.
    	for (CartItem cartItem : cartItems) {
    		Order_detail od=new Order_detail();
    		od.setMenu_Name(cartItem.getMenuName());
    		od.setCtg_Name(cartItem.getCtgName());
    		od.setOrder_Num(cartItem.getCount());
    		AppData.order.setOrder_detail(od);
		}
    }

    // 장바구니 비었을 때 경고창
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(orderButton.getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        
        // 다이얼로그 크기 먼저 계산
        alert.getDialogPane().applyCss();
        double dialogWidth = alert.getDialogPane().getWidth();
        double dialogHeight = alert.getDialogPane().getHeight();

        // 메인 스테이지 위치와 크기 가져오기
        Stage mainStage = Main.getPrimaryStage();
        double mainStageX = mainStage.getX();
        double mainStageY = mainStage.getY();
        double mainStageWidth = mainStage.getWidth();
        double mainStageHeight = mainStage.getHeight();
        double xOffset = -50;
        double yOffset = -70;

        // 다이얼로그를 메인 스테이지 중앙에 위치시키기
        alert.setX(mainStageX + (mainStageWidth - dialogWidth) / 2 + xOffset);
        alert.setY(mainStageY + (mainStageHeight - dialogHeight) / 2 + yOffset);
        alert.showAndWait();
    }
}
