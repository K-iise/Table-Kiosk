package application;

import java.io.IOException;

import entity.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AllMenu {

	@FXML
	private Button recommendButton; // 추천 메뉴 버튼
	
	@FXML
	private Button mealButton; // 식사 버튼
	
	@FXML
	private Button sideButton; // 사이드 버튼
	
	@FXML
	private Button drinkButton; // 음료 및 주류 버튼
	
	@FXML
	private Button callButton; // 직원 호출 버튼
	
	@FXML
	private Button detailsButton; // 주문 내역 버튼
	
	@FXML
	private Button cartButton; // 장바구니 버튼
	
	@FXML
	private Button menuButton; // 각 메뉴들 버튼

	private static String CtgName="추천메뉴";
	
	// 추천메뉴 버튼
    @FXML
    private void handlerecommendButtonAction(ActionEvent event) {
        try {
            Parent recommendMenuRoot = FXMLLoader.load(getClass().getResource("RecommendMenu.fxml"));
            Scene recommendMenuScene = new Scene(recommendMenuRoot);
            // css 파일 연결하면 버튼 이상
            // recommendMenuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            CtgName = "추천메뉴";
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(recommendMenuScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// 식사 버튼
    @FXML
    private void handlemealButtonAction(ActionEvent event) {
        try {
            Parent mealMenuRoot = FXMLLoader.load(getClass().getResource("MealMenu.fxml"));
            Scene mealMenuScene = new Scene(mealMenuRoot);
            // css 파일 연결하면 버튼 이상
            // mealMenuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            CtgName = "식사";
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mealMenuScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 사이드 버튼
    @FXML
    private void handlesideButtonAction(ActionEvent event) {
        try {
            Parent sideMenuRoot = FXMLLoader.load(getClass().getResource("SideMenu.fxml"));
            Scene sideMenuScene = new Scene(sideMenuRoot);
            CtgName = "사이드";
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(sideMenuScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 음료 및 주류 버튼
    @FXML
    private void handledrinkButtonAction(ActionEvent event) {
        try {
            Parent drinkMenuRoot = FXMLLoader.load(getClass().getResource("DrinkMenu.fxml"));
            Scene drinkMenuScene = new Scene(drinkMenuRoot);
            CtgName = "음료 및 주류";
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(drinkMenuScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 직원 호출 버튼
    @FXML
    private void handlecallButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffCall.fxml"));
            Parent root = loader.load();

            // 현재 창(Stage)의 위치와 크기 가져오기
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentStageX = currentStage.getX();
            double currentStageY = currentStage.getY();

            // 현재 창(Stage) 숨기기
            currentStage.hide();

            // 새로운 창(Stage) 생성
            Stage staffCallStage = new Stage();
            staffCallStage.setScene(new Scene(root));
            staffCallStage.setX(currentStageX); // 원래 창과 동일한 X 위치
            staffCallStage.setY(currentStageY); // 원래 창과 동일한 Y 위치
            staffCallStage.setTitle("E-ORDER");
            staffCallStage.show();

            // StaffCall 컨트롤러 가져오기
            StaffCall controller = loader.getController();
            controller.setPreviousStage(currentStage);  // 이전 Stage 전달

            // StaffCall 창이 닫힐 때 이전 창을 다시 보여주기
            staffCallStage.setOnHidden(e -> currentStage.show());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 주문 내역 버튼 (팝업 창 띄우기)
    @FXML
    private void handledetailsButtonAction(ActionEvent event) {
        try {
            // OrderDetails.fxml 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDetails.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED); // 타이틀 바 제거

            // Scene에 둥근 모서리 설정
            Scene scene = new Scene(root);
            Rectangle clip = new Rectangle();
            clip.setWidth(600); // Stage의 크기에 맞춰 설정
            clip.setHeight(400);
            clip.setArcWidth(30); // 둥글게 할 각도 설정
            clip.setArcHeight(30);
            root.setClip(clip);
            
            dialogStage.setScene(scene);

            // 현재 창을 블록하는 모달 창으로 설정
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // 팝업 창을 먼저 표시하여 크기를 결정
            dialogStage.show();

            // 메인 창의 Stage 가져오기
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 메인 창과 다이얼로그 창의 위치 및 크기를 사용하여 중앙에 배치
            dialogStage.setX(primaryStage.getX() + (primaryStage.getWidth() - dialogStage.getWidth()) / 2);
            dialogStage.setY(primaryStage.getY() + (primaryStage.getHeight() - dialogStage.getHeight()) / 2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 장바구니 버튼 (팝업 창 띄우기)
    @FXML
    private void handlecartButtonAction(ActionEvent event) {
        try {
            // ShoppingCart.fxml 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initStyle(StageStyle.UNDECORATED); // 타이틀 바 제거

            // Scene 설정
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            // 현재 창을 블록하는 모달 창으로 설정
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // 메인 창의 Stage 가져오기
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 팝업 창을 먼저 표시하여 크기를 결정
            dialogStage.show();

            // 우측 하단 모서리에 맞추기 (미세 조정 포함)
            double dialogStageWidth = dialogStage.getWidth();
            double dialogStageHeight = dialogStage.getHeight();
            double xOffset = -8;  // X축으로 5픽셀 왼쪽으로 이동
            double yOffset = -8; // Y축으로 5픽셀 위쪽으로 이동

            dialogStage.setX(primaryStage.getX() + primaryStage.getWidth() - dialogStageWidth + xOffset);
            dialogStage.setY(primaryStage.getY() + primaryStage.getHeight() - dialogStageHeight + yOffset);

            // 팝업 창을 닫았을 때 크기가 변경되는 경우에도 위치를 재조정
            dialogStage.setOnShown(ev -> {
                dialogStage.setX(primaryStage.getX() + primaryStage.getWidth() - dialogStage.getWidth() + xOffset);
                dialogStage.setY(primaryStage.getY() + primaryStage.getHeight() - dialogStage.getHeight() + yOffset);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // 각 메뉴버튼 ( 팝업 창 띄우기)
    // 이미지 URL 값으로 버튼을 구분해야 하기 때문에 onMouseClicked 사용
    @FXML
    private void handlemenuButtonClick(MouseEvent event) {
        try {
        	ImageView clickedImage = (ImageView) ((Button) event.getSource()).getGraphic();
            String imageName = clickedImage.getImage().getUrl();

            // 파일 경로에서 확장자 제거
            String baseName = imageName.substring(0, imageName.lastIndexOf('.'));
            String extension = imageName.substring(imageName.lastIndexOf('.'));
            
            // 파일 이름에 '_사진' 추가해서 삽입
            String menuImageName = baseName + "_사진" + extension;

            Image menuImage = new Image(menuImageName);

            // 이미지 경로에 따른 메뉴 이름과 가격 설정
            String menuName = "";
            int price = 0;

            if (imageName.contains("숙성삼겹살")) {
                menuName = "숙성삼겹살";
                price = 3900;
            } else if (imageName.contains("이베리코꽃목살")) {
                menuName = "이베리코꽃목살";
                price = 6400;
            } else if (imageName.contains("돼지갈비")) {
                menuName = "돼지갈비";
                price = 4400;
            } else if (imageName.contains("꽃삼겹대패")) {
                menuName = "꽃삼겹대패";
                price = 6900;
            } else if (imageName.contains("생막창")) {
                menuName = "생막창";
                price = 5400;
            } else if (imageName.contains("꼬들살")) {
                menuName = "꼬들살";
                price = 5900;
            } else if (imageName.contains("벌집껍데기")) {
                menuName = "벌집껍데기";
                price = 3900;
            }  else if (imageName.contains("매운껍데기")) {
                menuName = "벌집껍데기";
                price = 3900;
            }  else if (imageName.contains("차돌된장찌개")) {
                menuName = "차돌된장찌개";
                price = 5000;
            }  else if (imageName.contains("돼지김치찌개")) {
                menuName = "돼지김치찌개";
                price = 5000;
            }  else if (imageName.contains("얼큰라면")) {
                menuName = "얼큰라면";
                price = 4000;
            }  else if (imageName.contains("도시락볶음밥")) {
                menuName = "도시락볶음밥";
                price = 6000;
            }  else if (imageName.contains("물냉면")) {
                menuName = "물냉면";
                price = 6000;
            }  else if (imageName.contains("비빔냉면")) {
                menuName = "비빔냉면";
                price = 6000;
            }  else if (imageName.contains("계란찜")) {
                menuName = "계란찜";
                price = 3000;
            }  else if (imageName.contains("콜라")) {
                menuName = "콜라";
                price = 2000;
            }   else if (imageName.contains("사이다")) {
                menuName = "사이다";
                price = 2000;
            }   else if (imageName.contains("환타")) {
                menuName = "환타";
                price = 2000;
            }   else if (imageName.contains("소주")) {
                menuName = "소주";
                price = 4000;
            }   else if (imageName.contains("맥주")) {
                menuName = "맥주";
                price = 4000;
            }   else if (imageName.contains("청하")) {
                menuName = "청하";
                price = 4000;
            }
     
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuDetail.fxml"));
            Parent root = loader.load();

            // 컨트롤러에 메뉴 정보 설정
            MenuDetail controller = loader.getController();
            controller.setMenuDetails(menuName, price, menuImage, CtgName);

            // 새로운 Stage로 창 띄우기
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); // 타이틀 바 제거
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            
            // 메인 창의 Stage 가져오기
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 메인 창과 다이얼로그 창의 위치 및 크기를 사용하여 중앙에 배치
            stage.setX(primaryStage.getX() + (primaryStage.getWidth() - stage.getWidth()) / 2);
            stage.setY(primaryStage.getY() + (primaryStage.getHeight() - stage.getHeight()) / 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


