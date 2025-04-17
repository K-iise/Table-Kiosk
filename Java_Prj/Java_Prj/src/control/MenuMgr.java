package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import entity.Menu;

public class MenuMgr {
	private DBConnectionMgr pool;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	public MenuMgr() {
		pool=DBConnectionMgr.getInstance();
	}
	
	
	//모든메뉴 불러오기
	public Vector<Menu> selectAllMenu(){
		Vector<Menu> v=new Vector<Menu>();
		
		String sql="select * from menu";
		try {
			con=pool.getConnection();
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Menu m=new Menu();
				m.setCtg_Name(rs.getString(1));
				m.setMenu_Name(rs.getString(2));
				m.setMenu_Price(rs.getInt(3));
				v.addElement(m);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.closeResources(con, pstmt, rs);
		}
		return v;
	}
	//메뉴추가
	public boolean insertMenu(Menu m) {
		boolean b=false;
		String sql = "insert into menu values(?,?,?)";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getCtg_Name());
			pstmt.setString(2, m.getMenu_Name());
			pstmt.setInt(3, m.getMenu_Price());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
	//메뉴의 카테고리 변경
	public boolean updateMenuCtg(Menu m, String s) {
		boolean b=false;
		String sql = "update menu set ctg_name=? where ctg_name=? and menu_name=?";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, s);
			pstmt.setString(2, m.getCtg_Name());
			pstmt.setString(3, m.getMenu_Name());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
	//메뉴의 삭제
	public boolean deleteMenu(Menu m) {
		boolean b=false;
		String sql = "delete from menu where ctg_name=? and menu_name=?";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getCtg_Name());
			pstmt.setString(2, m.getMenu_Name());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
}