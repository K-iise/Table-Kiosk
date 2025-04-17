package admin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderItem {
	private StringProperty tblno;
    private StringProperty menu;
    private IntegerProperty quantity;

    public OrderItem(String tblno, String menu, int quantity) {
        this.tblno = new SimpleStringProperty(tblno);
        this.menu = new SimpleStringProperty(menu);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getTblno() {
        return tblno.get();
    }

    public StringProperty tblnoProperty() {
        return tblno;
    }

    public String getMenu() {
        return menu.get();
    }

    public StringProperty menuProperty() {
        return menu;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
