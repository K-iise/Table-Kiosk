package admin;

import control.AccountMgr;
import entity.Account;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class MSignup {
	@FXML
	private Button signupButton;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button checkButton;
	
	private Scene previousScene;
	
	@FXML
    private TextField userID;
    
    @FXML
    private PasswordField userPW;

    @FXML
    private PasswordField pwCheck;
    
    @FXML
    private TextField userBranch;
    
    @FXML
    private TextField userAddress;

    private AccountMgr accMgr;
    
    private Account acc;
	
	public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
	
	// 가입하기 버튼
	@FXML
    private void handleSignupButtonAction(ActionEvent event) {
        try {
        	if(registerAccount()) {
	            // MIntro.fxml 파일 로드
	            Parent MIntroRoot = FXMLLoader.load(getClass().getResource("MIntro.fxml"));
	            
	            // 새로운 장면(Scene) 생성
	            Scene MIntroScene = new Scene(MIntroRoot);
	            
	            // 현재 스테이지 가져오기
	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            
	            // 새로운 장면으로 설정
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
	
	// 중복확인 버튼
    @FXML
    private void handleDuplicateCheckButtonAction(ActionEvent event) {
        String Checkid = userID.getText();
        accMgr = new AccountMgr();
        
        // 확인 메시지
        Platform.runLater(() -> {
            if (accMgr.selectUserID(Checkid)) {
                showAlert("정보", "아이디가 존재합니다.");
            } 
            
            else if(Checkid.isEmpty()) {
            	showAlert("정보", "아이디를 입력해주세요.");
            	return;
            }
            else {
                checkButton.setDisable(true);
                userID.setDisable(true);
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
    
    private boolean registerAccount() {
        accMgr = new AccountMgr();
        acc = new Account();

        try {
            // 모든 입력 필드가 채워졌는지 확인
            if (!areFieldsFilled()) {
                showAlert("정보", "모든 정보를 입력해주세요.");
                return false; // 등록 실패
            }
            // 중복 확인 버튼이 비활성화 되어 있는지 확인.
            if(!isCheckButtonDisabled()) {
            	showAlert("정보", "아이디 중복 검사를 해주세요.");
            	return false; // 등록 실패
            }
            // 비밀번호가 일치하는지 확인
            if(!isPasswordValid()) {
            	showAlert("정보", "비밀번호가 일치하지 않습니다.");
            	return false; // 등록 실패
            }
            
            //Account 객체에 필드 값을 설정
            populateAccount();
            // DB에 Account 정보 저장.
            accMgr.insertAccount(acc);
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("오류", "계정 등록 중 오류가 발생했습니다.");
        }
        
        return true; // 등록 성공
    }
    

    // 모든 필드가 채워졌는지 확인
    private boolean areFieldsFilled() {
        return !userID.getText().isEmpty() &&
               !userPW.getText().isEmpty() &&
               !userBranch.getText().isEmpty() &&
               !userAddress.getText().isEmpty();
    }
    
    // 중복 확인 버튼이 비활성화되어 있는지 확인
    private boolean isCheckButtonDisabled() {
        return checkButton.isDisabled();
    }
    
    // 비밀번호가 일치하는지 확인.
    private boolean isPasswordValid() {
        return pwCheck.getText().equals(userPW.getText());
    }
    
    // Account 객체에 필드 값을 설정
    private void populateAccount() {
        acc.setUser_ID(userID.getText());
        acc.setUser_PW(userPW.getText());
        acc.setUser_Branch(userBranch.getText());
        acc.setUser_Address(userAddress.getText());
    }
    
}

