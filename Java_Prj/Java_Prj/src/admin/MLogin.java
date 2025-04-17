package admin;

import control.AccountMgr;
import entity.Account;
import entity.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MLogin {

	@FXML
	private Button loginButton;
	
	@FXML
	private Button signupButton;
	
	@FXML
	private TextField userID;
	
	@FXML
	private PasswordField userPW;
	
	private AccountMgr accMgr;
	
	// 로그인 버튼 -> 게스트 목록 페이지로 이동 / 주석 처리된 곳 수정해야함.
	// fxml 파일의 해당 버튼에 onAction="#이벤트 핸들러 함수명" 추가해야함.
	@FXML
    private void handleLoginButtonAction(ActionEvent event) {
        try {
        	
        	AppData.account.setUser_ID(userID.getText());
        	AppData.account.setUser_PW(userPW.getText());
        	accMgr = new AccountMgr();
        	
	        if(accMgr.selectAccount(AppData.account)) {
	        	
		        // Personnel.fxml 파일 로드
		        Parent MTableRoot = FXMLLoader.load(getClass().getResource("MTable.fxml"));
		            
		        // 새로운 장면(Scene) 생성
		        Scene MTableScene = new Scene(MTableRoot);
		            
		        // 현재 스테이지 가져오기
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		            
		        // 새로운 장면으로 설정
		        stage.setScene(MTableScene);
		        stage.show();
		        
		        MTable.me = MTableScene.getWindow();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// 회원가입 버튼
	@FXML
    private void handleSignupButtonAction(ActionEvent event) {
        try {
        	// 현재 장면(Scene) 가져오기
        	Scene currentScene = ((Node) event.getSource()).getScene();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("MSignup.fxml"));
        	Parent MSignupRoot = loader.load();

        	// 컨트롤러 가져오기
        	MSignup signupController = loader.getController();

        	// 이전 장면을 컨트롤러에 전달
        	signupController.setPreviousScene(currentScene);

        	// 새로운 장면(Scene) 생성
        	Scene signupScene = new Scene(MSignupRoot);

        	// 현재 스테이지 가져오기
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        	// 새로운 장면으로 설정
        	stage.setScene(signupScene);
        	stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}
