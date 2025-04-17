package entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderViewItem {
    private final SimpleStringProperty guestId;
    private final SimpleStringProperty menuName;
    private final SimpleIntegerProperty orderNum;

    public OrderViewItem(String guestId, String menuName, int orderNum) {
        this.guestId = new SimpleStringProperty(guestId);
        this.menuName = new SimpleStringProperty(menuName);
        this.orderNum = new SimpleIntegerProperty(orderNum);
    }

    public String getGuestId() {
        return guestId.get();
    }

    public String getMenuName() {
        return menuName.get();
    }

    public int getOrderNum() {
        return orderNum.get();
    }

    // getter를 통해 Property 객체를 제공하는 경우
    public StringProperty guestIdProperty() {
        return guestId;
    }

    public StringProperty menuNameProperty() {
        return menuName;
    }

    public IntegerProperty orderNumProperty() {
        return orderNum;
    }
}
