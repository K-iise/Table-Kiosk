package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionMgr {
    private static volatile DBConnectionMgr instance = null;
    private String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 변경 필요
    private String user = "dita"; // 변경 필요
    private String password = "1234"; // 변경 필요

    // 클래스 로딩 시점에 드라이버 로드
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Oracle JDBC Driver not found.");
        }
    }

    private DBConnectionMgr() {
        // 기본 생성자는 private으로 설정하여 외부에서 인스턴스를 생성하지 못하도록 함
    }

    public static DBConnectionMgr getInstance() {
        if (instance == null) {
            synchronized (DBConnectionMgr.class) {
                if (instance == null) {
                    instance = new DBConnectionMgr();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
        	System.err.println("Error message: " + e.getMessage()); // 예외 메시지 출력
            e.printStackTrace();
            throw e;
        }
    }
    
    // 리소스 해제 메소드
    public void closeResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
