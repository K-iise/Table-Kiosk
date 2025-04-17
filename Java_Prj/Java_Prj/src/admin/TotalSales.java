package admin;

import java.util.Vector;
import control.OrderMgr;
import entity.AppData;
import entity.Total_order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TotalSales {

    @FXML
    private Button backButton; // 뒤로가기 버튼
    
    @FXML
    private Label totalPrice;
    
    @FXML
    private TableView<Total_order> tableView; // TableView 정의
    
    @FXML
    private TableColumn<Total_order, Integer> tableNoColumn;
    @FXML
    private TableColumn<Total_order, String> orderDateColumn;
    @FXML
    private TableColumn<Total_order, String> menuListColumn;
    @FXML
    private TableColumn<Total_order, Integer> priceColumn;
    
    private Scene previousScene;
    
    private ObservableList<Total_order> list = FXCollections.observableArrayList();
    
    private OrderMgr ordMgr;
    
    private int Price = 0;
    
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
    
    @FXML
    private void initialize() {
        // Set up the table columns
        tableNoColumn.setCellValueFactory(new PropertyValueFactory<>("Order_No"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("Order_Date"));
        menuListColumn.setCellValueFactory(new PropertyValueFactory<>("Menu_list"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        // Load data
        selectTotalSale();
        totalPrice.setText("금일 총 매출 : " + Price);
        // Set data to TableView
        tableView.setItems(list);
    }
    
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
    
    private void selectTotalSale() {
        ordMgr = new OrderMgr();
        Vector<Total_order> orders = ordMgr.selectTotalOrder(AppData.account.getUser_ID());
        Price = 0;
        for (Total_order total_order : orders) {
        	Price += total_order.getPrice();
		}
        
        list.clear();
        list.addAll(orders);
    }
    
}
