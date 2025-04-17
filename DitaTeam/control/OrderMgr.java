package control;

import entity.Order;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderMgr {

	private DBConnectionMgr pool;

	public OrderMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	// 주문 테이블 삽입.
	public boolean insertOrder(Order bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		try {
			// 풀에서 커넥션을 가져온다.
			con = pool.getConnection();
			con.setAutoCommit(false);
			
			
			// 새 Order_ID를 가져오는 쿼리
	        String selectSql = "SELECT COALESCE(MAX(Order_No), 0) + 1 AS new_id FROM Orders";
	        pstmt = con.prepareStatement(selectSql);
	        rs = pstmt.executeQuery();
	        
	        // 디폴트값
	        int newOrderNo = 1;
	        
	        if(rs.next()) {
	        	newOrderNo = rs.getInt("new_id");
	        }
	        
			
			sql = "INSERT INTO Orders (Order_No, User_ID, Guest_ID, Order_Date) VALUES (?, ?, ?, SYSDATE)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newOrderNo);
			pstmt.setString(2, bean.getUser_ID());
			pstmt.setString(3, bean.getGuest_ID());

			if (pstmt.executeUpdate() == 1) {
				flag = true;
				bean.setOrder_No(newOrderNo);
			}
			// 커밋
			con.commit();
			
		} catch (Exception e) {
			if (con != null) {
	            try {
	            	// 롤백
	                con.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return flag;
	}

	// 주문 테이블 삭제.
	public boolean deleteOrder(Order bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from Orders where order_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bean.getOrder_No());

			if (pstmt.executeUpdate() == 1)
				flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, null);
		}
		return flag;
	}

	// 주문 테이브 조회.
	public Order selectOrder(int Order_No) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Order bean = new Order();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM Orders where Order_No = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Order_No);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				bean.setOrder_No(rs.getInt("Order_No"));
				bean.setUser_ID(rs.getString("User_ID"));
				bean.setGuest_ID(rs.getString("Guest_ID"));
				bean.setOrder_Date(rs.getDate("Order_Date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return bean;
	}
	
	public static void main(String[] args) {
		
		// 주문 삽입, 삭제 예시.
		Order bean = new Order();
		OrderMgr mgr = new OrderMgr();
		
		bean.setGuest_ID("guest1");
		bean.setUser_ID("user1");
		Date today = new Date();

		if(mgr.insertOrder(bean)) { System.out.println("주문이 완료되었습니다."); }
		
		//if(mgr.deleteOrder(bean)) { System.out.println("주문이 삭제되었습니다."); }
		 
	}

}
