package admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.Dimension;
import java.awt.Toolkit;

import entity.Account;
import javafx.scene.text.Font;

public class Main extends Application {
    
    private static Stage primaryStage; // 메인 창의 참조를 저장할 정적 변수
    public static String SERVER_ADDRESS = "localhost";
    public static int SERVER_PORT = 8003;
    private Server server; // 서버 인스턴스 추가
    @Override
    public void start(Stage primaryStage) {
        try {
            // 메인 창의 참조를 저장
            Main.primaryStage = primaryStage;
			Font.loadFont(getClass().getResourceAsStream("Inter-VariableFont_opsz,wght.ttf"), 10);
            Parent root = FXMLLoader.load(getClass().getResource("MIntro.fxml"));
            Scene scene = new Scene(root);
            
            // 모든 Scene에 공통 CSS 파일 적용
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("E-ORDER");
            primaryStage.setResizable(false);

            // 창을 먼저 보여줘야 정확한 크기를 얻을 수 있음
            primaryStage.show();

            // 화면의 해상도 가져오기
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double screenWidth = screenSize.getWidth();
            double screenHeight = screenSize.getHeight();

            // 창의 크기 가져오기
            double stageWidth = primaryStage.getWidth();
            double stageHeight = primaryStage.getHeight();

            // 창을 화면의 중앙에 위치시키기
            primaryStage.setX((screenWidth - stageWidth) / 2);
            primaryStage.setY((screenHeight - stageHeight) / 2);
            
            // 서버를 별도의 스레드에서 실행
            new Thread(() -> {
                server = new Server();
                server.startServer();
            }).start();

        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    // 메인 창의 참조를 반환하는 정적 메서드
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}
