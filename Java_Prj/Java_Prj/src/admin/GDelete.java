package admin;

import java.util.Optional;

import control.GuestMgr;
import control.OrderMgr;
import entity.AppData;
import entity.Guest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GDelete {
	
	@FXML
	private Button tbl1_Btn;
	
	@FXML
	private Button tbl2_Btn;
	
	@FXML
	private Button tbl3_Btn;
	
	@FXML
	private Button tbl4_Btn;
	
	@FXML
	private Button tbl5_Btn;
	
	@FXML
	private Button tbl6_Btn;
	
	@FXML
	private Button backButton; // 뒤로가기 버튼
	
	private Scene previousScene;
	
	public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
	
	@FXML
    private void handleTableButtonAction(ActionEvent event) {
        // 삭제 확인 다이얼로그 생성
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("계정 삭제");
        alert.setHeaderText("이 계정을 삭제하시겠습니까?");
        alert.setContentText("삭제하시려면 '확인'을 클릭하세요.");

        // 다이얼로그에서 사용자가 선택한 결과 기다리기
        Optional<ButtonType> result = alert.showAndWait();
        String BtnId = "";
        // '확인' 버튼을 클릭했을 때
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Button button = (Button) event.getSource();
            
            BtnId = button.getText();
                       
	        // 버튼의 부모 노드를 가져와서
	        Pane parent = (Pane) button.getParent();
	
	        // 부모 노드에서 해당 버튼을 삭제
	        parent.getChildren().remove(button);
            
         
        }
        
        Guest guest = new Guest();
        guest.setUser_ID(AppData.account.getUser_ID());
        guest.setGuest_ID(BtnId);
        
        System.out.println(guest.getGuest_ID());
        System.out.println(guest.getUser_ID());
        
        
        OrderMgr ordMgr = new OrderMgr();
        GuestMgr guestMgr = new GuestMgr();
        
		System.out.println(ordMgr.updateOrder(guest));
		guestMgr.deleteGuest(guest);
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
}
