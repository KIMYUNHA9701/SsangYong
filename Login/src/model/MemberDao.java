package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import util.Serviceutil;

public class MemberDao {
	
	public static ArrayList<MemberBean> selectAll(){
		String sql = "SELECT ID,PW FROM MEMBER";
		Connection con = Serviceutil.getInstance().getconnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberBean>list = new ArrayList<MemberBean>();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberBean bean = new MemberBean();
				bean.setId(rs.getString(1).trim());
				bean.setPw(rs.getString(2).trim());
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(con!=null) con.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return list;
	}
	
	public static void addMember(MemberBean bean) {
		String sql = "INSERT INTO MEMBER(ID,PW,GAME1_SCORE,GAME2_SCORE,GAME3_SCORE)" + 
	     " VALUES(?,?,0,0,0) ";
		Connection con =  Serviceutil.getInstance().getconnection();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getId());
			pstmt.setString(2, bean.getPw());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}finally {
			try {
				if(con!=null)con.close();
				if(pstmt!=null)pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void updateMember(String id,String pw) {
		String sql = "UPDATE MEMBER SET PW = ? WHERE ID = ? ";
		Connection con =  Serviceutil.getInstance().getconnection();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,pw);
			pstmt.setString(2,id);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}finally {
			try {
				if(con!=null) con.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void deleteMember(String id) {
		String sql = "DELETE FROM MEMBER WHERE ID = ? ";
		Connection con =  Serviceutil.getInstance().getconnection();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}finally {
			try {
				if(con!=null) con.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static boolean idCheck(String id) {
		  ArrayList<MemberBean>list = selectAll();
	      for(MemberBean m:list) {
	          if(m.getId().equals(id)) {
	             return true;
	          }//if
	      }//for
	      return false;
	}
	
	public static boolean idPassCheck(String id,String password) {
		  ArrayList<MemberBean>list = selectAll();
	      for(MemberBean m:list) {
	          if(m.getId().equals(id)&& m.getPw().equals(password)) {
	             return true;
	          }//if
	      }//for
	      return false;
	}
	
	public static TableModel injectTable(TableModel table) {
		Object[][] data = null;
		String[] item = null;
		String sql = "SELECT ID,GAME1_SCORE,GAME2_SCORE,GAME3_SCORE FROM MEMBER";
		Connection con = Serviceutil.getInstance().getconnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd=null;
		int r_count = 0;
		int c_count = 0;
		try {
			pstmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			rs.last();
			r_count = rs.getRow();
			rs.beforeFirst(); 
			rsmd=rs.getMetaData();
			c_count = rsmd.getColumnCount();
			data = new Object[r_count][c_count];
			item = new String[c_count];
			for(int i = 1; i <= c_count; i++) {
				item[i-1] = rsmd.getColumnName(i);
			}
			
			int i = 0;
			while(rs.next()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getInt(2);
				data[i][2] = rs.getInt(3);
				data[i][3] = rs.getInt(4);
				i++;
			}
			table.setData(data);
			table.setItem(item);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if(con!=null) con.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return table;
	}
	
	
	public static int selectGameScore3(String id) {
		String sql = "SELECT GAME3_SCORE FROM MEMBER WHERE ID = ? ";
		Connection con = Serviceutil.getInstance().getconnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int hiscore = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				hiscore = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(con!=null) con.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return hiscore;
	}
	
	public static void updateGameScore3(String id,int hiscore) {
		String sql = "UPDATE MEMBER SET GAME3_SCORE = ? WHERE ID = ? ";
		Connection con =  Serviceutil.getInstance().getconnection();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,hiscore);
			pstmt.setString(2,id);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}finally {
			try {
				if(con!=null) con.close();
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}
