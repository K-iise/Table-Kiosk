package admin;

import entity.Order;
import entity.Total_order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Management {
	@FXML
	private Button createButton; // 계정 생성 버튼
	
	@FXML
	private Button deleteButton; // 계정 삭제 버튼
	
	@FXML
	private Button backButton; // 뒤로가기 버튼
	
	private Scene previousScene;
	
    private ObservableList<Order> list = FXCollections.observableArrayList();
	
	public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
	
	// 계정 생성 버튼
	@FXML
    private void handleCreateButtonAction(ActionEvent event) {
		try {
			// 현재 장면(Scene) 가져오기
        	Scene currentScene = ((Node) event.getSource()).getScene();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GSignup.fxml"));
        	Parent GSignupRoot = loader.load();

        	// 컨트롤러 가져오기
        	GSignup signupController = loader.getController();

        	// 이전 장면을 컨트롤러에 전달
        	signupController.setPreviousScene(currentScene);

        	// 새로운 장면(Scene) 생성
        	Scene signupScene = new Scene(GSignupRoot);

        	// 현재 스테이지 가져오기
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        	// 새로운 장면으로 설정
        	stage.setScene(signupScene);
        	stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// 계정 삭제 버튼
	@FXML
	private void handleDeleteButtonAction(ActionEvent event) {
		try {
			// 현재 장면(Scene) 가져오기
        	Scene currentScene = ((Node) event.getSource()).getScene();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("GDelete.fxml"));
        	Parent GDeleteRoot = loader.load();

        	// 컨트롤러 가져오기
        	GDelete deleteController = loader.getController();

        	// 이전 장면을 컨트롤러에 전달
        	deleteController.setPreviousScene(currentScene);

        	// 새로운 장면(Scene) 생성
        	Scene deleteScene = new Scene(GDeleteRoot);

        	// 현재 스테이지 가져오기
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        	// 새로운 장면으로 설정
        	stage.setScene(deleteScene);
        	stage.show();
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
}
