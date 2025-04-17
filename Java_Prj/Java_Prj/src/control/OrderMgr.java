package control;

import entity.Account;
import entity.Guest;
import entity.Order;
import entity.Total_order;

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
			con.commit(); // 트랜잭션 커밋
			
			Order_detailMgr ordMgr = new Order_detailMgr();
			ordMgr.insertOrder_detail(bean);
			
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
	
	public Vector<Total_order> selectTotalOrder(String id) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    Vector<Total_order> list = new Vector<Total_order>();
	    
	    try {
	        con = pool.getConnection();
	        sql = "SELECT "
	                + "o.order_no AS \"테이블 번호\", "
	                + "TO_CHAR(o.order_date, 'YYYY-MM-DD HH24:MI:SS') AS \"주문 시간\", "
	                + "LISTAGG(DISTINCT od.menu_name, ', ') WITHIN GROUP (ORDER BY od.menu_name) AS \"주문 내역\", "
	                + "SUM(od.order_num * m.menu_price) AS \"금액\" "
	                + "FROM "
	                + "ORDER_DETAIL od "
	                + "INNER JOIN ORDERS o ON od.order_no = o.order_no "
	                + "INNER JOIN MENU m ON m.menu_name = od.menu_name AND m.ctg_name = od.ctg_name "
	                + "WHERE "
	                + "TRUNC(o.order_date) = TRUNC(SYSDATE) "
	                + "AND o.user_id = ? "
	                + "GROUP BY "
	                + "o.order_no, "
	                + "TO_CHAR(o.order_date, 'YYYY-MM-DD HH24:MI:SS')";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Total_order order = new Total_order();
	            order.setOrder_No(rs.getInt("테이블 번호"));
	            order.setMenu_list(rs.getString("주문 내역"));
	            order.setOrder_Date(rs.getString("주문 시간")); // 변경됨
	            order.setPrice(rs.getInt("금액")); // 확인 필요: BigDecimal 또는 Long을 사용할 수 있음
	            list.add(order);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.closeResources(con, pstmt, rs);
	    }
	    return list;
	}
	
	public boolean updateOrder(Guest bean) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    int n = 0;

	    try {
	        con = pool.getConnection();
	        con.setAutoCommit(false); // 트랜잭션 시작

	        sql = "UPDATE orders SET guest_id = 'Ghost' WHERE user_id = ? AND guest_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, bean.getUser_ID());
	        pstmt.setString(2, bean.getGuest_ID());

	        System.out.println(bean.getUser_ID());
	        System.out.println(bean.getGuest_ID());

	        n = pstmt.executeUpdate();

	        if (n > 0) {
	            con.commit(); // 트랜잭션 커밋
	        } else {
	            con.rollback(); // 업데이트가 없으면 롤백
	        }

	        System.out.println(n);

	    } catch (Exception e) {
	        if (con != null) {
	            try {
	                con.rollback(); // 예외 발생 시 롤백
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	    } finally {
	        pool.closeResources(con, pstmt, null);
	    }

	    return (n > 0);
	}


	
	public static void main(String[] args) {
		
		// 주문 삽입, 삭제 예시.
		Order bean = new Order();
		OrderMgr mgr = new OrderMgr();
		
		
		Vector<Total_order> list = mgr.selectTotalOrder("user1");
		
		for (Total_order total_order : list) {
			System.out.println(total_order.getOrder_No() + " : " + total_order.getMenu_list() + " : " + total_order.getOrder_Date()
			 + " : " + total_order.getPrice());
		}
		//if(mgr.deleteOrder(bean)) { System.out.println("주문이 삭제되었습니다."); }
		 
	}

}
