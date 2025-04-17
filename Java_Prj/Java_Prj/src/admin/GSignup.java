package admin;

import control.AccountMgr;
import control.GuestMgr;
import entity.AppData;
import entity.Guest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GSignup {
	
	@FXML
	private Button signupButton; // 가입하기 버튼
	
	@FXML
	private Button backButton; // 뒤로가기 버튼
	
	private Scene previousScene;
	
	public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
	
	@FXML 
	private TextField guestID;
	
	@FXML
	private Button checkButton;
	
	private GuestMgr gstMgr;
	
	private Guest gst;
	
	// 가입하기 버튼
	@FXML
    private void handleSignupButtonAction(ActionEvent event) {
		try {
			
			if(registerGuest()) {
	            Parent MIntroRoot = FXMLLoader.load(getClass().getResource("MTable.fxml"));
	            Scene MIntroScene = new Scene(MIntroRoot);
	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            stage.setScene(MIntroScene);
	            stage.show();
			}
			
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// 뒤로가기 버튼
	@FXML
    private void handleBackButtonAction(ActionEvent event) {
        if (previousScene != null) {
            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 이전 장면으로 설정
            stage.setScene(previousScene);
            stage.show();
        }
    }
	
	private boolean registerGuest() {
		gstMgr = new GuestMgr();
		gst = new Guest();
		try {
            // 중복 확인 버튼이 비활성화 되어 있는지 확인.
            if(!isCheckButtonDisabled()) {
            	showAlert("정보", "아이디 중복 검사를 해주세요.");
            	return false; // 등록 실패
            }
           
            // Guest 객체에 필드 값을 설정
            populateGuest();
            
            // DB에 Guset 정보 저장.
            if(gstMgr.insertGuset(gst))
            	return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("오류", "계정 등록 중 오류가 발생했습니다.");
        }
        
        return false; // 등록 실패
	}
	
	// 중복확인 버튼
    @FXML
    private void handleDuplicateCheckButtonAction(ActionEvent event) {
        Guest guest = new Guest();
    	String Checkid = guestID.getText();
    	guest.setGuest_ID(Checkid);
    	guest.setUser_ID(AppData.account.getUser_ID());
        gstMgr = new GuestMgr();
        
        // 확인 메시지
        Platform.runLater(() -> {
            if (gstMgr.selectGuest(guest)) {
                showAlert("정보", "아이디가 존재합니다.");
            } else {
                checkButton.setDisable(true);
                guestID.setDisable(true);
            }
        });
    }
    
    // Alert를 중앙에 표시하는 메서드
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        
        // Alert의 Stage 가져오기
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

        // 화면 중앙에 위치시키기 (안됨)
        Platform.runLater(() -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double stageWidth = alertStage.getWidth();
            double stageHeight = alertStage.getHeight();

            double x = screenBounds.getMinX() + (screenBounds.getWidth() - stageWidth) / 2;
            double y = screenBounds.getMinY() + (screenBounds.getHeight() - stageHeight) / 2;

            alertStage.setX(x);
            alertStage.setY(y);
        });
        
        // Alert을 실행하여 창의 크기를 설정합니다.
        alert.showAndWait();
    }
    
    // 중복 확인 버튼이 비활성화되어 있는지 확인
    private boolean isCheckButtonDisabled() {
        return checkButton.isDisabled();
    }
    
    // Guest 객체에 필드 값을 설정
    private void populateGuest() {
        gst.setGuest_ID(guestID.getText());
        gst.setUser_ID(AppData.account.getUser_ID());
    }
}
