module JavaFX_Base {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.swing;
	requires java.sql;
	requires javafx.graphics;
    requires javafx.base;

	opens application to javafx.graphics, javafx.fxml;
	
    exports admin; // admin 패키지 공개
    opens admin to javafx.graphics, javafx.fxml; // admin 패키지 열기
    opens entity to javafx.base;
}
