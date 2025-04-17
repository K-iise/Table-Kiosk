package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import entity.Account;
import entity.Guest;

public class GuestMgr {
	private DBConnectionMgr pool;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	public GuestMgr() {
		pool=DBConnectionMgr.getInstance();
	}
	
	// 게스트 검색 
    public boolean selectGuestID(String guestID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Guest WHERE Guest_ID = ?";
        
        boolean flag = false;
        try {
            con = pool.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, guestID);
            rs = pstmt.executeQuery();
            
            if (rs.next()) { 
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
	
	
	// 게스트 검색 
    public boolean selectGuest(Guest bean) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Guest WHERE Guest_ID = ? and User_ID = ?";
        
        boolean flag = false;
        try {
            con = pool.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getGuest_ID());
            pstmt.setString(2, bean.getUser_ID());
            rs = pstmt.executeQuery();
            
            if (rs.next()) { 
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
	

	//모든 게스트 불러오기
	public Vector<Guest> selectAllGuest(){
		Vector<Guest> v=new Vector<Guest>();
		
		String sql="select * from guest";
		try {
			con=pool.getConnection();
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Guest g=new Guest();
				g.setGuest_ID(rs.getString(1));
				g.setUser_ID(rs.getString(2));
				v.addElement(g);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.closeResources(con, pstmt, rs);
		}
		return v;
	}
	//게스트 추가
	public boolean insertGuset(Guest g) {
		boolean b=false;
		String sql = "insert into guest values(?,?)";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, g.getGuest_ID());
			pstmt.setString(2, g.getUser_ID());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
	//게스트 아이디 변경?..(*필요한가 싶음)
	public boolean updateGuest(Guest g,String s) {
		boolean b=false;
		String sql = "update guest set guest_id=? where guest_id=? and user_id=?";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, s);
			pstmt.setString(2, g.getGuest_ID());
			pstmt.setString(3, g.getUser_ID());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
	
	//게스트 삭제
	public boolean deleteGuest(Guest g) {
		
		boolean b=false;
		String sql = "delete from guest where guest_id=? and user_id=?";
		try {
			con = pool.getConnection();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, g.getGuest_ID());
			pstmt.setString(2, g.getUser_ID());
			if(pstmt.executeUpdate()!=0) b=true;
			
			// 커밋
			con.commit(); // 트랜잭션 커밋
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, null);
		}
		return b;
	}
}