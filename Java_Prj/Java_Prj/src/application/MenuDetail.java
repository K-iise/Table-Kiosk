package application;

import entity.AppData;
import entity.Order_detail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuDetail {
	
	private String CtgName;
	
	private String menuName;
	
	private int price;
	
    @FXML
    private Label countLabel;
    
    @FXML
    private Button minusButton;

    @FXML
    private Button plusButton;

    @FXML
    private ImageView menuImageView;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private Button cartButton;

    @FXML
    private Label menuNameLabel;

    private int count = 1;

    public void setMenuDetails(String menuName, int price, Image menuImage, String CtgName) {
    	this.price=price;
    	this.CtgName = CtgName;
    	this.menuName = menuName;
    	
        menuNameLabel.setText(menuName + "\n" + price + "원");
        
        // 레이블이 부모 AnchorPane에서 중앙에 위치하도록 설정
        AnchorPane.setLeftAnchor(menuNameLabel, 0.0);
        AnchorPane.setRightAnchor(menuNameLabel, 0.0);
        AnchorPane.setTopAnchor(menuNameLabel, -60.0);
        AnchorPane.setBottomAnchor(menuNameLabel, 0.0);
        
        menuNameLabel.setAlignment(Pos.CENTER);  // 텍스트를 중앙 정렬
        menuNameLabel.setTextAlignment(TextAlignment.CENTER); // 텍스트를 중앙 정렬

        // 이미지 설정
        menuImageView.setImage(menuImage);
        menuImageView.setFitWidth(215);
        menuImageView.setFitHeight(240);
        menuImageView.setPreserveRatio(true);
        menuImageView.setSmooth(true);
        menuImageView.setViewport(calculateViewport(menuImage, 215, 240));
    }

    // 이미지가 꽉 차도록 Viewport 계산
    private Rectangle2D calculateViewport(Image image, double fitWidth, double fitHeight) {
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        double scaleX = fitWidth / imageWidth;
        double scaleY = fitHeight / imageHeight;
        double scale = Math.max(scaleX, scaleY); // 더 큰 비율로 확대

        double width = fitWidth / scale;
        double height = fitHeight / scale;

        double x = (imageWidth - width) / 2;
        double y = (imageHeight - height) / 2;

        return new Rectangle2D(x, y, width, height);
    }


    @FXML
    private void initialize() {
        // 초기 숫자를 표시
        countLabel.setText(String.valueOf(count));
    }

    // 마이너스 버튼
    @FXML
    private void handleMinusButtonAction(ActionEvent event) {
        if (count > 1) {
            count--;
            countLabel.setText(String.valueOf(count));
            
        }
    }

    // 플러스 버튼
    @FXML
    private void handlePlusButtonAction(ActionEvent event) {
        if (count < 10) {
            count++;
            countLabel.setText(String.valueOf(count));
        }
    }
    
    // 닫기 버튼
    @FXML
    private void handlecloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    // 장바구니 담기 버튼 (팝업 창 띄우기)
    @FXML
    private void handlecartButtonAction(ActionEvent event) {
        try {
        	
        	// ShoppingCart.fxml 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
            Parent root = loader.load();

            // ShoppingCart 컨트롤러 가져오기
            ShoppingCart shoppingCartController = loader.getController();

            // 메뉴 이름과 가격, 현재 수량 전달
            String menuName = menuNameLabel.getText().split("\n")[0]; // 메뉴 이름만 추출
            int price = Integer.parseInt(menuNameLabel.getText().split("\n")[1].replace("원", "").replace(",", "")); // 가격만 추출
            int currentCount = Integer.parseInt(countLabel.getText()); // 현재 수량을 그대로 전달

            // 장바구니에 추가
            shoppingCartController.addMenuItem(menuName, price, currentCount, CtgName);
            
            // MenuDetail 창 닫기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


}