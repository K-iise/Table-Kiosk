package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import entity.Category;

public class CategoryMgr {
	private DBConnectionMgr pool;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	public CategoryMgr() {
		pool=DBConnectionMgr.getInstance();
	}
	
	//모든 카테고리 불러오기
	public Vector<Category> selectAllCtg(){
		Vector<Category> v=new Vector<Category>();
		
		String sql="select * from category";
		try {
			con=pool.getConnection();
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Category c=new Category();
				c.setCtg_Name(rs.getString(1));
				v.addElement(c);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.closeResources(con, pstmt, rs);
		}
		return v;
	}
	//카테고리 추가
	public boolean insertCtg(Category c) {
		boolean b=false;
		String sql = "insert into category values(?)";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, c.getCtg_Name());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
	//카테고리 변경
	public boolean updateCtg(Category c,String s) {
		boolean b=false;
		String sql = "update category set ctg_name=? where ctg_name=?";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, s);
			pstmt.setString(2, c.getCtg_Name());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
	//카테고리 삭제
	public boolean deleteCtg(Category c) {
		boolean b=false;
		String sql = "delete from category where ctg_name=?";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, c.getCtg_Name());
			if(pstmt.executeUpdate()!=0) b=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.closeResources(con, pstmt, rs);
		}
		return b;
	}
}