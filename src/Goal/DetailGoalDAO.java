package Goal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

import DB.DB_Conn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DetailGoalDAO {
	private DetailGoal dGoal = new DetailGoal();
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private String sql;
	private ArrayList<DetailGoal> dGoalList;

	public ArrayList<DetailGoal> getDetailGoalList(int gid) {
		ResultSet rs = null;

		dGoalList = new ArrayList();
		SimpleDateFormat newformatter = new SimpleDateFormat("yyyy-mm-dd");
		SimpleDateFormat beforeformatter = new SimpleDateFormat("yyyymmdd");

		try {
			conn = DB_Conn.getConnection();

			sql = "SELECT * FROM detailgoals WHERE g_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gid);
			System.out.println("요청받은 gid : " + gid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DetailGoal dGoal = new DetailGoal();
				dGoal.setDetail_gID(rs.getInt(1));
				dGoal.setgID(rs.getInt(2));
				dGoal.setTitle(rs.getString(3));
				dGoal.setContents(rs.getString(4));

				String start = rs.getString(5);
				String end = rs.getString(6);
				Date STARTTIME = beforeformatter.parse(start);
				Date ENDTIME = beforeformatter.parse(end); // 파싱 과정 필요
				start = newformatter.format(STARTTIME);
				end = newformatter.format(ENDTIME);
				dGoal.setStartDay(start);
				dGoal.setEndDay(end);
				dGoal.setGoal(rs.getBoolean(7));
				
				
				
				dGoalList.add(dGoal);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// System.out.println(dGoalList.get(0).getContents());
		return dGoalList;
	}
	
	public boolean insertDetailGoal(DetailGoal detailGoal) {
		int res = 0;
		try {
			conn = DB_Conn.getConnection();
			
			sql = "INSERT INTO detailgoals(g_ID, title, contents, startday, endday, goal) VALUES(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			System.out.println("세부 목표 등록 요청한 g_ID : " + detailGoal.getgID());
			pstmt.setInt(1, detailGoal.getgID());
			pstmt.setString(2, detailGoal.getTitle());
			pstmt.setString(3, detailGoal.getContents());
			pstmt.setString(4, detailGoal.getStartDay());
			pstmt.setString(5, detailGoal.getEndDay());
			pstmt.setBoolean(6, detailGoal.isGoal());
			res = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		if (res > 0) return true;
    	else return false;
	}
	
	public boolean updateDetailGoal(DetailGoal detailGoal) {
		int res = 0;
		
		try {
			conn = DB_Conn.getConnection();
			sql = "UPDATE detailgoals SET title = ?, contents = ?, startday = ?, endday = ?, goal = ? WHERE detail_gid = ?";
			pstmt = conn.prepareStatement(sql);
			System.out.println("세부 목표 수정 요청한 detail_gid : "+ detailGoal.getDetail_gID());
			pstmt.setString(1, detailGoal.getTitle());
			pstmt.setString(2, detailGoal.getContents());
			String startDay = detailGoal.getStartDay().replace("-", "");
			String endDay = detailGoal.getEndDay().replace("-", "");
			pstmt.setString(3, startDay);
			pstmt.setString(4, endDay);
			pstmt.setBoolean(5, detailGoal.isGoal());
			pstmt.setInt(6, detailGoal.getDetail_gID());
			
			res = pstmt.executeUpdate();
			
			
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		if (res > 0) return true;
    	else return false;
	}
	

	
	public boolean deleteDetailGoal(int detail_gid) {
		int res = 0;
		
		try {
			conn = DB_Conn.getConnection();
			sql = "DELETE FROM detailgoals WHERE detail_gid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, detail_gid);
			
			res = pstmt.executeUpdate();
			
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		if (res > 0) return true;
    	else return false;
	}
}
