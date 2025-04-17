package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MTable {
	
	public static Window me;
	
	@FXML 
	private AnchorPane anchorPane;
	
	@FXML
	private Button logOutButton;
	
	@FXML
	private Button mgButton;
	
	@FXML
	private Button salesButton;
	
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
	
	// 테이블 버튼 -> 주문 목록(MOrder) 페이지로 이동
	// 각 테이블의 번호를 이용해 해당 계정의 주문 목록을 표시해야함.
	@FXML
    private void handleTableButtonAction(ActionEvent event) {
        try {
        	Button button;
        	String buttonId = "";
        	// 이벤트 소스가 Button 타입인지 확인
            Node source = (Node) event.getSource();
            if (source instanceof Button) {
                button = (Button) source;
                
                // 버튼의 ID를 가져오기
                buttonId = button.getText();  // 또는 button.getText()로 버튼의 이름 가져오기

                //System.out.println("Button ID: " + buttonId);
            }
        	
        	
            
	         // fxml 파일 로드
	    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("MOrder_detail.fxml"));
	         Parent MDetailRoot = loader.load();

	         WindowController request = loader.getController();
	         
	         request.updateOrderQ(buttonId);
            
            // 새로운 장면(Scene) 생성
            Scene MOrderScene = new Scene(MDetailRoot);
            
            // 현재 이벤트의 소스에서 현재 스테이지 가져오기
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            
            // 화면 중앙에 위치시키기
            stage.setOnShown(e -> {
                double stageWidth = stage.getWidth();
                double stageHeight = stage.getHeight();
                double currentStageX = currentStage.getX();
                double currentStageY = currentStage.getY();
                double currentStageWidth = currentStage.getWidth();
                double currentStageHeight = currentStage.getHeight();
                
                // 중앙 위치 계산
                double centerXPosition = currentStageX + (currentStageWidth - stageWidth) / 2;
                double centerYPosition = currentStageY + (currentStageHeight - stageHeight) / 2;

                stage.setX(centerXPosition);
                stage.setY(centerYPosition);
            });
            stage.initOwner(anchorPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            // 새로운 장면으로 설정
            stage.setScene(MOrderScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// 로그아웃 버튼
	@FXML
    private void handleLogOutButtonAction(ActionEvent event) {
        try {
            // MIntro.fxml 파일 로드
            Parent MIntroRoot = FXMLLoader.load(getClass().getResource("MIntro.fxml"));
            
            // 새로운 장면(Scene) 생성
            Scene MIntroScene = new Scene(MIntroRoot);
            
            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 장면으로 설정
            stage.setScene(MIntroScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// 계정 관리 버튼
	@FXML
    private void handleMgButtonAction(ActionEvent event) {
		try {
			// 현재 장면(Scene) 가져오기
        	Scene currentScene = ((Node) event.getSource()).getScene();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("Management.fxml"));
        	Parent ManagementRoot = loader.load();

        	// 컨트롤러 가져오기
        	Management signupController = loader.getController();

        	// 이전 장면을 컨트롤러에 전달
        	signupController.setPreviousScene(currentScene);

        	// 새로운 장면(Scene) 생성
        	Scene managementScene = new Scene(ManagementRoot);

        	// 현재 스테이지 가져오기
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        	// 새로운 장면으로 설정
        	stage.setScene(managementScene);
        	stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// 전체 매출 버튼
	@FXML
    private void handleSalesButtonAction(ActionEvent event) {
		try {
			// 현재 장면(Scene) 가져오기
        	Scene currentScene = ((Node) event.getSource()).getScene();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("TotalSales.fxml"));
        	Parent TotalSalesRoot = loader.load();

        	// 컨트롤러 가져오기
        	TotalSales totalSalesController = loader.getController();

        	// 이전 장면을 컨트롤러에 전달
        	totalSalesController.setPreviousScene(currentScene);

        	// 새로운 장면(Scene) 생성
        	Scene totalSalesScene = new Scene(TotalSalesRoot);

        	// 현재 스테이지 가져오기
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        	// 새로운 장면으로 설정
        	stage.setScene(totalSalesScene);
        	stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

}
