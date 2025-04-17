package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Start {
	
	@FXML
	private Button startButton;

    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        try {
            // Personnel.fxml 파일 로드
            Parent personnelRoot = FXMLLoader.load(getClass().getResource("RecommendMenu.fxml"));
            
            // 새로운 장면(Scene) 생성
            Scene personnelScene = new Scene(personnelRoot);
            personnelScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 장면으로 설정
            stage.setScene(personnelScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
