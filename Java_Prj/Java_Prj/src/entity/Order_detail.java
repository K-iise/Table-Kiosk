package entity;

import java.io.Serializable;

public class Order_detail implements Serializable {
    private static final long serialVersionUID = 1L; // 직렬화 버전 ID

    private int Order_No;
    private String Menu_Name;
    private String Ctg_Name;
    private int Order_Num;
    
    // 기본 생성자
    public Order_detail() {
    }

    // Getter 및 Setter
    public int getOrder_No() {
        return Order_No;
    }
    public void setOrder_No(int order_No) {
        Order_No = order_No;
    }
    public String getMenu_Name() {
        return Menu_Name;
    }
    public void setMenu_Name(String menu_Name) {
        Menu_Name = menu_Name;
    }
    public String getCtg_Name() {
        return Ctg_Name;
    }
    public void setCtg_Name(String ctg_Name) {
        Ctg_Name = ctg_Name;
    }
    public int getOrder_Num() {
        return Order_Num;
    }
    public void setOrder_Num(int order_Num) {
        Order_Num = order_Num;
    }
}
