package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderComplete {

	@FXML
	private Button checkButton;
	
	// 확인 버튼
    @FXML
    private void handlecheckButtonAction(ActionEvent event) {
        try {
            Parent checkRoot = FXMLLoader.load(getClass().getResource("RecommendMenu.fxml"));
            Scene checkScene = new Scene(checkRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(checkScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}