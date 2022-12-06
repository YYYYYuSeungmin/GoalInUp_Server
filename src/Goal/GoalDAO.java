package Goal;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
//import java.sql.Date;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import DB.DB_Conn;
import User.User;

public class GoalDAO {
	private Goal goal = new Goal();
	private Connection conn = null;
    private PreparedStatement pstmt = null;
    String sql;
    ArrayList<Goal> goalList;
    
    public boolean insertGoal(Goal goal) { //(큰)목표 등록하기
    	int res = 0;
    	try {
			conn = DB_Conn.getConnection();
			
			sql = "insert into goal(id, title, startday, endday, goal) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, goal.getgID());
			pstmt.setString(1, goal.getUserID());
			pstmt.setString(2, goal.getTitle());
			pstmt.setString(3, goal.getStartDay());
			pstmt.setString(4, goal.getEndDay());
			pstmt.setBoolean(5, goal.isGoal());
			res = pstmt.executeUpdate();
			
			
	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    	if (res > 0) return true;
    	else return false;
    }
    public boolean updateGoal(Goal goal) {
    	int res = 0;
    	try {
			conn = DB_Conn.getConnection();
			System.out.println("입력받은 Goal의 startDay : " + goal.getStartDay());
			sql = "UPDATE goal SET id = ?, title = ?, startday = ?, endday = ?, goal = ? WHERE g_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, goal.getUserID());
			pstmt.setString(2, goal.getTitle());
			pstmt.setString(3, goal.getStartDay());
			pstmt.setString(4, goal.getEndDay());
			pstmt.setBoolean(5, goal.isGoal());
			pstmt.setInt(6, goal.getgID());
			res = pstmt.executeUpdate();
	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    	if (res > 0) return true;
    	else return false;
    }
    public boolean removeGoal(int g_ID) {
    	int res = 0;
    	try {
    		conn = DB_Conn.getConnection();
    		
    		sql = "delete from goal where g_ID = ?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, g_ID);
    		
    		res = pstmt.executeUpdate();

    	} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    	
    	if (res > 0) return true;
    	else return false;
    }
	public ArrayList<Goal> getGoalList(String userID) {
		ResultSet rs = null;
		ArrayList<Goal> goalList = new ArrayList();
		SimpleDateFormat newformatter = new SimpleDateFormat("yyyy-mm-dd");
		SimpleDateFormat beforeformatter = new SimpleDateFormat("yyyymmdd");
		
		try {
			conn = DB_Conn.getConnection();
			
			sql = "SELECT * FROM GOAL WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Goal goal = new Goal();
				goal.setgID(rs.getInt(1));
				goal.setUserID(rs.getString(2));
				goal.setTitle(rs.getString(3));
				goal.setStartDay(rs.getString(4));
				goal.setEndDay(rs.getString(5));
				goal.setGoal(rs.getBoolean(6));
	
				String start = goal.getStartDay();
				String end = goal.getEndDay();
				
				Date STARTTIME = beforeformatter.parse(start);
				Date ENDTIME = beforeformatter.parse(end); //파싱 과정 필요
				
				start = newformatter.format(STARTTIME);
				end = newformatter.format(ENDTIME);
				
				goal.setStartDay(start);
				goal.setEndDay(end);
				
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
}