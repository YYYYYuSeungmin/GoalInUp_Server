package TEST;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Goal.Goal;
import DB.DB_Conn;

public class GOALTEST {
	private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    String sql;
	public void insertGoal(Goal goal) {
		Goal insGoal = new Goal();
		insGoal.setTitle("목표 DB연동 테스트 insert");
		insGoal.setUserID("TEST");
		insGoal.setStartDay("20221126");
		insGoal.setEndDay("20221127");
		
		try {
			conn = DB_Conn.getConnection();
			
			sql = "insert into goal(id, title, startday, endday) values(?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, insGoal.getUserID());
			pstmt.setString(2, insGoal.getTitle());
			pstmt.setString(3, insGoal.getStartDay());
			pstmt.setString(4, insGoal.getEndDay());
			
			int res = pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public ArrayList<Goal> getGoalList(String userID) {
		ResultSet rs = null;
		ArrayList<Goal> goalList = new ArrayList();
		SimpleDateFormat newformatter = new SimpleDateFormat("yyyy-mm-dd");
		SimpleDateFormat beforeformatter = new SimpleDateFormat("yyyymmdd");
		
		System.out.println("getGoalList 진입");
		
		try {
			conn = DB_Conn.getConnection();
			
			sql = "SELECT * FROM GOAL WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int count = 0;
				System.out.println("목표 탐색" + count++);
				Goal goal = new Goal();
				goal.setgID(rs.getInt(1));
				goal.setUserID(rs.getString(2));
				goal.setTitle(rs.getString(3));
				goal.setStartDay(rs.getString(4));
				goal.setEndDay(rs.getString(5));
				goal.setGoal(rs.getBoolean(6));
	
				String start = goal.getStartDay();
				String end = goal.getStartDay();
				
				Date STARTTIME = beforeformatter.parse(start);
				Date ENDTIME = beforeformatter.parse(end); //파싱 과정 필요
				
				start = newformatter.format(STARTTIME);
				end = newformatter.format(ENDTIME);
				
				System.out.println(start + " ~ " + end);
				goalList.add(goal);
			}
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return goalList;
	}
	
	public static void main(String[] args) {
		GOALTEST gT = new GOALTEST();
		Goal g = new Goal();
		gT.insertGoal(g);
		gT.getGoalList("TEST");
	}
}