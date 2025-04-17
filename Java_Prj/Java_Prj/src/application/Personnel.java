package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Personnel {

	@FXML
	private Button startButton;
	
    @FXML
    private Label countLabel;
    
    @FXML
    private Label selectLabel;

    @FXML
    private Button minusButton;

    @FXML
    private Button plusButton;

    private int count = 1;

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
    	if (count < 4) {
    		count++;
    		countLabel.setText(String.valueOf(count));
    	}
    }
    
    // 시작하기 버튼
    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        try {
            // Personnel.fxml 파일 로드
            Parent RecommendMenuRoot = FXMLLoader.load(getClass().getResource("RecommendMenu.fxml"));
            
            // 새로운 장면(Scene) 생성
            Scene RecommendMenuScene = new Scene(RecommendMenuRoot);
            
            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 장면으로 설정
            stage.setScene(RecommendMenuScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
