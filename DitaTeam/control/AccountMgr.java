package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Account;

public class AccountMgr {
    private DBConnectionMgr pool;
    
    public AccountMgr() {
        pool = DBConnectionMgr.getInstance();
    }
    
    // 관리자 테이블 검색 메소드 (로그인 검사)
    public boolean selectAccount(Account bean) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM ACCOUNT WHERE USER_ID = ? AND USER_PW = ?";
        
        boolean flag = false;
        try {
            con = pool.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getUser_ID());
            pstmt.setString(2, bean.getUser_PW());
            rs = pstmt.executeQuery();
            
            if (rs.next()) { 
                bean.setUser_ID(rs.getString("USER_ID"));
                bean.setUser_PW(rs.getString("USER_PW"));
                bean.setUser_Branch(rs.getString("USER_BRANCH"));
                bean.setUser_Address(rs.getString("USER_ADDRESS"));
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
        	pool.closeResources(con, pstmt, rs);
        }
        
        return flag;
    }
    
    // 관리자 테이블 삽입 메소드. (회원가입)
    public boolean insertAccount(Account bean) {
    	Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert into ACCOUNT values (?, ?, ? ,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getUser_ID());
			pstmt.setString(2, bean.getUser_PW());
			pstmt.setString(3, bean.getUser_Branch());
			pstmt.setString(4, bean.getUser_Address());
			int cnt = pstmt.executeUpdate();
			
			if(cnt == 1) flag = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            // 자원 해제
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return flag;
    }
    
    // 관리자 테이블 수정
    public boolean updateAccount(Account bean) {
    	Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "UPDATE set User_Branch = ?, User_Address = ? where User_ID = ? and User_PW = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getUser_Branch());
			pstmt.setString(2, bean.getUser_Address());
			pstmt.setString(3, bean.getUser_ID());
			pstmt.setString(4, bean.getUser_PW());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, null);
		}
		return flag;
    }
    
    public static void main(String[] args) {
		/*
		 * AccountMgr mgr = new AccountMgr(); AccountBean bean = new AccountBean();
		 * boolean flag = mgr.checkLogin("user1", "1234", bean);
		 * 
		 * System.out.println("Login Successful: " + flag); if (flag) {
		 * System.out.println(bean.getUser_ID()); System.out.println(bean.getUser_PW());
		 * System.out.println(bean.getUser_Branch());
		 * System.out.println(bean.getUser_Address()); }
		 * 
		 * bean.setUser_ID("test2"); bean.setUser_PW("1234");
		 * bean.setUser_Branch("서면점"); bean.setUser_Address("Address2"); boolean flag2 =
		 * mgr.registerAccount(bean);
		 * 
		 * System.out.println("Register Successful: " + flag2);
		 * 
		 * if (flag2) { System.out.println(bean.getUser_ID());
		 * System.out.println(bean.getUser_PW());
		 * System.out.println(bean.getUser_Branch());
		 * System.out.println(bean.getUser_Address()); }
		 */
    }
}

