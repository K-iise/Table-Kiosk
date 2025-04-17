package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entity.AppData;
import entity.Call;
import entity.DataType;
import entity.Guest;
import entity.Protocol;
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

public class GLogin {
	@FXML
	private Button loginButton; // 로그인 버튼
	
	@FXML
	private TextField guestID;
	
	private DataType dt;
	
	private Guest guest;
	// 로그인 버튼
	@FXML
    private void handleLoginButtonAction(ActionEvent event) {
		try {
			if(requestLogin()) {
            Parent StartRoot = FXMLLoader.load(getClass().getResource("Start.fxml"));
            Scene StartScene = new Scene(StartRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(StartScene);
            stage.show();
            }
			else {
				showAlert("정보", "일치하는 게스트 아이디가 없습니다.");
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private boolean requestLogin() {		
		try {
			Socket socket = new Socket(Main.SERVER_ADDRESS, Main.SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            dt = new DataType();
            dt.protocol = Protocol.loginGuest;
            AppData.guest.setGuest_ID(guestID.getText());
        	
        	dt.obj = AppData.guest;
            out.writeObject(dt);
            out.flush();
            
            // 서버로부터 응답 대기 및 처리
            DataType response = null;
            while(response == null) {
            	response = (DataType) in.readObject();
            }
            
            // 게스트 정보 받아오기.
            AppData.guest = (Guest) response.obj;
            
            if(AppData.guest.getUser_ID() == null) {
            	return false;
            }
            
            return true;
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
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
}
