package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CallComplete {
	@FXML
	private Button checkButton;
	
	private Stage previousStage;
	
	// 이전 Stage를 설정하는 메서드
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }
	
	@FXML
    private void handlecheckButtonAction(ActionEvent event) {
		try {
            // 현재 창(Stage) 닫기
            Stage currentStage = (Stage) checkButton.getScene().getWindow();
            currentStage.close();

            // 이전 Stage가 존재하는 경우 다시 보여주기
            if (previousStage != null) {
                previousStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
